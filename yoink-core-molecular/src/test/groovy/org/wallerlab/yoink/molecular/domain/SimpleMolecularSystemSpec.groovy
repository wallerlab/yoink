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
package org.wallerlab.yoink.molecular.domain

import java.util.List;

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Molecule;

import spock.lang.Specification

class SimpleMolecularSystemSpec extends Specification{


	def "test constructor SimpleMolecularSystem(List<Molecule> molecules)"(){

		def a=Mock(Atom)
		List<Atom> atoms=[a, a]
		def m=Mock(Molecule)
		m.getAtoms()>>atoms
		List<Molecule> molecules=[m, m]

		when:"make a  SimpleMolecularSystem using constructor"
		def ms=new SimpleMolecularSystem( molecules)
		then:"test the return value of getters"
		ms.getMolecules().size()==2
		ms.getAtoms().size()==4
	}
}
