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
package org.wallerlab.yoink.molecule.service.calculator

import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.domain.SimpleCoord
import org.wallerlab.yoink.molecule.service.DistanceCalculator

import spock.lang.Specification

class ClosestDistanceToMoleculeCalculatorSpec extends Specification{

	def "test method calculate(Coord gridCoord, Molecule molecule) "(){
		def coordinate=new SimpleCoord(SimpleVector3DFactory.staticCreate(0,0,0))
		def atom1=Mock(MolecularSystem.Molecule.Atom)
		def atom2=Mock(MolecularSystem.Molecule.Atom)
		def m=Mock(MolecularSystem.Molecule)
		m.getAtoms()>>[atom1,atom2]
		atom1.getCoordinate() >> SimpleVector3DFactory.staticCreate(0,0,1)
		atom2.getCoordinate() >> SimpleVector3DFactory.staticCreate(10,10,10)

		
		when:"ratio distance between atom and coordinate"
		def calculator = new DistanceCalculator()
		double distance=calculator.closest( coordinate,m)
		then:"assert the distance value"
		assert distance==1.0
	}
}
