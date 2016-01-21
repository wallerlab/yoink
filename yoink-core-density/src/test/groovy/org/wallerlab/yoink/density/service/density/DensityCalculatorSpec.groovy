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

import org.wallerlab.yoink.density.service.density.DensityCalculator

import spock.lang.Specification

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;

class DensityCalculatorSpec extends Specification {

	def"test method calculate(currentCoord,molecules), density is not close zero"(){

		given:
		def atom1=Mock(Atom)
		def m1=Mock(Molecule)
		m1.getAtoms()>>[atom1]

		def atom2=Mock(Atom)
		def m2=Mock(Molecule)
		m2.getAtoms()>>[atom2]

		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, atom1)>>(double)1.0
		atomDensityCalculator.calculate(currentCoord, atom2)>>(double)2.0

		when:"set up DensityCalculator"
		def calculator= new DensityCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"assert density value"
		double density=calculator.calculate(currentCoord, ((Set<Molecule>)[m1, m2]))
		assert Math.abs(density-3)<=1.0E-6
	}


	def"test method calculate(currentCoord,molecules), density is very close zero"(){
		given:"atomic density is smaller than default density value"
		def atom1=Mock(Atom)
		def m1=Mock(Molecule)
		m1.getAtoms()>>[atom1]

		def atom2=Mock(Atom)
		def m2=Mock(Molecule)
		m2.getAtoms()>>[atom2]

		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, atom1)>>(double)1.0E-31
		atomDensityCalculator.calculate(currentCoord, atom2)>>(double)2.0E-31

		when:"set up DensityCalculator"
		def calculator= new DensityCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"assert density value"
		double density=calculator.calculate(currentCoord, ((Set<Molecule>)[m1, m2]))
		assert Math.abs(density-1.0E-30)<=1.0E-40
	}
}
