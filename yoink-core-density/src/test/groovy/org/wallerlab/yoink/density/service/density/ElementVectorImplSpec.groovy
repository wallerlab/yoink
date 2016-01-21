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
package org.wallerlab.yoink.density.service.density

import spock.lang.Specification
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;

class ElementVectorImplSpec extends Specification {


	def "test it give back right parameters"(){

		when:"make a new ElementVector"
		def elementVector=new ElementVectorImpl()
		elementVector.elementType=Element.H
		def myVector3D=new SimpleVector3DFactory(Vector3DType.COMMONS);
		elementVector.myVector3D=myVector3D;

		then:"assert the value of element vector"
		elementVector.inverseZVector().equals(myVector3D.create(Math.pow(0.5288, -1),1.0/1.0,1.0/1.0))
		elementVector.cVector().equals(myVector3D.create(0.2815,0.0,0.0))
	}
}
