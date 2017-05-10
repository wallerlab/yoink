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

import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Element;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.density.service.density.AtomDensityCalculator
import org.wallerlab.yoink.math.config.MathConfig;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory
import org.wallerlab.yoink.molecule.data.RadialGridReader
import org.wallerlab.yoink.molecule.domain.SimpleRadialGrid
import org.wallerlab.yoink.api.service.math.Matrix;
import spock.lang.Specification

class AtomDensityCalculatorSpec extends Specification {

	def "test density from radial grid"(){
		given:
		def grid= new SimpleRadialGrid()
		def reader= new RadialGridReader()
		def myMatrix= new SimpleMatrixFactory()
		myMatrix.matrixType=Matrix.Type.COMMONS
		reader.myMatrix=myMatrix
		reader.read("./src/test/resources/c__lda.wfc",  grid)
		def atom=Mock(Atom)
		atom.getElementType()>>Element.C
		atom.getRadialGrid()>>grid
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord, atom)>>(double)   3.4641016151377544
		when:"make a new AtomDensityCalculator"
		def calculator= new AtomDensityCalculator()
		calculator.distanceCalculator=distanceCalculator
		then:
		 Math.abs(calculator.calculate(currentCoord, atom)-1.52625795738137279E-003)<=1.0E-5
	}

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