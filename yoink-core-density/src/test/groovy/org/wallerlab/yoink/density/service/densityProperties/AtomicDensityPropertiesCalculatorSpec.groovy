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
package org.wallerlab.yoink.density.service.densityProperties
import org.wallerlab.yoink.api.model.Atom
import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.DensityPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.density.domain.SimpleDensityPoint
import org.wallerlab.yoink.density.service.densityProperties.AtomicDensityPropertiesCalculator
import org.wallerlab.yoink.math.linear.CommonsVector3D
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecular.domain.SimpleCoord
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory

import spock.lang.Specification;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
class AtomicDensityPropertiesCalculatorSpec extends Specification{



	def"calculate(densityValues,atom), check result values: density,gradientVector,hessian"(){

		given:
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		def coordinate=  simpleCoordFactory.create((double[])[-5.448858, -1.592916 , -2])
		def a = Mock(Atom)
		a.getElementType()>>Element.H
		def aCoord=simpleCoordFactory.create((double[])[-3.448858, 0.407084 , 0])
		a.getCoordinate()>>aCoord
		double[][]	h=[
			[
				3.3309016742003245E-4,
				5.526679305394191E-4,
				5.526679305394191E-4
			],
			[
				5.526679305394191E-4,
				3.3309016742003245E-4,
				5.526679305394191E-4
			],
			[
				5.526679305394191E-4,
				5.526679305394191E-4,
				3.3309016742003245E-4
			]
		]

		when:"make a new AtomicDensityPropertiesCalculator"
		def calculator= new AtomicDensityPropertiesCalculator()
		def dv=new SimpleDensityPoint(coordinate)

		then:"assert gradient vector and hessian matrix"
		calculator.calculate(dv, a)
		Math.abs(dv.getDensity()-0.40223E-03)<=1.0E-5
		for(int i=0;i<3;i++){
			Math.abs(dv.getGradientVector().getEntry(i)+0.0004391555)<=1.0E-5
		}
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				Math.abs(dv.getHessian().data[i][j]-h[i][j])<=1.0E-5
			}
		}
	}
}
