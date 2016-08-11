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
package org.wallerlab.yoink.region.service.partitioners.old

import org.wallerlab.yoink.api.model.Job
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.region.Regionizer
import org.wallerlab.yoink.density.service.SimpleDensityCalculator

import org.wallerlab.yoink.api.model.VoronoiPoint;
import org.wallerlab.yoink.api.model.DensityPoint;

import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.molecule.service.Computer
import org.wallerlab.yoink.region.domain.SimpleRegion
import spock.lang.Ignore
import spock.lang.Specification;

@Ignore
class DoriPartitionerSpec extends Specification{

	def "test method partition"(){

		def regions=Mock(Map)
		def parameters=Mock(Map)
		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)
		def grid1=Mock(VoronoiPoint)
		def grid2=Mock(VoronoiPoint)
		grid1.getNearestMolecules()>>[m1]
		grid2.getNearestMolecules()>>[m2]
		def gridPoints=[grid1, grid2]

		def region=Mock(Region)
		region.getName()>>Region.Name.QM_ADAPTIVE

		region.getMolecularMap()>>Mock(Map)
		region.getAtoms()>>[Mock(MolecularSystem.Molecule.Atom)]

		regions.get(_)>>region
		def singleRegionizerService=Mock(Regionizer)
		singleRegionizerService.regionize(_,_)>>region

		def densityPropertiesCalculator=Mock(Calculator)
		densityPropertiesCalculator.calculate(_,_)>>Mock(DensityPoint)

		def densityCalculator=Mock(Calculator)
		densityCalculator.calculate(_,_)>>(double)1.0E-2

		def densityOverlapRegionsIndicatorComputer=Mock(Computer)
		densityOverlapRegionsIndicatorComputer.calculate(_)>>(double)0.99

		parameters.get(Job.JobParameter.DENSITY_DORI)>>(double)1.0E-4
		parameters.get(Job.JobParameter.DORI)>>(double)0.9
		def simpleRegionFactory=new SimpleRegion()

		when:"set up a new DensityOverlapRegionsIndicatorPartitioner"
		def partitioner= new SimpleDensityCalculator()
		partitioner.regionFactory=simpleRegionFactory
		partitioner.dori=densityOverlapRegionsIndicatorComputer
		partitioner.density=densityCalculator
		partitioner.descriptor=densityPropertiesCalculator
		partitioner.singleRegionizerService=singleRegionizerService

		then:"method partition is executable and gets right result"
		partitioner.partition(regions,parameters,gridPoints)
		regions.get(QM_ADAPTIVE)==region
	}
}