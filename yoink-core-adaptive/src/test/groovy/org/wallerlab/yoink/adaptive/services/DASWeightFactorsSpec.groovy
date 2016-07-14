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

import org.wallerlab.yoink.api.enums.*

import org.wallerlab.yoink.api.model.region.Region
import org.wallerlab.yoink.api.model.batch.Job
import org.wallerlab.yoink.api.model.molecule.Molecule
import spock.lang.Specification

class DASWeightFactorsSpec extends Specification {

	def "test method execute(YoinkJob<JAXBElement> job)"(){

		given:"a Job"
		def job=Mock(Job)

		def m1=Mock(Molecule)
		def m2=Mock(Molecule)

		m1.getIndex()>>(int)1
		m2.getIndex()>>(int)2

		def region=Mock(Region)
		region.getMolecules()>>[m1, m2]
		def regions=Mock(Map)
		regions.get(Region.Name.BUFFER)>>region
		job.getRegions()>>regions

		def properties= [:]

		def smoothfactors = [0.1d,0.2d]
		properties.put("smoothfactors",smoothfactors)
		job.getProperties()>>properties

		when:"make DAS weights"
		def weightFactors=new DASWeightFactors()

		then:"call method smooth, assert the size of calculated value"
		weightFactors.smooth(job)
		properties.get("weightfactors").size()==3
	}
}
