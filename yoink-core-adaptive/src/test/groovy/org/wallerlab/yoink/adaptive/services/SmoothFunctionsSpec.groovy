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
package org.wallerlab.yoink.adaptive.services

import org.wallerlab.yoink.adaptive.services.SmoothFactors
import org.wallerlab.yoink.adaptive.services.SmoothFunctions
import spock.lang.Specification;

class SmoothFunctionsSpec extends Specification{

	def min=2.0d
	def max=3.0d
	double currentValue;
	def sf= new SmoothFunctions()

	def"test brooks smooth(double currentValue, double min, double max)"(){
		when:"current value is larger than max"
		currentValue=3.1
		then:"result value is 0.0"
		sf.brooks.evaluate(currentValue, min, max)==0.0

		when:"current value is smaller than min"
		currentValue=1.9
		then:"result value is 1.0"
		sf.brooks.evaluate(currentValue, min, max)==1.0

		when:"current value is between min and max"
		currentValue=2.5
		then:"assert reult value"
		Math.abs(sf.brooks.evaluate(currentValue, min, max)-0.57475)<=1.0E-5
	}


	def"test bulo smooth(double currentValue, double min, double max)"(){
		when:"current value is larger than max"
		currentValue=3.1
		then:"result value is 1.0"
		sf.bulo.evaluate(currentValue, min, max)==1.0

		when:"current value is smaller than min"
		currentValue=1.9
		then:"result value is 0.0"
		sf.bulo.evaluate(currentValue, min, max)==0.0

		when:"current value is between min and max"
		currentValue=2.5
		then:"assert reult value"
		Math.abs(sf.bulo.evaluate(currentValue, min, max)-0.5)<=1.0E-5
	}


	def "test morokuma smooth(double currentValue, double min, double max)"(){
		when:"current value is max"
		currentValue=3.1
		then:"result value is 1.0"
		sf.morokuma.evaluate(currentValue, min, max)==1.0

		when:"current value is  min"
		currentValue=1.9
		then:"result value is 0.0"
		sf.morokuma.evaluate(currentValue, min, max)==0.0

		when:"current value is between min and max"
		currentValue=2.5
		then:"assert reult value"
		Math.abs(sf.morokuma.evaluate(currentValue, min, max)-0.05792)<=1.0E-5
	}


	def "test permuted smooth(double currentValue, double min, double max)"(){

		when:"current value is max"
		currentValue=3.2
		then:"result value is 0"
		sf.permuted.evaluate(currentValue, min, max)==0

		when:"current value is  min"
		currentValue=1.8
		then:"result value is 1.0"
		sf.permuted.evaluate(currentValue, min, max)==1.0

		when:"current value is between min and max"
		currentValue=2.84
		then:"assert reult value"
		Math.abs(sf.permuted.evaluate(currentValue, min, max)-0.94208)<=1.0E-5
	}

	def"test scmp smooth(double currentValue, double min, double max)"(){

		when:"current value is larger than max"
		currentValue=3.1
		then:"result value is 1.0"
		sf.scmp.evaluate(currentValue, min, max)==0.0

		when:"current value is smaller than min"
		currentValue=1.9
		then:"result value is 0.0"
		sf.scmp.evaluate(currentValue, min, max)==1.0

		when:"current value is between min and max"
		currentValue=2.5
		then:"assert reult value"
		Math.abs(sf.scmp.evaluate(currentValue, min, max)-0.5)<=1.0E-5
	}
}
