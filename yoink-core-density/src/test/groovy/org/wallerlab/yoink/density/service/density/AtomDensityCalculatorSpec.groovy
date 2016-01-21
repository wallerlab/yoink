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
package org.wallerlab.yoink.density.service.density

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.density.service.density.AtomDensityCalculator
import org.wallerlab.yoink.math.config.MathConfig;

import spock.lang.Specification

class AtomDensityCalculatorSpec extends Specification {


	def "test method  calculate(Coord currentCoord, Atom atom) "(){
		given:
		def atom=Mock(Atom)
		atom.getElementType()>>Element.H
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord, atom)>>Math.sqrt(12)

		when:"make a new AtomDensityCalculator"
		def calculator= new AtomDensityCalculator()
		calculator.distanceCalculator=distanceCalculator

		then:"assert density value"
		Math.abs(calculator.calculate(currentCoord, atom)-0.40223E-3)<=1.0E-5
	}

	def "test method2  calculate(Coord currentCoord, Atom atom) "(){
		def atom=Mock(Atom)
		atom.getElementType()>>Element.H
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord, atom)>>(double)1.0

		when:"make a new AtomDensityCalculator"
		def calculator= new AtomDensityCalculator()
		calculator.distanceCalculator=distanceCalculator

		then:"assert density value"
		Math.abs(calculator.calculate(currentCoord, atom)-0.04248)<=1.0E-5
	}
}
