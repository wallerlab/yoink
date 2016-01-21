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
package org.wallerlab.yoink.molecular.service.calculator

import org.wallerlab.yoink.math.linear.CommonsVector3D

import spock.lang.Specification

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
class TwoAtomsDistanceCalculatorSpec extends Specification {

	def "test method calculate(Atom atom1, Atom atom2) "(){

		def coordinate1=Mock(Coord)
		coordinate1.getCoords()>>new CommonsVector3D(0,0,0);
		def atom1=Mock(Atom)
		atom1.getCoordinate()>>coordinate1
		def coordinate2=Mock(Coord)
		coordinate2.getCoords()>>new CommonsVector3D(1,2,2);
		def atom2=Mock(Atom)
		atom2.getCoordinate()>>coordinate2
		when:"calulate distance bwtween two atoms"
		def calculator=new TwoAtomsDistanceCalculator()
		double distance=calculator.calculate( atom1,atom2)
		then:"assert distance value"
		assert distance==3
	}
}
