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
import org.wallerlab.yoink.api.model.molecular.Element
import org.wallerlab.yoink.api.service.molecule.Calculator
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.math.Matrix
import org.wallerlab.yoink.density.data.RadialGridReader
import org.wallerlab.yoink.density.domain.ExponentialFit
import org.wallerlab.yoink.density.domain.SimpleRadialGrid
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.domain.SimpleCoord
import spock.lang.Ignore
import spock.lang.Specification

class DensityCalculatorSpec extends Specification {

	def calculator

	def m1
	def atoms1
	def atom1
	def e1
	def coord1

	def m2
	def atoms2
	def atom2
	def e2
	def coord2

	def currentCoord

	def setup(){

		calculator= new SimpleDensityCalculator()

		//Molecule 1
		atom1=Mock(MolecularSystem.Molecule.Atom)
		e1 = Element.C
		atom1.getElement() >> e1
		m1=Mock(MolecularSystem.Molecule)
		atoms1 = [atom1] as Set
		m1.getAtoms()>> atoms1

		//Molecule 2
		atom2=Mock(MolecularSystem.Molecule.Atom)
		e2 = Element.C
		atom2.getElement() >> e2
		m2=Mock(MolecularSystem.Molecule)
		atoms2 = [atom2] as Set
		m2.getAtoms()>>atoms2

		currentCoord = new SimpleCoord(SimpleVector3DFactory.staticCreate(0.0,0.0,0.0))

	}

	def"test method calculate(currentCoord,molecules), density is not close zero"(){

		given:"set up DensityCalculator"
		    coord1 = SimpleVector3DFactory.staticCreate(x1,y1,z1)
		    coord2 = SimpleVector3DFactory.staticCreate(x2,y2,z2)
		    atom1.getCoordinate() >> coord1
		    atom2.getCoordinate() >> coord2
		    def molecules = [m1, m2] as Set

		expect:"assert density value"
		    calculator.electronic(currentCoord, molecules) >= expectedDensity

		where:
		     x1 |  y1 | z1 |   x2 |   y2 | z2   | expectedDensity
		    0.1 | 0.1 | 0.1| -0.1 | -0.1 | -0.1 | 1.0E-6

	}

	def"test method calculate(currentCoord,molecules), density is very close zero"(){

		given:"atomic density is smaller than default density value"
		    coord1 = SimpleVector3DFactory.staticCreate(x1,y1,z1)
		    coord2 = SimpleVector3DFactory.staticCreate(x2,y2,z2)
		    atom1.getCoordinate() >> coord1
		    atom2.getCoordinate() >> coord2
		    def molecules = [m1, m2] as Set

		expect:"assert density value"
		   Math.abs(calculator.electronic(currentCoord, molecules)-1.0E-30) <= expectedDensity

		where:
		    x1  |  y1 | z1 |   x2 |   y2 |  z2  | expectedDensity
		   100 | 100 | 100| -100 | -100 | -100 | 1.0E-40

	}

	def"test method calculate(coord, atoms), density is not close zero"(){

		given:"set up DensityCalculator"
		coord1 = SimpleVector3DFactory.staticCreate(x1,y1,z1)
		coord2 = SimpleVector3DFactory.staticCreate(x2,y2,z2)
		atom1.getCoordinate() >> coord1
		atom2.getCoordinate() >> coord2
		def atoms = [atom1, atom2] as Set

		expect:"assert density value"
		calculator.atomic(currentCoord, atoms) >= expectedDensity

		where:
		x1 |  y1 | z1 |   x2 |   y2 | z2   | expectedDensity
		0.1 | 0.1 | 0.1| -0.1 | -0.1 | -0.1 | 1.0E-6

	}


	def"molecular ratio"(){
		given:"set up AtomicDensityRatioCalculator for two known atoms"
		coord1 = SimpleVector3DFactory.staticCreate(x1,y1,z1)
		coord2 = SimpleVector3DFactory.staticCreate(x2,y2,z2)
		atom1.getCoordinate() >> coord1
		atom2.getCoordinate() >> coord2
		MolecularSystem.Molecule[] molecules = new MolecularSystem.Molecule[2]
		molecules[0] = m1
		molecules[1] = m2


		expect:"assert ratio value"
		calculator.ratio(currentCoord,molecules) == expectedRatio

		where:
		x1  |  y1 | z1 |   x2   |   y2   |  z2    | expectedRatio
		100 | 100 | 100| -100   | -100   | -100   | 1.0
		100 | 100 | 100| -50    | -50    | -50    | 2.3269039548004824E-69
		1   | 1   |   1| -100   | -100   | -100   | 7.830363182248395E135

	}


	def"atomic ratio"(){
		given:"set up AtomicDensityRatioCalculator for two known atoms"
		coord1 = SimpleVector3DFactory.staticCreate(x1,y1,z1)
		coord2 = SimpleVector3DFactory.staticCreate(x2,y2,z2)
		atom1.getCoordinate() >> coord1
		atom2.getCoordinate() >> coord2
		MolecularSystem.Molecule.Atom[] atoms = new MolecularSystem.Molecule.Atom[2]
		atoms[0] = atom1
		atoms[1] = atom2


		expect:"assert ratio value"
		   calculator.ratio(currentCoord,atoms) == expectedRatio

		where:
		    x1  |  y1 | z1 |   x2   |   y2   |  z2    | expectedRatio
		    100 | 100 | 100| -100   | -100   | -100   | 1.0
			100 | 100 | 100| -50    | -50    | -50    | 2.3269039548004824E-69
		    1   | 1   |   1| -100   | -100   | -100   | 7.830363182248395E135

	}



	@Ignore
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


		expect:
		Math.abs(calculator.ratio(currentCoord, atom)-1.52625795738137279E-003)<= expectedRatio

		where:
		x1  |  y1 | z1 |   x2   |   y2   |  z2    | expectedRatio
		100 | 100 | 100| -100   | -100   | -100   | 1.0
		100 | 100 | 100| -50    | -50    | -50    | 2.3269039548004824E-69
		1   | 1   |   1| -100   | -100   | -100   | 7.830363182248395E135
	}

}
