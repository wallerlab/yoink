/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wallerlab.yoink.region.service.partitioners

import org.wallerlab.yoink.api.model.Job

import org.wallerlab.yoink.api.model.DensityPoint.DensityType

import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.density.domain.ExponentialFit
import org.wallerlab.yoink.density.domain.RadialGrid;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator

import org.wallerlab.yoink.api.service.region.Regionizer
import org.wallerlab.yoink.api.service.molecule.FilesReader
import org.wallerlab.yoink.region.domain.SimpleRegion
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class DensityPartitionerSpec extends Specification{

	def "test method getDensityThreshold"(){
		def partitioner= new DensityPartitioner()
		def parameters=Mock(Map)
		when:"store thresholds  in parameters"
		parameters.get(Job.JobParameter.DENSITY_ASR_QMCORE)>>(double)1.0E-5
		parameters.get(Job.JobParameter.DENSITY_ASR_QM)>>(double)1.0E-4
		parameters.get(Job.JobParameter.DENSITY_BUFFER)>>(double)1.0E-9

		then:"density partitioner gets right thresholds"
		partitioner.getDensityThreshold(parameters,DensityType.DORI)==1.0E-4
		partitioner.getDensityThreshold(parameters,DensityType.ELECTRONIC)==1.0E-9
		partitioner.getDensityThreshold(parameters,DensityType.SEDD)==1.0E-5

		when:"density type  is not in source code"
		partitioner.getDensityThreshold(parameters,DensityType.DENSITY)
		then:"throw exception"
		Exception ex=thrown()
		ex.message=="invalid  name"
	}

	def "calculateAdaptiveSearchRegion" (){

		def parameters=Mock(Map)
		parameters.get(Job.JobParameter.DENSITY_BUFFER)>>(double)1.0E-9
		parameters.get(Job.JobParameter.DGRID)>>true
		parameters.get(Job.JobParameter.WFC_PATH)>>"./src/test/resources"
		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)
		def m3=Mock(MolecularSystem.Molecule)
		def mMap=new HashMap<MolecularSystem.Molecule,Integer>()
		mMap.put(m2, 2)
		mMap.put(m3,3)
		def atom=Mock(MolecularSystem.Molecule.Atom)
		 atom.getElementType()>>ExponentialFit.C
		m1.getAtoms()>>[atom]
		m2.getAtoms()>>[atom]
		m3.getAtoms()>>[atom]
		def mMap0=new HashMap<MolecularSystem.Molecule,Integer>()
		mMap0.put(m1, 1)
		def region0=Mock(Region)
		region0.getMolecules()>>[m1]
		region0.getMolecularMap()>>mMap0
		region0.getAtoms()>>[atom]
		def region=Mock(Region)
		region.getMolecules()>>[m2, m3]

		def regions=new HashMap<Region.Name,Region>()
		regions.put(Region.Name.QM_CORE,region0)
		regions.put(Region.Name.NONQM_CORE,region)

		def singleRegionizerService=Mock(Regionizer)
		singleRegionizerService
				.regionize(_,_)>>Mock(Region)
		def densityCalculator=Mock(Calculator)
		densityCalculator.calculate(_,	_)>>(double)1.0E-5
		def simpleRegionFactory=new SimpleRegion()
		
		def radialGridReader= Mock(FilesReader)
		radialGridReader.read(_,_)>>Mock(RadialGrid)
		when:"set up a new DensityPartitioner"
		def partitioner= new DensityPartitioner()
		partitioner.density=densityCalculator
		partitioner.regionizer=singleRegionizerService
		partitioner.regionFactory=simpleRegionFactory
		partitioner.radialGridReader=radialGridReader

		then:"method partition  is executable and returns right result"
		partitioner.partition(regions,parameters,DensityType.ELECTRONIC)
		regions.get(Region.Name.ADAPTIVE_SEARCH).getSize()==3
	}
}

