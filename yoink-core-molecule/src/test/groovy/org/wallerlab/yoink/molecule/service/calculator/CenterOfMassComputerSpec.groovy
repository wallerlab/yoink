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


import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.molecular.Element
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import org.wallerlab.yoink.api.service.math.Vector
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.domain.SimpleCoordFactory
import org.wallerlab.yoink.molecule.service.DistanceCalculator
import spock.lang.Specification

class CenterOfMassComputerSpec extends Specification {
	def "test method  calculate(Set<Molecule> molecules)"(){

		def molecules= new HashSet<MolecularSystem.Molecule>()
		def m=Mock(MolecularSystem.Molecule)
		def coordinate=Mock(Coord)
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		coordinate.getCoords()>>myVector3D.create(1,1,1);
		def a=Mock(MolecularSystem.Molecule.Atom)
		a.getCoordinate()>>coordinate
		a.getElementType()>>Element.H
		m.getAtoms()>>[a]
		molecules.add(m)
		def simpleCoordFactory=new SimpleCoordFactory()
		simpleCoordFactory.myVector3D=myVector3D

		when:"set up a new CenterOfMassComputer"
		def COMcomputer= new DistanceCalculator()
		COMcomputer.simpleCoordFactory=simpleCoordFactory
		COMcomputer.vectorFactory=myVector3D
		then:"get the center of mass of molecules"
		def COM =  COMcomputer.calculate(molecules)
		Math.abs((COM.getCoords().getEntry(0)-1))<=1.0E-5
	}
}
