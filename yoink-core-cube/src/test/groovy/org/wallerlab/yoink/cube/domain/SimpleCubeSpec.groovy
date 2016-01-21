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
package org.wallerlab.yoink.cube.domain

import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;

import spock.lang.Specification;

class SimpleCubeSpec extends Specification{

	def "cube origin"(){
		when:
		def cube = new SimpleCube()
		def coord = Mock(Coord)
		then:
		cube.setGridOrigin(coord)
		cube.getGridOrigin()==coord
	}

	def "cube numberOfXYZSteps"(){
		when:
		def cube = new SimpleCube()
		int[] numberOfXYZSteps=[10, 10, 10]
		then:
		cube.setNumberOfXYZSteps(numberOfXYZSteps)
		cube.getNumberOfXYZSteps()==[10, 10, 10]
	}

	def"cube xyzStepSize Constructor"(){
		when:
		double[] xyzStepSize=[0.1, 0.1, 0.1]
		def cube = new SimpleCube(xyzStepSize)

		then:
		Math.abs(cube.getXyzStepSize()[0]-0.18897259885789)<=1.0E-5
	}

	def "cube moleucles"(){
		when:
		def cube = new SimpleCube()
		def m1=Mock(Molecule)
		def a1=Mock(Atom)
		m1.getAtoms()>>[a1]
		def m2=Mock(Molecule)
		def a2=Mock(Atom)
		m2.getAtoms()>>[a2]
		Set<Molecule> mSet=[m1, m2]
		then:
		cube.setMolecules(mSet)
		cube.getMolecules().equals((Set<Molecule>)[m1, m2])
		cube.getAtoms().equals((Set<Atom>)[a1, a2])
	}

	def "cube size"(){
		when:
		def cube = new SimpleCube()
		then:
		cube.setSize(10)
		cube.getSize()==10
		when:
		cube = new SimpleCube(10)
		then:
		cube.getSize()==10
		cube.getValues().size()==10
	}

	def "cube name"(){
		when:
		def cube = new SimpleCube()
		then:
		cube.setName("adaptive")
		cube.getName()=="adaptive"
	}

	def "cube densityTypes"(){
		when:
		def cube = new SimpleCube()
		DensityType dt=DensityType.DORI
		List<DensityType> dts =[dt, dt, dt]
		then:
		cube.setDensityTypes(dts)
		cube.getDensityTypes()==[dt, dt, dt]
	}

	def "cube coordinates"(){
		when:
		def cube = new SimpleCube()
		def cd=Mock(Coord)
		List<Coord> cds=[cd, cd , cd]
		then:
		cube.setCoordinates(cds)
		cube.getCoordinates()== [cd, cd , cd]
	}

	def "cube values"(){
		when:
		def cube = new SimpleCube()
		List<Double> d=[1.0, 1.0, 1.0]
		then:
		cube.setValues(d)
		cube.getValues()==[1.0, 1.0, 1.0]
	}
}
