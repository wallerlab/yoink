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
package org.wallerlab.yoink.molecule.service.translator

import org.wallerlab.yoink.batch.api.service.molecular.Converter
import org.wallerlab.yoink.molecule.data.JaxbFileReader
import org.xml_cml.schema.Cml
import spock.lang.Specification;
import org.wallerlab.yoink.batch.api.model.bootstrap.JobParameter;
class ParameterTranslatorSpec extends Specification {

	def "test method translate(JAXBElement<Cml> cml)"(){
		when:"set up a new ParameterTranslator,read in a given file"
		def parameterTranslator= new ParameterTranslator()
		parameterTranslator.unitConverterType=Converter.UnitConverterType.AngstromToBohr
		def cml=new JaxbFileReader().read("./src/test/resources/aro.xml", new  Cml())
		then:"tranlate all parameters in given file"
		parameterTranslator.translate(cml)
		parameterTranslator.translate(cml).size()==23
		parameterTranslator.translate(cml).get(JobParameter.DGRID)==true
	}
}
