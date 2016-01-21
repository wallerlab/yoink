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

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import spock.lang.Specification;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType

class SimpleVector3DFactorySpec extends Specification {

	def"test meethod create(double x, double y, double z) "(){
		def factory= new SimpleVector3DFactory();
		factory.myVectorType=Vector3DType.COMMONS;
		when:"make a new vector usint method create(x,y,z)"
		def v=factory.create(1, 2, 3)
		then:"assert the vector from method create(x,y,z)"
		v.getInternalVector().equals(new Vector3D(1,2,3))
	}


	def"test meethod create(double [] d) "(){
		def factory= new SimpleVector3DFactory();
		factory.myVectorType=Vector3DType.COMMONS;
		double[] d=[1, 2, 3]
		when:"make a new vector usint method create(double[] d)"
		def v=factory.create(d)
		then:"assert the vector from method create(double[] d)"
		v.getInternalVector().equals(new Vector3D(1,2,3))
	}
}
