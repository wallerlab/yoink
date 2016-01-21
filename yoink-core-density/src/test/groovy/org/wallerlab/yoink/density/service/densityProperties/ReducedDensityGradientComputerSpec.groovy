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
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.math.constants.Constants;

import spock.lang.Specification;

class ReducedDensityGradientComputerSpec  extends Specification {
	def "test method calculate(DensityPoint densityPoint) "(){

		def calculator= new ReducedDensityGradientComputer()

		when:"make a  DensityPoint"
		def dv=Mock(DensityPoint)
		dv.getDensity()>>4.022262648305632E-4
		dv.getGradient()>>5.78572728678162E-7
		then:"assert the rdg value"
		Math.abs(calculator.calculate(dv)-0.414054E+01)<=1.0E-4

		when:"make another  DensityPoint"
		def dv2=Mock(DensityPoint)
		dv2.getDensity()>>0.21631E-5
		dv2.getGradient()>>1.5014110903446034E-11
		then:"assert the rdg value"
		Math.abs(calculator.calculate(dv2)-0.22386E+02)<=1.0E-4
	}
}