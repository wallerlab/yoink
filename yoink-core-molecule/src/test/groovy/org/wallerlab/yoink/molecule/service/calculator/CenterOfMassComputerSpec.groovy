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

import org.wallerlab.yoink.api.model.molecular.Element
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.service.DistanceCalculator

import spock.lang.Specification

class CenterOfMassComputerSpec extends Specification {

	def "test method calculate(Set<Molecule> molecules)"(){

		when:"set up a new CenterOfMassComputer"
		    def molecules= new HashSet<MolecularSystem.Molecule>()
		    def m=Mock(MolecularSystem.Molecule)
		    def a1=Mock(MolecularSystem.Molecule.Atom)
		    def a2=Mock(MolecularSystem.Molecule.Atom)

		    def coordinate1 = SimpleVector3DFactory.staticCreate(0.0,0.0,1.0)
		    def coordinate2 = SimpleVector3DFactory.staticCreate(0.0,0.0,-1.0)

		    a1.getCoordinate()>>coordinate1
		    a1.getElement()>>Element.H
		    a2.getCoordinate()>>coordinate2
		    a2.getElement()>>Element.H
		    m.getAtoms()>>[a1,a2]
		    molecules.add(m)

            def calculator = new DistanceCalculator()

		then:"get the center of mass of molecules"
		    def COM =  calculator.centerOfMass(molecules)
		    Math.abs((COM.getCoords().getEntry(0)))<=1.0E-5
	    	Math.abs((COM.getCoords().getEntry(1)))<=1.0E-5
		    Math.abs((COM.getCoords().getEntry(2)))<=1.0E-5
	}

}
