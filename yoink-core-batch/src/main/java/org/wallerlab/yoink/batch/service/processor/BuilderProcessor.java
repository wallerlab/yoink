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

import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.batch.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.batch.api.model.bootstrap.Job;
import org.wallerlab.yoink.batch.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.batch.api.service.bootstrap.JobBuilder;
import org.wallerlab.yoink.batch.api.service.molecular.Translator;
import org.wallerlab.yoink.batch.domain.AdaptiveQMMMJob;
import org.xml_cml.schema.Cml;

/**
 * this class is to read in all inputs (like molecular system and parameters)
 * needed for adaptive QM/MM partitioning.
 * 
 * @author Min Zheng
 *
 */
@Service
public class BuilderProcessor implements JobBuilder<JAXBElement, JAXBElement> {

	@Resource
	protected Translator<MolecularSystem, JAXBElement<Cml>> molecularSystemTranslator;

	@Resource
	protected Translator<Map<JobParameter, Object>, JAXBElement<Cml>> parameterTranslator;

	/**
	 * read in cml file, and convert it to molecular system and parameters for
	 * building a new adaptive qmmm job.
	 *
	 * @param input
	 *            , the file name to be read
	 * @return job, the new built YoinkJob
	 *         {@link Job }
	 */
	@Override
	public Job<JAXBElement> build(JAXBElement input) {
		Job<JAXBElement> job= (Job<JAXBElement>) new AdaptiveQMMMJob();
		job.setInput(input);
		translate(job);
		return job;
	}

	protected void translate(Job<JAXBElement> job) {
		translateMolecularSystem(job);
		translateParameters(job);
	}

	protected void translateMolecularSystem(Job<JAXBElement> job) {
		job.setMolecularSystem(molecularSystemTranslator.translate(job.getInput()));
	}

	protected void translateParameters(Job<JAXBElement> job) {
		job.setParameters(parameterTranslator.translate(job.getInput()));
	}


}
