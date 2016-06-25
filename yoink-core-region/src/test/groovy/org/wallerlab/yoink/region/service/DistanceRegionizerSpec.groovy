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
package org.wallerlab.yoink.region.service


import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator
import org.wallerlab.yoink.region.domain.SimpleRegionFactory
import org.wallerlab.yoink.api.service.region.Partitioner;

class DistanceRegionizerSpec extends Specification{
	def "test method regionize(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters)"(){

		def regions=new HashMap<Region.Name,Region>()
		def region=Mock(Region)
		def m=Mock(Molecule)
		region.getCenterOfMass()>>Mock(Coord)
		def region2=Mock(Region)
		def a1=Mock(Atom)
		def m1=Mock(Molecule)
		m1.getAtoms()>>[a1]
		def a2=Mock(Atom)
		def m2=Mock(Molecule)
		m2.getAtoms()>>[a2]
		def a3=Mock(Atom)
		def m3=Mock(Molecule)
		m3.getAtoms()>>[a3]
		region2.getMolecules()>>[m1, m2, m3]

		def distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,m1)>>(double)1.0
		distanceCalculator.calculate(_,m2)>>(double)2.0
		distanceCalculator.calculate(_,m3)>>(double)3.0

		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.QM_CORE,region)
		regions.put(Region.Name.NONQM_CORE,region2)

		def parameters=Mock(Map)
		parameters.get(JobParameter.DISTANCE_QM)>>(double)2.5
		parameters.get(JobParameter.DISTANCE_BUFFER)>>(double)1.0
		parameters.get(JobParameter.PARTITIONER)>>Partitioner.Type.DISTANCE

		def simpleRegionFactory=new SimpleRegionFactory()

		when:"set up a new DistanceRegionizer"
		def regionizer=new DistanceRegionizer()
		regionizer.closestDistanceToMoleculeCalculator=distanceCalculator
		regionizer.regionFactory=simpleRegionFactory

		then:"the new distanceRegionizer is executable and gets right results"
		regionizer.regionize(regions,parameters)
		regions.size()==5
		regions.get(Region.Name.QM_ADAPTIVE).getSize()==2
		regions.get(Region.Name.BUFFER).getSize()==1
	}
}
