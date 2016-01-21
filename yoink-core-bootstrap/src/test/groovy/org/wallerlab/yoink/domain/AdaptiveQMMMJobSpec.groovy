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
package org.wallerlab.yoink.domain

import org.xml_cml.schema.ObjectFactory

import spock.lang.Specification;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

class AdaptiveQMMMJobSpec extends Specification{

	def "test domain model "(){

		def ms=Mock(MolecularSystem)
		def parameters=Mock(Map)
		def properties=Mock(Map)
		def regions=Mock(Map)
		def job=new AdaptiveQMMMJob()
		def factory=new ObjectFactory()
		def cml=factory.createCml()
		def input=factory.createCml(cml)

		when:  " when used as a javabean"
		job.setInput(input)
		job.setMolecularSystem(ms)
		job.setParameters(parameters)
		job.setProperties(properties)
		job.setRegions(regions)

		then: " check that no nulls are returned"
		job.getInput()==input
		job.getMolecularSystem()==ms
		job.getParameters()==parameters
		job.getProperties()==properties
		job.getRegions()==regions
	}
}
