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

import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.molecular.domain.SimpleCoord
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory
import org.wallerlab.yoink.api.model.cube.Cube;
import org.wallerlab.yoink.api.service.math.Vector;

import spock.lang.Specification;

class CoordInCubeCalculatorSpec extends Specification{

	def "calculate()"(){

		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		int[] xyzCurrentStep=[1, 1, 1]
		def cube=Mock(Cube)
		cube.getGridOrigin()>>simpleCoordFactory.create()
		double[] d=[0.1, 0.1, 0.1]
		cube.getXyzStepSize()>>d

		when:"set up new CoordInCubeCalculator"
		def calculator= new CoordInCubeCalculator()
		calculator.myVector3D=myVector3D
		calculator.simpleCoordFactory=simpleCoordFactory

		then:"assert the x/y/z coordinate value"
		def c=calculator.calculate(xyzCurrentStep, cube)
		c.getCoords().getX()==0.1
		c.getCoords().getY()==0.1
		c.getCoords().getZ()==0.1
	}
}

