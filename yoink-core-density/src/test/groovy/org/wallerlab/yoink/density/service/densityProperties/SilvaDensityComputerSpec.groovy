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

import org.wallerlab.yoink.api.model.density.DensityPoint
import org.wallerlab.yoink.api.service.math.Matrix
import org.wallerlab.yoink.api.service.math.Vector
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory

import spock.lang.Specification
class SilvaDensityComputerSpec extends Specification{

	def "test method calculate(DensityPoint densityPoint) "(){
		when:"make a DensityPoint for SilvaDensityComputer"
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def myMatrix=new SimpleMatrixFactory()
		myMatrix.matrixType=Matrix.Type.COMMONS
		def densityPoint=Mock(DensityPoint)
		densityPoint.getDensity()>>(double)1.0
		densityPoint.getHessian()>>myMatrix.matrix3x3()
		densityPoint.getGradientVector()>>myVector3D.create(1,1,1)
		densityPoint.getGradient()>>(double)1.0

		then:"call calculate method in SilvaDensityComputer,no error thrown "
		def SilvaDensityComputer=new SilvaDensityComputer()
		SilvaDensityComputer.calculate(densityPoint)
	}
}
