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
package org.wallerlab.yoink.region.partitioner

import org.wallerlab.yoink.api.model.batch.JobParameter
import org.wallerlab.yoink.api.service.region.RegionizerMath

import spock.lang.Ignore
import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region
import org.wallerlab.yoink.region.domain.SimpleRegionFactory

class InteractionPartitionerSpec extends Specification{
	@Ignore // why test abstract method?
	def "test method partition"(){

		def regions=Mock(Map)
		def parameters=Mock(Map)
		def grid1=Mock(GridPoint)
		def grid2=Mock(GridPoint)
		grid1.getTwoClosestMolecules()>>[Mock(Molecule)]
		grid2.getTwoClosestMolecules()>>[Mock(Molecule)]
		def gridPoints=[grid1, grid2]
		def singleRegionizerService=Mock(RegionizerMath)
		def region=Mock(Region)
		region.getName()>>Region.Name.QM_ADAPTIVE
		singleRegionizerService.regionize(_,_)>>region
		def simpleRegionFactory=new SimpleRegionFactory()

		when:"set up a new InteractionPartitioner"
		def partitioner=new InteractionPartitioner(Region){
			@Override
			public Region checkCriteria(Map<Region.Name, Region> regionsNames,
						  Region regionForSpec, GridPoint gridPoint,
						  Set<Molecule> neighbours,
						  Map<JobParameter, Object> parametersForSpec){
				return;
			}
		}
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.simpleRegionFactory=simpleRegionFactory

		then:"it is executable and no errors thrown"
		partitioner.partition(regions,parameters,gridPoints)
	}
}
