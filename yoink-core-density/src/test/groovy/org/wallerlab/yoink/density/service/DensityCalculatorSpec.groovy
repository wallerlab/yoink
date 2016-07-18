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
package org.wallerlab.yoink.density.service

import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.service.molecule.Calculator
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.math.Matrix
import org.wallerlab.yoink.density.data.RadialGridReader
import org.wallerlab.yoink.density.domain.ExponentialFit
import org.wallerlab.yoink.density.domain.SimpleRadialGrid
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory

import spock.lang.Specification

class DensityCalculatorSpec extends Specification {

	def"test method calculate(currentCoord,molecules), density is not close zero"(){

		given:
		def atom1=Mock(MolecularSystem.Molecule.Atom)
		def m1=Mock(MolecularSystem.Molecule)
		m1.getAtoms()>>[atom1]

		def atom2=Mock(MolecularSystem.Molecule.Atom)
		def m2=Mock(MolecularSystem.Molecule)
		m2.getAtoms()>>[atom2]

		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, atom1)>>(double)1.0
		atomDensityCalculator.calculate(currentCoord, atom2)>>(double)2.0

		when:"set up DensityCalculator"
		def calculator= new SimpleDensityCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"assert density value"
		double density=calculator.calculate(currentCoord, ((Set<MolecularSystem.Molecule>)[m1, m2]))
		assert Math.abs(density-3)<=1.0E-6
	}


	def"test method calculate(currentCoord,molecules), density is very close zero"(){
		given:"atomic density is smaller than default density value"
		def atom1=Mock(MolecularSystem.Molecule.Atom)
		def m1=Mock(MolecularSystem.Molecule)
		m1.getAtoms()>>[atom1]

		def atom2=Mock(MolecularSystem.Molecule.Atom)
		def m2=Mock(MolecularSystem.Molecule)
		m2.getAtoms()>>[atom2]

		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, atom1)>>(double)1.0E-31
		atomDensityCalculator.calculate(currentCoord, atom2)>>(double)2.0E-31

		when:"set up DensityCalculator"
		def calculator= new SimpleDensityCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"assert density value"
		double density=calculator.calculate(currentCoord, ((Set<MolecularSystem.Molecule>)[m1, m2]))
		assert Math.abs(density-1.0E-30)<=1.0E-40
	}

	def"molecular density ratio"(){
		given:
		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)
		def a1=Mock(MolecularSystem.Molecule.Atom)
		def a2=Mock(MolecularSystem.Molecule.Atom)
		m1.getAtoms()>>[a1]
		m2.getAtoms()>>[a2]
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, a1)>>(double)1
		atomDensityCalculator.calculate(currentCoord, a2)>>(double)2
		MolecularSystem.Molecule[] neighbours=[m1, m2]

		when:"make a  new MolecularDensityRatioCalculator"
		def calculator= new SimpleDensityCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then :"assert calculated value"
		double ratio=	calculator.ratio(currentCoord,
				neighbours)
		Math.abs(ratio)<=0.5
	}

	def"atomic ratio"(){
		given:
		def atom1=Mock(MolecularSystem.Molecule.Atom)
		def atom2=Mock(MolecularSystem.Molecule.Atom)
		def currentCoord=Mock(Coord)
		MolecularSystem.Molecule.Atom[] atoms=[atom1, atom2]
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> atomDensityCalculator=Mock(Calculator)
		atomDensityCalculator.calculate(currentCoord, atom1)>>(double)1.0
		atomDensityCalculator.calculate(currentCoord, atom2)>>(double)2.0

		when:"set up AtomicDensityRatioCalculator for two known atoms"
		def calculator= new SimpleDensityCalculator()
		calculator.atomDensityCalculator=atomDensityCalculator

		then:"assert ratio value"
		double ratio=calculator.ratio(currentCoord,atoms);
		Math.abs(ratio-0.5)<=1.0E-6
	}

	def "test density from radial grid"(){
		given:
		def grid= new SimpleRadialGrid()
		def reader= new RadialGridReader()
		def myMatrix= new SimpleMatrixFactory()
		myMatrix.matrixType=Matrix.Type.COMMONS
		reader.myMatrix=myMatrix
		reader.read("./src/test/resources/c__lda.wfc",  grid)
		def atom=Mock(MolecularSystem.Molecule.Atom)
		atom.getElementType()>>ExponentialFit.C
		atom.getRadialGrid()>>grid
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord, atom)>>(double)   3.4641016151377544
		when:"make a new AtomDensityCalculator"
		def calculator= new SimpleDensityCalculator()
		calculator.distanceCalculator=distanceCalculator
		then:
		Math.abs(calculator.ratio(currentCoord, atom)-1.52625795738137279E-003)<=1.0E-5
	}

	def "test method  calculate(Coord currentCoord, Atom atom) "(){
		given:
		def atom=Mock(MolecularSystem.Molecule.Atom)
		atom.getElementType()>>ExponentialFit.H
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord, atom)>>Math.sqrt(12)

		when:"make a new AtomDensityCalculator"
		def calculator= new SimpleDensityCalculator()
		calculator.distanceCalculator=distanceCalculator

		then:"assert density value"
		Math.abs(calculator.ratio(currentCoord, atom)-0.40223E-3)<=1.0E-5
	}

	def "test method2  calculate(Coord currentCoord, Atom atom) "(){
		def atom=Mock(MolecularSystem.Molecule.Atom)
		atom.getElementType()>>ExponentialFit.H
		def currentCoord=Mock(Coord)
		Calculator<Double, Coord, MolecularSystem.Molecule.Atom> distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(currentCoord, atom)>>(double)1.0

		when:"make a new AtomDensityCalculator"
		def calculator= new SimpleDensityCalculator()
		calculator.distanceCalculator=distanceCalculator

		then:"assert density value"
		Math.abs(calculator.ratio(currentCoord, atom)-0.04248)<=1.0E-5
	}
}
