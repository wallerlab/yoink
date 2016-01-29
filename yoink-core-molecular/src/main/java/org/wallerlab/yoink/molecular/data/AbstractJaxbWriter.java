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


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.service.molecular.FilesWriter;

/**
 * this class is to use jaxb to write an object out.
 * 
 * @author Min Zheng
 *
 */
@Service
public abstract class AbstractJaxbWriter implements FilesWriter<Object> {

	protected JAXBContext jaxbContext;
	protected Marshaller jaxbMarshaller;
	protected Object jaxbObject;

	/**
	 * use JAXB writer to write out an instance to a file.
	 * 
	 * @param nameOfFile
	 *            - the name of out put file
	 * @param jaxbObject
	 *            - the instance will be written into a file
	 */
	protected void marshall() {
		try {
			jaxbContext = JAXBContext.newInstance(Class
					.forName(jaxbObject.getClass().getName()));
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void marshal() throws JAXBException;

}
