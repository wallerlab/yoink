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

class SingleExponentialDecayDetectorPartitionerSpec extends Specification{
	def "test method partition"(){

		def regions=Mock(Map)
		def parameters=Mock(Map)
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def grid1=Mock(GridPoint)
		def grid2=Mock(GridPoint)
		grid1.getTwoClosestMolecules()>>[m1]
		grid2.getTwoClosestMolecules()>>[m2]
		grid1.getTwoClosestAtoms()>>[Mock(Atom)]
		grid2.getTwoClosestAtoms()>>[Mock(Atom)]
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

		def  atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(_,_)>>(double)1.0E-1

		def singleExponentialDecayDetectorComputer=Mock(Computer)
		singleExponentialDecayDetectorComputer.calculate(_)>>(double)0.99

		def atomicDensityRatioCalculator=Mock(Calculator)
		atomicDensityRatioCalculator.calculate(_,_)>>(double)1

		parameters.get(JobParameter.SEDD)>>(double)4
		parameters.get(JobParameter.DENSITY_SEDD)>>(double)1.0E-4
		parameters.get(JobParameter.DENSITY_RATIO_MIN)>>(double)0.2
		parameters.get(JobParameter.DENSITY_RATIO_MAX)>>(double)5
		parameters.get(JobParameter.REGION_CUBE)>>Region.Name.ADAPTIVE_SEARCH

		when:"set up a new sedd partitioner"
		def partitioner=new SingleExponentialDecayDetectorPartitioner()
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.densityPropertiesCalculator=densityPropertiesCalculator
		partitioner.atomDensityCalculator= atomDensityCalculator
		partitioner.singleExponentialDecayDetectorComputer=singleExponentialDecayDetectorComputer
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.atomicDensityRatioCalculator=atomicDensityRatioCalculator

		then:"it is executalble and gets right result"
		partitioner.partition(regions,parameters,gridPoints)
		regions.get(Region.Name.QM_ADAPTIVE)==region
	}
}
