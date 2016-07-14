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
package org.wallerlab.yoink.region.domain

import org.wallerlab.yoink.api.model.molecule.Element
import org.wallerlab.yoink.api.model.molecule.Atom
import org.wallerlab.yoink.api.model.molecule.Coord
import org.wallerlab.yoink.api.model.molecule.Molecule
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.domain.SimpleCoordFactory
import org.wallerlab.yoink.molecule.domain.SimpleMolecule
import org.wallerlab.yoink.api.service.math.Vector;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

import spock.lang.Specification

class SimpleRegionSpec extends Specification{


	def"test constructor SimpleRegion(Name, Set<Molecule>)"(){

		def molecule=Mock(Molecule)
		def molecules = [molecule]
		def atom = Mock(Atom)
		molecule.getAtoms()>>[atom]

		when:"add a molecule"
		def region= new SimpleRegion(MM,molecules)

		then:"get right information about added molecule"
		region.getName()==MM
		region.getSize()==1
		region.getAtoms()==[atom]
	}

	def"test getCenterOfMass"(){
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		def molecules = [Mock(Molecule)]
		def region= new SimpleRegion(MM, molecules)
		def coordinate=Mock(Coord)
		coordinate.getCoords()>>myVector3D.create(1,1,1);
		def a=Mock(Atom)
		a.getCoordinate()>>coordinate
		a.getElementType()>>Element.H
		def m=new SimpleMolecule(1,[a])

		when:
		region.addMolecule(m,1)

		then:
		Math.abs(region.getCenterOfMass().getCoords().getEntry(0)-1)<=1.0E-5
	}

}
