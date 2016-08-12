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

import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.math.Vector

import spock.lang.Specification

class SimpleAtomSpec extends Specification {


	def "test constructor SimpleAtom(int index, Element elementType, Coord coordinate)"(){
		def coordinate=Mock(Vector)
		def elementType=Element.B
		def index=1

		when:"make a new atom using constructor"
		def atom=new SimpleAtom(index,elementType,coordinate);

		then:"assert the value of getters"
		assert atom instanceof SimpleAtom
		assert atom.toString() instanceof String
		atom.getIndex()==1
		atom.getElement()==elementType
		atom.getCoordinate()==coordinate
	}


	def "test coordinate in simple atom "(){
		def coordinate=Mock(Vector)
		coordinate.getX()>>1.0
		coordinate.getY()>>2.0
		coordinate.getZ()>>3.0
		def elementType=Element.B
		def index=1
		def atom=new SimpleAtom(index,elementType,coordinate);

		atom.getCoordinate()>>coordinate

		when:"make a new atom using constructor"
		then:"assert the value of x/y/z coordinate getters"
		atom.getCoordinate().getX()==1.0
		atom.getCoordinate().getY()==2.0
		atom.getCoordinate().getZ()==3.0
	}
}
