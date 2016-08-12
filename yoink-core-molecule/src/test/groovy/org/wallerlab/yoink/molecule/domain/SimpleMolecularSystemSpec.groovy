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


import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import spock.lang.Ignore;
import spock.lang.Specification

class SimpleMolecularSystemSpec extends Specification{


	def "test constructor SimpleMolecularSystem(List<Molecule> molecules)"(){

		def a1=Mock(MolecularSystem.Molecule.Atom)
		def a2=Mock(MolecularSystem.Molecule.Atom)
		def atoms1=[a1, a2] as Set

		def a3=Mock(MolecularSystem.Molecule.Atom)
		def a4=Mock(MolecularSystem.Molecule.Atom)
		def atoms2=[a3, a4] as Set

		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)
		m1.getAtoms()>>atoms1
		m2.getAtoms()>>atoms2

		def  molecules=[m1, m2] as Set

		when:"make a  SimpleMolecularSystem using constructor"
		def ms = new SimpleMolecularSystem( molecules)
		then:"test the return value of getters"
		ms.getMolecules().size()==2
		ms.getAtoms().size()==4
	}
}
