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
package org.wallerlab.yoink.service.response

import org.xml_cml.schema.ObjectFactory

import spock.lang.Specification;

import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.springframework.batch.item.ItemWriter;
import org.wallerlab.yoink.api.model.bootstrap.Job
import org.wallerlab.yoink.api.service.molecular.FilesWriter
import org.wallerlab.yoink.molecular.data.JaxbStringWriter

class JmsJobItemWriterSpec extends Specification{

	def "test method write(List<? extends YoinkJob<JAXBElement>> jobs)"(){
		def job=Mock(Job)
		def jobList=[job, job]
		def parameters=Mock(Map)
		parameters.get(JobParameter.JOB_NAME)>>"test"
		parameters.get(JobParameter.OUTPUT_FOLDER)>>"./src/test/resources"
		job.getParameters()>>parameters
		def factory=new ObjectFactory()
		def cml=factory.createCml()
		def input=factory.createCml(cml)
		job.getInput()>>input
		def jaxbWriter= new JaxbStringWriter()

		def jmsItemWriter = Mock(ItemWriter)
		
		when:"mock response"
		def response=Mock(ItemWriter)
		then:"call method, no error thrown"
		response.write(jobList)

		when:"make a new CmlFilesResponse"
		def response2 = new JmsJobItemWriter()
		response2.jaxbStringWriter= jaxbWriter
		response2.jmsItemWriter = jmsItemWriter
		then:"call method, no error thrown"
		response2.write(jobList)
	}
}
