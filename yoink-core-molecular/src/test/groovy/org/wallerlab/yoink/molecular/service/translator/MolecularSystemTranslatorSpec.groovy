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
package org.wallerlab.yoink.molecular.service.translator

import javax.xml.bind.JAXBElement;

import org.wallerlab.yoink.api.service.molecular.Converter.UnitConverterType;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecular.data.JaxbFileReader
import org.wallerlab.yoink.molecular.domain.SimpleCoordFactory
import org.xml_cml.schema.Cml;
import org.xml_cml.schema.ObjectFactory
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.math.Vector;

import spock.lang.Specification

class MolecularSystemTranslatorSpec extends Specification {

	def "test method translate(JAXBElement<Cml> cml)"(){
		when:"make a new  MolecularSystemTranslator for a given file"
		def cml=(JAXBElement<Cml>)new JaxbFileReader().read("./src/test/resources/aro.xml", new  Cml())
		def molecularSystemTranslator= new MolecularSystemTranslator()
		molecularSystemTranslator.unitConverterType=UnitConverterType.AngstromToBohr
		def myVector3D=new SimpleVector3DFactory()
		myVector3D.myVectorType=Vector.Vector3DType.COMMONS
		def simpleCoordFactory=new  SimpleCoordFactory()
		simpleCoordFactory.myVector3D= myVector3D
		molecularSystemTranslator.simpleCoordFactory=simpleCoordFactory
		def centerOfMassComputer=Mock(Computer)
		centerOfMassComputer.calculate(_)>>Mock(Coord)
		molecularSystemTranslator.centerOfMassComputer=centerOfMassComputer
		molecularSystemTranslator.myVector3D=myVector3D
		
		then:"translate molecular information in the given file"
		def ms =molecularSystemTranslator.translate(cml)
		ms.getMolecules().size()==476
		ms.getAtoms().size()==1435
	}
}
