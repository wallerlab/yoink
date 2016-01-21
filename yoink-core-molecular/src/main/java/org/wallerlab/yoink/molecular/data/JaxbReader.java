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
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.service.molecular.FilesReader;

/**
 * this class is to use JAXB to read a file.
 * 
 * @author Min Zheng
 *
 */
@Service
public class JaxbReader implements FilesReader<Object, String> {

	/**
	 * jaxb reader to read in an instance from a file.
	 * 
	 * @param nameOfFile
	 *            - the file name
	 * @param jaxbObject
	 *            - the instance will be read from the file
	 * @return jaxbObject - the required instance
	 */
	public Object read(String nameOfFile, Object jaxbObject) {
		File file = new File(nameOfFile);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Class
					.forName(jaxbObject.getClass().getName()));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbObject = jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return jaxbObject;
	}

}
