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
package org.wallerlab.yoink.molecular.domain

import spock.lang.Specification

import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.api.service.math.Vector;
class SimpleCoordFactorySpec extends Specification {

	def "test method create"(){
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		when:"make a SimpleCoordFactory"
		def simpleCoordFactory=new SimpleCoordFactory()
		simpleCoordFactory.myVector3D=myVector3D
		then:"create requird coordinate"
		def coord=simpleCoordFactory.create(1, 1, 3)
		coord.getCoords().equals(myVector3D.create(1, 1, 3))
	}
}
