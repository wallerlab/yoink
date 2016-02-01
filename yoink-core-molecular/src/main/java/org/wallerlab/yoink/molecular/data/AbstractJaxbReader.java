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
package org.wallerlab.yoink.molecular.data;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.service.molecular.FilesReader;
import org.xml_cml.schema.Cml;

/**
 * this class is to use JAXB to read input.
 * 
 * @author Min Zheng
 *
 */
@Service
public abstract class AbstractJaxbReader implements FilesReader<Object, String> {

	protected JAXBContext jaxbContext;
	protected Unmarshaller jaxbUnmarshaller;	
	
	public abstract JAXBElement<Cml> read(String input, Object jaxbObject);
	
	protected JAXBElement<Cml> init(Object jaxbObject) {
		JAXBElement<Cml> marshalled =  null;
		try {
			jaxbContext = JAXBContext.newInstance(Class.forName(jaxbObject.getClass().getName()));
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			marshalled = unmarshal(jaxbObject);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return marshalled;
	}
	
	protected abstract JAXBElement<Cml> unmarshal(Object jaxbObject) throws JAXBException;

}
