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
package org.wallerlab.yoink.cube.domain


import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem

import spock.lang.Specification;

class SimpleGridPointSpec extends Specification{
	def "coordinate,densityValues,indexInCube"(){
		when:"make a new new SimpleVoronoiPoint()"
		def gp=new SimpleVoronoiPoint()
		def coordinate =Mock(Coord)
		then:"test setters and getters"
		gp.setCoordinate(coordinate)
		gp.setIndexInCube(0)
		gp.getCoordinate()==coordinate
		gp.getIndex()==0
	}

	def "properties in  SimpleGridPoint"(){
		when:"make a new SimpleVoronoiPoint()"
		def gp=new SimpleVoronoiPoint()
		def properties=new HashMap<String,Object>()
		then:"test properties setter and getter"
		gp.setProperties(properties)
		gp.getProperties()==properties

		when:"Mock List for property value"
		def d=Mock(List)
		then:"no error thrown"
		gp.addProperty("d",d)
		gp.getProperty("d")==d

		when:"Mock Set<Molecule> for property value"
		def a1=Mock(MolecularSystem.Molecule.Atom)
		def a2=Mock(MolecularSystem.Molecule.Atom)
		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)
		Set<MolecularSystem.Molecule.Atom> aSet= new HashSet<MolecularSystem.Molecule.Atom>([a1, a2])
		m1.getAtoms()>>[a1]
		m2.getAtoms()>>[a2]
		Set<MolecularSystem.Molecule> mSet= new HashSet<MolecularSystem.Molecule>([m1, m2])
		properties.put("twoClosestAtoms", aSet)
		properties.put("twoClosestMolecules", mSet)
		gp.setProperties(properties)
		then:"test getters for properties' value"
		gp.getTwoClosestAtoms().equals((Set<MolecularSystem.Molecule.Atom>)[a1, a2])
		gp.getTwoClosestMolecules().equals((Set<MolecularSystem.Molecule>)[m1, m2])
		gp.getAtomsInTwoClosestMolecules().equals((Set<MolecularSystem.Molecule.Atom>)[a1, a2])
	}
}
