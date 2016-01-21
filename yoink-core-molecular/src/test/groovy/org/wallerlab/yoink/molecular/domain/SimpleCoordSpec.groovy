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
import org.wallerlab.yoink.api.Vector;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.wallerlab.yoink.api.service.math.Vector;

class SimpleCoordSpec extends Specification{

	def "test constructor   SimpleCoord(Vector coordVector) "(){
		when:"make a new SimpCoord using constructor"
		def coordVector =Mock(Vector)
		def coord= new  SimpleCoord(coordVector)
		then:"assert the return type of getCoords()"
		coord.getCoords() instanceof Vector
	}
}
