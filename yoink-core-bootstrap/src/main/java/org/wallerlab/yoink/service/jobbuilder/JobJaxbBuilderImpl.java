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

package org.wallerlab.yoink.service.jobbuilder;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.service.molecular.FilesReader;
import org.wallerlab.yoink.domain.AdaptiveQMMMJob;

/**
 * this class is to read in all inputs (like molecular system and parameters)
 * needed for adaptive QM/MM partitioning.
 * 
 *
 */
@Service
public class JobJaxbBuilderImpl extends AbstractJobBuilder<JAXBElement,JAXBElement>{

	@Resource
	private FilesReader<Object, String> jaxbFileReader;
	
	/**
	 * read in cml file, and convert it to molecular system and parameters for
	 * building a new adaptive qmmm job.
	 * 
	 * @param input
	 *            , the file name to be read
	 * @return job, the new built YoinkJob
	 *         {@link org.wallerlab.yoink.api.model.bootstrap.Job }
	 */
	@Override
	public Job<JAXBElement> build(JAXBElement input) {
		//wrap it back up - crazy ;).
		Job<JAXBElement> job= new AdaptiveQMMMJob();
		job.setInput(input);
		process(job);
		return job;
	}

}
