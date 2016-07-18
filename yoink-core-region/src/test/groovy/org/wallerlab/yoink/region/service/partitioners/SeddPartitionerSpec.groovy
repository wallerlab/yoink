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
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.region.Regionizer
import spock.lang.Specification;


import org.wallerlab.yoink.api.model.VoronoiPoint;
import org.wallerlab.yoink.api.model.DensityPoint;

import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.molecule.service.Computer

class SeddPartitionerSpec extends Specification{
	def "test method partition"(){

		def regions=Mock(Map)
		def parameters=Mock(Map)
		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)
		def grid1=Mock(VoronoiPoint)
		def grid2=Mock(VoronoiPoint)
		grid1.getTwoClosestMolecules()>>[m1]
		grid2.getTwoClosestMolecules()>>[m2]
		grid1.getTwoClosestAtoms()>>[Mock(MolecularSystem.Molecule.Atom)]
		grid2.getTwoClosestAtoms()>>[Mock(MolecularSystem.Molecule.Atom)]
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

		def  atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(_,_)>>(double)1.0E-1

		def singleExponentialDecayDetectorComputer=Mock(Computer)
		singleExponentialDecayDetectorComputer.calculate(_)>>(double)0.99

		def atomicDensityRatioCalculator=Mock(Calculator)
		atomicDensityRatioCalculator.calculate(_,_)>>(double)1

		parameters.get(Job.JobParameter.SEDD)>>(double)4
		parameters.get(Job.JobParameter.DENSITY_SEDD)>>(double)1.0E-4
		parameters.get(Job.JobParameter.DENSITY_RATIO_MIN)>>(double)0.2
		parameters.get(Job.JobParameter.DENSITY_RATIO_MAX)>>(double)5
		parameters.get(Job.JobParameter.REGION_CUBE)>>Region.Name.ADAPTIVE_SEARCH

		when:"set up a new sedd partitioner"
		def partitioner=new DensityPartitioner()
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.descriptor=densityPropertiesCalculator
		partitioner.atomicDensity= atomDensityCalculator
		partitioner.seddComputer=singleExponentialDecayDetectorComputer
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.atomicDensityRatio=atomicDensityRatioCalculator

		then:"it is executalble and gets right result"
		partitioner.partition(regions,parameters,gridPoints)
		regions.get(Region.Name.QM_ADAPTIVE)==region
	}
}
