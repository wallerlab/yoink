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

import java.io.StringWriter;
import javax.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

/**
 * this class is to use jaxb to write an object into a string.
 * 
 * @author Min Zheng
 *
 */
@Service
public class JaxbStringWriter extends AbstractJaxbWriter{

	private StringWriter output;
	
	/**
	 * use JAXB writer to write out an instance to string.
	 * 
	 * @param notUsed
	 *            - the name of out put file
	 * @param jaxbObject
	 *            - the instance will be written into a file
	 */
	public void write(String notUsed, Object jaxbObject) {
		this.jaxbObject = jaxbObject;
		this.output = new StringWriter();
		marshall();
	}
	
	protected void marshal() throws JAXBException {
		jaxbMarshaller.marshal(jaxbObject, output);
	}

	public String getOutput() {
		return output.toString();
	}

	public void setOutput(StringWriter output) {
		this.output = output;
	}

	
	
}
