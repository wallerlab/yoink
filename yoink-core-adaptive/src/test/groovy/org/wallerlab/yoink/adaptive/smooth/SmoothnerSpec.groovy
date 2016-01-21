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
package org.wallerlab.yoink.adaptive.smooth

import spock.lang.Specification;

import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;
import org.wallerlab.yoink.api.model.bootstrap.Job

class SmoothnerSpec extends Specification{
	def "test parent class Smoothner"(){

		when:"mock Smoothner"
		def smoothner=Mock(Smoothner)
		def smoothFunction=Mock(SmoothFunction)
		def job=Mock(Job)
		then:"method smooth is executable, no error thrown"
		smoothner.smooth(job)
	}
}
