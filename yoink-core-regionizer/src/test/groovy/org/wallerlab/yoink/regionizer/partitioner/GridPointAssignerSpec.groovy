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
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.*

class GridPointAssignerSpec extends Specification{

	def "for neighbour pair test method  assign(Coord tempCoord, Map<Region.Name, Region> regions,Region.Name cubeRegionName)"(){
		def partitioner=new  GridPointAssigner()
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def tempCoord=Mock(Coord)
		def regions=Mock(Map)
		def region=Mock(Region)
		def cubeRegionName
		region.containsall(_)>>(boolean)false
		region.getMolecules()>>(Set<Molecule>)[m1, m2]
		regions.get(_)>>region
		def properties =new HashMap<String, Object>()
		properties.put("twoClosestMolecules", (Set<Molecule>)[m1, m2])
		def voronoiCalculator=Mock(Calculator)
		voronoiCalculator.calculate(_,_)>>properties
		partitioner.voronoiCalculator=voronoiCalculator

		when:"region name is ADAPTIVE_SEARCH"
		cubeRegionName=Region.Name.SYSTEM
		then:"thie size of data  method assign returns is 2"
		partitioner.assign(tempCoord, regions,cubeRegionName).get("twoClosestMolecules").size()==2

		when:"region name is ADAPTIVE_SEARCH"
		cubeRegionName=Region.Name.ADAPTIVE_SEARCH
		then:"thie size of data  method assign returns is 2"
		partitioner.assign(tempCoord, regions,cubeRegionName)
				.get("twoClosestMolecules").size()==2
	}

	def "when not neighbour pair,test method  assign(),cube region name not system"(){
		def partitioner=new  GridPointAssigner()
		def m1=Mock(Molecule)
		def tempCoord=Mock(Coord)
		def regions=Mock(Map)
		def region=Mock(Region)
		def cubeRegionName
		region.containsall(_)>>(boolean)false
		region.getMolecules()>>(Set<Molecule>)[m1]
		regions.get(_)>>region
		def properties =new HashMap<String, Object>()
		properties.put("twoClosestMolecules", (Set<Molecule>)[m1])
		def voronoiCalculator=Mock(Calculator)
		voronoiCalculator.calculate(_,_)>>properties
		partitioner.voronoiCalculator=voronoiCalculator


		when:"region name is ADAPTIVE_SEARCH"
		cubeRegionName=Region.Name.ADAPTIVE_SEARCH
		then:"method assign returs empty properties"
		partitioner.assign(tempCoord, regions,cubeRegionName).isEmpty()==true
	}


	def "when not neighbour pair,test method  assign(),cube region name is system"(){
		def partitioner=new  GridPointAssigner()
		def m1=Mock(Molecule)
		def tempCoord=Mock(Coord)
		def regions=Mock(Map)
		def region=Mock(Region)
		def cubeRegionName
		region.containsall(_)>>(boolean)false
		region.getMolecules()>>(Set<Molecule>)[m1]
		regions.get(_)>>region
		def properties =new HashMap<String, Object>()
		properties.put("twoClosestMolecules", (Set<Molecule>)[m1])
		def voronoiCalculator=Mock(Calculator)
		voronoiCalculator.calculate(_,_)>>properties
		partitioner.voronoiCalculator=voronoiCalculator

		when:"region name is SYSTEM"
		cubeRegionName=Region.Name.SYSTEM
		then:"method assign returs empty properties"
		partitioner.assign(tempCoord, regions,cubeRegionName).isEmpty()==true
	}
}

