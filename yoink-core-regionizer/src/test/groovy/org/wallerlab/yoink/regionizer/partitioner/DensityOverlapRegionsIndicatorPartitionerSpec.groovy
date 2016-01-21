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

import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.regionizer.*;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegionFactory

class DensityOverlapRegionsIndicatorPartitionerSpec extends Specification{

	def "test method partition"(){

		def regions=Mock(Map)
		def parameters=Mock(Map)
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def grid1=Mock(GridPoint)
		def grid2=Mock(GridPoint)
		grid1.getTwoClosestMolecules()>>[m1]
		grid2.getTwoClosestMolecules()>>[m2]
		def gridPoints=[grid1, grid2]

		def region=Mock(Region)
		region.getName()>>Region.Name.QM_ADAPTIVE

		region.getMolecularMap()>>Mock(Map)
		region.getAtoms()>>[Mock(Atom)]

		regions.get(_)>>region
		def singleRegionizerService=Mock(RegionizerMath)
		singleRegionizerService.regionize(_,_)>>region

		def densityPropertiesCalculator=Mock(Calculator)
		densityPropertiesCalculator.calculate(_,_)>>Mock(DensityPoint)

		def densityCalculator=Mock(Calculator)
		densityCalculator.calculate(_,_)>>(double)1.0E-2

		def densityOverlapRegionsIndicatorComputer=Mock(Computer)
		densityOverlapRegionsIndicatorComputer.calculate(_)>>(double)0.99

		parameters.get(JobParameter.DENSITY_DORI)>>(double)1.0E-4
		parameters.get(JobParameter.DORI)>>(double)0.9
		def simpleRegionFactory=new SimpleRegionFactory()

		when:"set up a new DensityOverlapRegionsIndicatorPartitioner"
		def partitioner=new DensityOverlapRegionsIndicatorPartitioner()
		partitioner.simpleRegionFactory=simpleRegionFactory
		partitioner.densityOverlapRegionsIndicatorComputer=densityOverlapRegionsIndicatorComputer
		partitioner.densityCalculator=densityCalculator
		partitioner.densityPropertiesCalculator=densityPropertiesCalculator
		partitioner.singleRegionizerService=singleRegionizerService

		then:"method partition is executable and gets right result"
		partitioner.partition(regions,parameters,gridPoints)
		regions.get(Region.Name.QM_ADAPTIVE)==region
	}
}