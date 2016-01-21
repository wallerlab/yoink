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

import spock.lang.Specification;

import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.density.domain.SimpleDensityPoint;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.CommonsMatrix;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory
class DensityPropertiesCalculatorSpec extends Specification{

	def "test the return type of calculate(Set<Atom> atoms,Coord currentCoord)"(){

		given:
		def currentCoord=Mock(Coord)
		def a1 = Mock(Atom)
		def a2 = Mock(Atom)
		Set<Atom> atoms=[a1, a2]
		Calculator<DensityPoint, DensityPoint, Atom> atomicDensityPropertiesCalculator=Mock(Calculator)
		def densityPoint=Mock(DensityPoint)
		atomicDensityPropertiesCalculator.calculate(_,_)>>densityPoint
		densityPoint.getDensity()>>(double)1.0
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def myMatrix=new SimpleMatrixFactory()
		myMatrix.matrixType=Matrix.Type.COMMONS
		densityPoint.getGradientVector()>> myVector3D.create(1,1,1)
		def hessian=myMatrix.matrix3x3()
		double[][]	h=[
			[1, 1, 1],
			[1, 1, 1],
			[1, 1, 1
			]
		]
		hessian.array2DRowRealMatrix(h)
		densityPoint.getHessian()>>hessian
		def gvr=myVector3D.create(2,2,2)
		def hr=hessian.scalarMultiply(2.0)
		def simpleDensityPointFactory=Mock( Factory)
		simpleDensityPointFactory.create(_)>>new SimpleDensityPoint()

		when:"set up a new DensityPropertiesCalculator"
		def calculator= new DensityPropertiesCalculator()
		calculator.myVector3D=myVector3D;
		calculator.myMatrix=myMatrix;
		calculator.atomicDensityPropertiesCalculator=atomicDensityPropertiesCalculator
		calculator.simpleDensityPointFactory=simpleDensityPointFactory;

		then:"assert the return type and value"
		def dp=calculator.calculate(atoms,currentCoord)
		dp instanceof DensityPoint
		dp.getDensity()==2.0
		dp.getGradientVector().equals(gvr)
		dp.getHessian().equals(hr)
	}
}

