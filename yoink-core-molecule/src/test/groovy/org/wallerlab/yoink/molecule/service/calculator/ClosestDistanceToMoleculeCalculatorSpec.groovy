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

import org.wallerlab.yoink.api.service.molecule.Calculator
import org.wallerlab.yoink.molecule.service.DistanceCalculator
import spock.lang.Ignore;
import spock.lang.Specification

@Ignore
class ClosestDistanceToMoleculeCalculatorSpec extends Specification{

	def "test method calculate(Coord gridCoord, Molecule molecule) "(){
		def coordinate=Mock(Coord)
		def atom1=Mock(MolecularSystem.Molecule.Atom)
		def atom2=Mock(MolecularSystem.Molecule.Atom)
		def m=Mock(MolecularSystem.Molecule)
		m.getAtoms()>>[atom1,atom2]
		def distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(coordinate,atom1)>> 1.0d
		distanceCalculator.calculate(coordinate,atom2)>> 2.0d
		
		when:"ratio distance between atom and coordinate"
		def calculator=new DistanceCalculator()
		calculator.distanceCalculator=distanceCalculator
		double distance=calculator.calculate( coordinate,m)
		then:"assert the distance value"
		assert distance==1.0
	}
}
