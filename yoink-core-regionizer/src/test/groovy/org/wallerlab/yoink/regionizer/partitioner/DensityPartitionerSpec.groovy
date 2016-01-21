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
package org.wallerlab.yoink.regionizer.partitioner

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.regionizer.*;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegionFactory

import spock.lang.Specification;


class DensityPartitionerSpec extends Specification{

	def "test method getDensiyThreshold"(){
		def partitioner= new DensityPartitioner()
		def parameters=Mock(Map)
		when:"store thresholds  in parameters"
		parameters.get(JobParameter.DENSITY_ASR_QMCORE)>>(double)1.0E-5
		parameters.get(JobParameter.DENSITY_ASR_QM)>>(double)1.0E-4
		parameters.get(JobParameter.DENSITY_BUFFER)>>(double)1.0E-9

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
		parameters.get(JobParameter.DENSITY_BUFFER)>>(double)1.0E-9
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def m3=Mock(Molecule)
		def mMap=new HashMap<Molecule,Integer>()
		mMap.put(m2, 2)
		mMap.put(m3,3)

		def mMap0=new HashMap<Molecule,Integer>()
		mMap0.put(m1, 1)
		def region0=Mock(Region)
		region0.getMolecules()>>[m1]
		region0.getMolecularMap()>>mMap0

		def region=Mock(Region)
		region.getMolecules()>>[m2, m3]

		def regions=new HashMap<Region.Name,Region>()
		regions.put(Region.Name.QM_CORE,region0)
		regions.put(Region.Name.NONQM_CORE,region)

		def singleRegionizerService=Mock(RegionizerMath)
		singleRegionizerService
				.regionize(_,_)>>Mock(Region)
		def densityCalculator=Mock(Calculator)
		densityCalculator.calculate(_,	_)>>(double)1.0E-5
		def simpleRegionFactory=new SimpleRegionFactory()

		when:"set up a new DensityPartitioner"
		def partitioner= new DensityPartitioner()
		partitioner.densityCalculator=densityCalculator
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.simpleRegionFactory=simpleRegionFactory

		then:"method partition  is executable and returns right result"
		partitioner.partition(regions,parameters,DensityType.ELECTRONIC)
		regions.get(Region.Name.ADAPTIVE_SEARCH).getSize()==3
	}
}

