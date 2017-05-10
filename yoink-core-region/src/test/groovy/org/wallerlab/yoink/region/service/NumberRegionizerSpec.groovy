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
import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.region.domain.SimpleRegion
import org.wallerlab.yoink.region.service.NumberRegionizer;
import org.wallerlab.yoink.api.service.region.Partitioner;

class NumberRegionizerSpec extends Specification{
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
		region2.getMolecules()>>(Set<Molecule>)[m1, m2, m3]
		
		def sortedDistancesCalculator=Mock(Calculator)
		def sMap=new HashMap<Molecule, Double>()
		sMap.put(m1, (double)0.2)
		sMap.put(m2,  (double)0.2)
		sMap.put(m3,  (double)0.2)
		sortedDistancesCalculator.calculate(_,region2.getMolecules())>>sMap

		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.QM_CORE,region)
		regions.put(Region.Name.NONQM_CORE,region2)
		def parameters=Mock(Map)
		parameters.get(JobParameter.NUMBER_QM)>>(int)2
		parameters.get(JobParameter.NUMBER_BUFFER)>>(int)1
		parameters.get(JobParameter.PARTITIONER)>>Partitioner.Type.NUMBER

		def simpleRegionFactory=Mock(Factory)
		simpleRegionFactory.create(Region.Name.QM_ADAPTIVE)>>new SimpleRegion(Region.Name.QM_ADAPTIVE)
		simpleRegionFactory.create(Region.Name.QM)>>new SimpleRegion(Region.Name.QM)
		simpleRegionFactory.create(Region.Name.BUFFER)>>new SimpleRegion(Region.Name.BUFFER)

		when:"start up a new NumberRegionizer"
		def regionizer=new NumberRegionizer()
		regionizer.sortedDistancesCalculator=sortedDistancesCalculator
		regionizer.simpleRegionFactory=simpleRegionFactory


		then:"the new numberRegionizer is executable and gets right results"
		regionizer.regionize(regions,parameters)
		regions.size()==5
		regions.get(Region.Name.QM_ADAPTIVE).getSize()==2
		regions.get(Region.Name.BUFFER).getSize()==1
	}
}
