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

import org.wallerlab.yoink.api.model.molecular.Element
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.density.domain.ExponentialFit

import org.wallerlab.yoink.api.model.Coord

import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.domain.SimpleCoordFactory
import org.wallerlab.yoink.molecule.domain.SimpleMolecule
import org.wallerlab.yoink.api.service.math.Vector
import spock.lang.Ignore;

import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;

import spock.lang.Specification

class SimpleRegionSpec extends Specification{


	def"test constructor SimpleRegion(Name, Set<Molecule>)"(){

		def molecule=Mock(MolecularSystem.Molecule)
		def molecules = [molecule] as Set
		def atom = Mock(MolecularSystem.Molecule.Atom)
		molecule.getAtoms()>>[atom]

		when:"add a molecule"
		def region= new SimpleRegion(MM,molecules)

		then:"get right information about added molecule"
		region.getName()==MM
		region.getSize()==1
		region.getAtoms()==[atom] as Set
	}

	@Ignore
	def"test getCenterOfMass"(){

		def a=Mock(MolecularSystem.Molecule.Atom)
		def coordinate=Mock(Coord)
		coordinate.getCoords()>> SimpleVector3DFactory.staticCreate(1, 1, 1);
		a.getCoordinate()>>coordinate
		a.getElement()>>Element.H
		def m=new SimpleMolecule(1,[a,a] as Set)
		m.getAtoms()>>a
		def molecules = [Mock(MolecularSystem.Molecule)] as Set
		molecules.add(m)

		when:
		def region= new SimpleRegion(MM, molecules)
		then:
		Math.abs(region.getCenterOfMass().getCoords().getEntry(0)-1)<=1.0E-5
	}

}
