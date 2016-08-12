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
package org.wallerlab.yoink.molecule.domain


import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.adaptive.Region;

import spock.lang.Specification

class SimpleMoleculeSpec extends Specification{

	def "test constructor SimpleMolecule(int index, List<Atom> atoms) "(){
		def atom1=Mock(MolecularSystem.Molecule.Atom)
		def atom2=Mock(MolecularSystem.Molecule.Atom)

		def index=1
		Set<MolecularSystem.Molecule.Atom> atoms=[atom1, atom2] as Set

		when:"make a new SimpleMolecule using constructor and setters"
		def m=new SimpleMolecule( index,atoms)
		m.setName(Region.Name.QM)
		def centerOfMass=Mock(Coord)
		m.setCenterOfMass(centerOfMass)
		then:"test ther return value of getters"
		assert m.toString() instanceof  String
		m.getIndex()==1
		m.getAtoms()==atoms
		m.getAtoms().size()==2
		m.getName()==Region.Name.QM
		m.getCenterOfMass()==centerOfMass
	}
}
