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
package org.wallerlab.yoink.density.domain

import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;

import spock.lang.Specification

class SimpleDensityPointSpec extends Specification{

	def "test constructor SimpleDensityPoint(Coord currentCoord)"(){
		def coord=Mock(Coord);
		when:"make a new SimpleDensityPoint with mocked coord"
		def point=new SimpleDensityPoint(coord)
		then:"assert the type of point.currentCoord"
		assert	point.currentCoord instanceof Coord
	}


	def "test getters and setters"(){
		def hessian=Mock(Matrix);
		def gradientVector=Mock(Vector);
		def point=new SimpleDensityPoint()
		when:"set gradient vector and hessian"
		point.setGradientVector(gradientVector)
		point.setHessian(hessian)
		then:"assert getters for gradient vector and hessian"
		point.getHessian()==hessian
		point.getGradientVector()==gradientVector
	}
}
