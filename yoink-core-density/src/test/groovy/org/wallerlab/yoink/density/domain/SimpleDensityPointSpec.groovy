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

import org.wallerlab.yoink.api.service.math.Vector
import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.service.math.Matrix
import spock.lang.Ignore
import spock.lang.Shared;
import spock.lang.Specification
import spock.lang.Unroll

class SimpleDensityPointSpec extends Specification{

	@Shared def point
	def gradientVector = Mock(Vector)
	def hessian = Mock(Matrix)

	def setup(){
		def coord=Mock(Coord);
		def density = 0.01
		def gradient = 0.0001
		point =new SimpleDensityPoint( coord, density, gradient, gradientVector, hessian)
	}

	def "test constructor SimpleDensityPoint(Coord currentCoord)"(){

		expect:"assert the type of point.coord"
			assert point.coord          instanceof Coord
			assert point.hessian        instanceof Matrix
			assert point.gradientVector instanceof Vector
	}

	def "test getters and setters"(){

		expect:"assert getters for gradient vector and hessian"
			point.getDensity()         == 0.01
			point.getGradient()        == 0.0001
			point.getHessian()         == hessian
			point.getGradientVector()  == gradientVector
	}
}
