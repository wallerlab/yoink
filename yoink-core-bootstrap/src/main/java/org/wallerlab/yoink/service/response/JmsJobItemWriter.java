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
package org.wallerlab.yoink.service.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.molecular.data.JaxbStringWriter;
import org.xml_cml.schema.Cml;

/**
 * This class converts a job to a string using JAXB, and then 
 * delegates to a standard JmsItemWriter from Spring batch.
 *
 *
 */
@Service
public class JmsJobItemWriter implements ItemWriter<Job> {

	@Autowired
	@Qualifier("jaxbStringWriter")
	private JaxbStringWriter  jaxbStringWriter;
	
	@Autowired
	ItemWriter<String> jmsItemWriter;

	protected static final Log log = LogFactory.getLog(CmlFilesResponse.class);

	/**
	 * write adaptive QM/MM result into a String, and then send to a JMS queue.
	 * 
	 * @param jobs
	 *            - a List of Job
	 *            {@link org.wallerlab.yoink.api.model.bootstrap.Job } List
	 */
	@Override
	public void write(List<? extends Job> jobs) throws Exception {
		List<String> items = new ArrayList<>();
		for (Job job : jobs) {
			JAXBElement<Cml> input = (JAXBElement<Cml>) job.getInput();
			jaxbStringWriter.write("notused",input.getValue());
			items.add(jaxbStringWriter.getOutput());
		}
		jmsItemWriter.write(items);
	}

	public ItemWriter<String> getJmsItemWriter() {
		return jmsItemWriter;
	}

	public void setJmsItemWriter(ItemWriter<String> jmsItemWriter) {
		this.jmsItemWriter = jmsItemWriter;
	}

}
