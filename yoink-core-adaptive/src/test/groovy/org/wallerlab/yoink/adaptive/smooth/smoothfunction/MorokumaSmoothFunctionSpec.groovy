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
package org.wallerlab.yoink.adaptive.smooth.smoothfunction

import spock.lang.Specification;

class MorokumaSmoothFunctionSpec extends Specification{

	def"test method smooth(double currentValue, double min, double max)"(){
		def  min=(double)3.8
		def max=(double)4.0
		double currentValue;
		def sf= new MorokumaSmoothFunction()

		when:"current value is max"
		currentValue=4.2
		then:"result value is 1.0"
		sf.evaluate(currentValue, min, max)==1.0

		when:"current value is  min"
		currentValue=3.2
		then:"result value is 0.0"
		sf.evaluate(currentValue, min, max)==0.0

		when:"current value is between min and max"
		currentValue=3.84
		then:"assert reult value"
		Math.abs(sf.evaluate(currentValue, min, max)-0.05792)<=1.0E-5
	}
}

