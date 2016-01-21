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
package org.wallerlab.yoink.regionizer.domain


import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecular.domain.SimpleCoord
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory
import org.wallerlab.yoink.molecular.domain.SimpleMolecule
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.model.regionizer.Region
import org.wallerlab.yoink.api.service.math.Vector;

import spock.lang.Specification
class SimpleRegionSpec extends Specification{


	def"test constructor SimpleRegion(name)"(){

		def region= new SimpleRegion(Region.Name.MM)
		def m=Mock(Molecule)
		Map<Molecule, Integer> molecularMap=Mock(Map)
		def a=Mock(Atom)
		m.getAtoms()>>[a]

		when:"add a molecule"
		region.addMolecule(m,1)
		Set<Molecule> ms=new HashSet<Molecule>()
		ms.add(m)
		then:"get right information about added molecule"
		region.getName()==Region.Name.MM
		region.getSize()==1
		region.getAtoms()==[a]
		region.containsAll(ms)
		region.addAll(molecularMap)

		when:"set the value of molecularMap"
		region.setMolecularMap(molecularMap)
		then:"get the value of molecularMap"
		region.getMolecularMap()==molecularMap
	}


	def"test methods : changeMolecularId and getCenterOfMass"(){
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		def region= new SimpleRegion(Region.Name.MM)
		def coordinate=Mock(Coord)
		coordinate.getCoords()>>myVector3D.create(1,1,1);
		def a=Mock(Atom)
		a.getCoordinate()>>coordinate
		a.getElementType()>>Element.H
		def m=new SimpleMolecule(1,[a])
		def centerOfMassComputer=Mock(Computer)
		centerOfMassComputer.calculate(_)>>simpleCoordFactory.create((double[])[1, 1, 1])
		region.setCenterOfMassComputer(centerOfMassComputer)

		when:"change molecular name using the region name"
		region.addMolecule(m,1)
		region.changeMolecularId()
		then:"molecular name equals region name"
		Math.abs(region.getCenterOfMass().getCoords().getEntry(0)-1)<=1.0E-5
		m.getName()==Region.Name.MM

		when:"change molecular name using given name"
		region.changeMolecularId(Region.Name.QM)
		then:"molecular name equals given name"
		m.getName()==Region.Name.QM
	}
}
