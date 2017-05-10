
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

import spock.lang.Specification;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.service.molecule.FilesReader;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.service.molecule.Translator
import org.wallerlab.yoink.service.jobbuilder.JobFileBuilderImpl;
import org.wallerlab.yoink.api.model.bootstrap.Job

class JobStringBuilderImplSpec extends Specification {

	def "test method read(String inputfile,YoinkJob<JAXBElement> job)"(){
		def jaxbReader=Mock(FilesReader)
		def  molecularSystemTranslator=Mock(Translator)
		def  parameterTranslator=Mock(Translator)
		def factory=new ObjectFactory()
		def gridReader=Mock(FilesReader)
		def cml=factory.createCml()
		def input=factory.createCml(cml)
		jaxbReader.read(_,_)>>input
		molecularSystemTranslator.translate(_)>>Mock(MolecularSystem)
		def parameter=[:]
		
		parameterTranslator.translate(_)>>parameter

		when:"set up a new JobBuilder"
		def builder=new JobFileBuilderImpl()
		builder.jaxbFileReader=jaxbReader
		builder.molecularSystemTranslator=molecularSystemTranslator
		builder.parameterTranslator=parameterTranslator
		def inputfile="./src/test/resources/aro.xml"
		String fileContents = new File(inputfile).text	
		
		then:"check the return type"
		builder.build(fileContents) instanceof Job
	}
}
