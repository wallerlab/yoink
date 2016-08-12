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
package org.wallerlab.yoink.batch.service.processor;

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.service.region.Regionizer;

import javax.xml.bind.JAXBElement;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is to set up and execute adaptive QM/MM partitioning.
 *
 * Spring batch should wrap this class in a processor, move is back to region module
 * 
 * @author Min Zheng
 *
 */
//Deprecate this
@Service
public class RegionizerProcessor implements ItemProcessor<Job<JAXBElement>,Job>{

	@Autowired
	Regionizer regionizer;

	/**
	 * read in a list of requests and execute them.
	 *
	 * @param job
	 *            - a job to be processed
	 * @return jobs - a list of YoinkJob
	 *         {@link Job}
	 */
	public Job process(Job<JAXBElement> job) throws Exception {
		return regionizer.regionize(job);
	}

}
