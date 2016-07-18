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

import org.wallerlab.yoink.Application
import spock.lang.Specification

class DistanceRegionizer extends Specification{

	def "test parent class Smoothner"(){

		when:
		println "hello"

		def file = "distance_das.xml"
		def qmMolecules = [1, 18, 33, 45, 120, 129, 207, 234, 337, 341, 369, 373, 375, 381, 455, 470, 474]
		def size = 17

		def file2 = "distance_abrupt.xml"
		def qmMolecules2 = [ 18, 24, 29, 33, 45, 104, 120, 129, 150, 207, 234, 337, 369, 373, 375, 381, 420, 455, 470, 474]
		def size2 =21

		then:"run yoink"
		//Application.main(file)

		def result = new XmlParser().parse("./outputs/distance_abrupt-out.xml")
		def resultList =  Eval.me(result.propertyList[0].property[0].scalar[0].text())
		qmMolecules == resultList.sort()

		def result2 = new XmlParser().parse("./outputs/distance_abrupt-out.xml")
		def resultList2 =  Eval.me(result2.propertyList[0].property[0].scalar[0].text())
		qmMolecules2 == resultList2.sort()
	}
}
