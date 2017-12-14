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

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.service.molecule.FilesWriter;

/**
 * This class is for job response, to write adaptive qmmm result into a cml
 * file.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class CmlFilesResponse implements ItemWriter<List<Job<JAXBElement>>> {

	@Autowired
	@Qualifier("jaxbFileWriter")
	private FilesWriter<Object> jaxbWriter;

	protected static final Log log = LogFactory.getLog(CmlFilesResponse.class);

	/**
	 * write adaptive qmmm result into a cml file.
	 * 
	 * @param jobs
	 *            - a List of Job
	 *            {@link org.wallerlab.yoink.api.model.bootstrap.Job } List
	 */
	@Override
	public void write(List<? extends List<Job<JAXBElement>>> jobs)
			throws Exception {
		for (List<Job<JAXBElement>> jobList : jobs) {
			for (Job<JAXBElement> job : jobList) {
				String name = (String) job.getParameters().get(
						JobParameter.JOB_NAME);
				String parentDirName = (String) job.getParameters().get(
						JobParameter.OUTPUT_FOLDER);
				if (parentDirName == null) {
					name = ".";
				}
				String outputFileName = parentDirName + "/" + name + "-out.xml";
				jaxbWriter.write(outputFileName, job.getInput().getValue());
			}
		}
		log.info("finish writing all output files");
	}

}
