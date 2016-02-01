
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
package org.wallerlab.yoink.service.jobbuilder

import org.xml_cml.schema.ObjectFactory

import spock.lang.Specification

import javax.xml.bind.JAXBElement;

import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.service.molecular.FilesReader;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.molecular.Translator
import org.wallerlab.yoink.service.jobbuilder.JobFileBuilderImpl;
import org.wallerlab.yoink.api.model.bootstrap.Job

class JobJaxbBuilderImplSpec extends Specification {

	def "test method read(String inputfile,YoinkJob<JAXBElement> job)"(){
		def jaxbReader=Mock(FilesReader)
		def  molecularSystemTranslator=Mock(Translator)
		def  parameterTranslator=Mock(Translator)
		def factory=new ObjectFactory()
		def cml=factory.createCml()	
		def jaxB = factory.createCml(cml)
		molecularSystemTranslator.translate(_)>>Mock(MolecularSystem)
		parameterTranslator.translate(_)>>Mock(Map)
		

		when:"set up a new JobBuilder"
		def builder=new JobJaxbBuilderImpl()
		builder.jaxbFileReader=jaxbReader
		builder.molecularSystemTranslator=molecularSystemTranslator
		builder.parameterTranslator=parameterTranslator
		
		then:"check the return type"
		builder.build(jaxB) instanceof Job
	}
}
