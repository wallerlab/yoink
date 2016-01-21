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
package org.wallerlab.yoink.math.linear

import org.wallerlab.yoink.api.service.math.Matrix;

import spock.lang.Specification;

class SimpleMatrixFactorySpec extends Specification {
	def "test methods: matrix(), matrix3x3"(){
		def myMatrix= new SimpleMatrixFactory()
		myMatrix.matrixType= Matrix.Type.COMMONS;

		when:"call method matrix()"
		def m=myMatrix.matrix();
		then:"check the return type"
		m instanceof Matrix
		m instanceof CommonsMatrix

		when:"call method matrix3x3()"
		m=myMatrix.matrix3x3()
		double[][]	d=[
			[0, 0, 0],
			[0, 0, 0],
			[0, 0, 0
			]
		]
		then:"check the return value"
		m.getData()==d

		when:"call method vector3D()"
		m=myMatrix.vector3D()
		d=[[0, 0, 0]]
		then:"chekc the return value"
		m.getData()==d
	}
}
