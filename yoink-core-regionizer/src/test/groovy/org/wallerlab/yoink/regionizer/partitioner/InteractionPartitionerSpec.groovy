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
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.regionizer.*;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegionFactory

class InteractionPartitionerSpec extends Specification{
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
		def partitioner=new InteractionPartitioner()
		partitioner.singleRegionizerService=singleRegionizerService
		partitioner.simpleRegionFactory=simpleRegionFactory

		then:"it is executable and no errors thrown"
		partitioner.partition(regions,parameters,gridPoints)
	}
}
