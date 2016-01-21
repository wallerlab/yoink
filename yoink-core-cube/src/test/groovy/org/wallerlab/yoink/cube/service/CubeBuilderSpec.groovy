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
package org.wallerlab.yoink.cube.service

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.cube.domain.SimpleCube
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecular.domain.SimpleCoord
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory

import spock.lang.Specification;
class CubeBuilderSpec extends Specification{


	def"build(cube, moleucles),test the return type"(){

		def cube= Mock(Cube)
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		def m1=Mock(Molecule)
		def a1=Mock(Atom)
		m1.getAtoms()>>[a1]
		def m2=Mock(Molecule)
		def a2=Mock(Atom)
		m2.getAtoms()>>[a2]
		Set<Molecule> mSet=[m1, m2]
		double[] d=[0.01, 0.01, 0.01]
		cube.getXyzStepSize()>>d
		cube.getDensityTypes()>>[]
		cube.getGridOrigin()>Mock(Coord)
		cube.getNumberOfXYZSteps()>>[10, 10, 10]
		cube.getSize()>>1000
		Calculator<Coord, int[], Cube> coordInCubeCalculator =Mock(Calculator)
		coordInCubeCalculator.calculate(_, cube)>>simpleCoordFactory.create((double[])[1, 2, 3])

		when:"make a new CubeBuilder"
		def builder= new CubeBuilderImpl()
		builder.simpleCoordFactory=simpleCoordFactory
		builder.coordInCubeCalculator=coordInCubeCalculator
		then:"assert the return type"
		assert builder.build(d, mSet) instanceof  Cube
	}

	def"build(cube, moleucles),test the effect of DensityTypes"(){

		double[] d=[0.05, 0.05, 0.05]
		def cube= new SimpleCube(d)
		def builder= new CubeBuilderImpl()
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		def m1=Mock(Molecule)
		def a1=Mock(Atom)
		a1.getX3()>>0.0
		a1.getY3()>>0.0
		a1.getZ3()>>0.0
		m1.getAtoms()>>[a1]
		def m2=Mock(Molecule)
		def a2=Mock(Atom)
		a2.getX3()>>0.1
		a2.getY3()>>0.1
		a2.getZ3()>>0.1
		m2.getAtoms()>>[a2]
		Set<Molecule> mSet=[m1, m2]
		Calculator<Coord, int[], Cube> coordInCubeCalculator =Mock(Calculator)
		coordInCubeCalculator.calculate(_, cube)>>simpleCoordFactory.create((double[])[-2, -2, -2])
		builder.coordInCubeCalculator=coordInCubeCalculator
		builder.simpleCoordFactory=simpleCoordFactory

		when:"densityTypes is  empty"
		cube.setDensityTypes([])
		cube= builder.build(d, mSet)
		then:"assert the number of xyz steps and origin coordinate"
		assert cube.getNumberOfXYZSteps()==[2, 2, 2]
		assert cube.getGridOrigin().getCoords().internalVector.equals(new Vector3D(-0,-0,-0))
	}
}
