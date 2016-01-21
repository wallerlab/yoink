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
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.regionizer.Region;

import spock.lang.Specification

class SimpleMoleculeSpec extends Specification{

	def "test constructor SimpleMolecule(int index, List<Atom> atoms) "(){
		def atom=Mock(Atom)
		def index=1
		List<Atom> atoms=[atom, atom]
		atoms
		when:"make a new SimpleMolecule using constructor and setters"
		def m=new SimpleMolecule( index,atoms)
		m.setName(Region.Name.QM)
		def centerOfMass=Mock(Coord)
		m.setCenterOfMass(centerOfMass)
		then:"test ther return value of getters"
		m.getIndex()==1
		m.getAtoms()==atoms
		m.getAtoms().size()==2
		m.getName()==Region.Name.QM
		m.getCenterOfMass()==centerOfMass
	}
}
