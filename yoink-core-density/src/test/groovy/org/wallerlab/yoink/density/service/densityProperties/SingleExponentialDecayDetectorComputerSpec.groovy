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

import org.wallerlab.yoink.math.linear.SimpleMatrixFactory
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;
import org.wallerlab.yoink.api.service.math.Matrix.Type;
class SingleExponentialDecayDetectorComputerSpec extends Specification{

	def "test method calculate(DensityPoint densityPoint), calculate sedd value"(){
		when:"prepare a DensityPoint for new SingleExponentialDecayDetectorComputer"
		def dv=Mock(DensityPoint)
		dv.getDensity()>>4.022262648305632E-4
		dv.getGradient()>>5.78572728678162E-7
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def myMatrix=new SimpleMatrixFactory()
		myMatrix.matrixType=Matrix.Type.COMMONS
		dv.getGradientVector()>>myVector3D.create(-4.3916E-4,-4.3916E-4,-4.3916E-4)
		def matrix2= myMatrix.matrix3x3()
		matrix2.array2DRowRealMatrix((double[][])
				[
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
					]]
				)
		dv.getHessian()>>matrix2

		then:"assert the calculated sedd value"
		def calculator= new SingleExponentialDecayDetectorComputer()
		(calculator.calculate(dv)-0)<=1.0E-5
	}


	def " test method calculate(DensityPoint densityPoint), calculate sedd value with another set of inputs"(){
		when:"prepare a DensityPoint for new SingleExponentialDecayDetectorComputer"
		def dv=Mock(DensityPoint)
		dv.getDensity()>>0.21631E-5
		dv.getGradient()>>1.6728625438655234E-11
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector3DType.COMMONS
		dv.getGradientVector()>>myVector3D.create(-3.89586557153796E-6,-2.6225334050902747E-7,-1.217407110440996E-6)
		def matrix2= new SimpleMatrixFactory().matrix3x3()
		matrix2.array2DRowRealMatrix((double[][])
				[
					[
						6.956852534963774E-6,
						5.116535407588096E-7,
						2.3751486154306788E-6
					],
					[
						5.116535407588096E-7,
						-6.096948349452995E-7,
						1.602886106532189E-7
					],
					[
						2.3751486154306788E-6,
						1.602886106532189E-7,
						9.985215470575422E-8
					]]
				)
		dv.getHessian()>>matrix2

		then:"assert the calculated sedd value"
		def calculator= new SingleExponentialDecayDetectorComputer()
		(calculator.calculate(dv)-11.949362462482528)<=1.0E-5
	}
}
