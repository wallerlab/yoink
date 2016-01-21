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

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.math.linear.CommonsVector3D

import spock.lang.Specification

class DistanceCalculatorSpec extends Specification{

	def "test method calculate(Coord gridCoord, Atom atom) "(){
		def coordinate=Mock(Coord)
		def atom=Mock(Atom)
		coordinate.getCoords()>>new CommonsVector3D(1,1,1);
		atom.getCoordinate()>>coordinate
		when:"calculate distance between atom and coordinate"
		def calculator=new DistanceCalculator()
		double distance=calculator.calculate( coordinate,atom)
		then:"assert the distance value"
		assert distance==0
	}
}
