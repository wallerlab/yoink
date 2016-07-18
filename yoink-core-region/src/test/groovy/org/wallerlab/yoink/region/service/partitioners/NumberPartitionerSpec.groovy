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

import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import spock.lang.Specification;


import org.wallerlab.yoink.api.model.Coord;

import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator

import static Region.Name.*;
import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;

class  NumberPartitionerSpec extends Specification{

	def "test method regionize(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters)"(){

		def job = Mock(Job)
		def regions=new HashMap<Region.Name,Region>()
		def region=Mock(Region)
		def m=Mock(MolecularSystem.Molecule)
		region.getCenterOfMass() >> Mock(Coord)

		def region2=Mock(Region)
		def a1=Mock(MolecularSystem.Molecule.Atom)
		def m1=Mock(MolecularSystem.Molecule)
		m1.getAtoms()>>[a1]
		def a2=Mock(MolecularSystem.Molecule.Atom)
		def m2=Mock(MolecularSystem.Molecule)
		m2.getAtoms()>>[a2]
		def a3=Mock(MolecularSystem.Molecule.Atom)
		def m3=Mock(MolecularSystem.Molecule)
		m3.getAtoms()>>[a3]
		region2.getMolecules()>>(Set<MolecularSystem.Molecule>)[m1, m2, m3]
		
		def sortedDistancesCalculator=Mock(Calculator)
		def sMap=new HashMap<MolecularSystem.Molecule, Double>()
		sMap.put(m1, (double)0.2)
		sMap.put(m2,  (double)0.2)
		sMap.put(m3,  (double)0.2)
		sortedDistancesCalculator.calculate(_,region2.getMolecules())>>sMap

		def molecularMap=new HashMap<MolecularSystem.Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(QM_CORE,region)
		regions.put(NONQM_CORE,region2)

		def parameters=Mock(Map)
		parameters.get(NUMBER_QM)>> 2
		parameters.get(NUMBER_BUFFER)>> 1
		parameters.get(PARTITIONER)>> NUMBER


		when:"start up a new NumberRegionizer"
		def regionizer=new NumberPartitioner()
		regionizer.distanceCalculator=sortedDistancesCalculator


		then:"the new numberRegionizer is executable and gets right results"
		def results = regionizer.partition(job)
		results.get(QM_ADAPTIVE).size()==2
		results.get(BUFFER).size()==1
	}
}
