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
import org.springframework.batch.item.ItemProcessor;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.molecule.Translator;
import org.wallerlab.yoink.batch.domain.AdaptiveQMMMJob;
import org.cml_v3.generated.Cml;

import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * this class is to read in all inputs (like molecule system and parameters)
 * needed for adaptive QM/MM partitioning.
 * 
 * @author Min Zheng
 *
 */
@Service
public class BuilderProcessor implements ItemProcessor<JAXBElement, Job<JAXBElement>> {

	@Resource
	@Qualifier("molecularSystemTranslator")
	protected Translator<MolecularSystem, JAXBElement<Cml>> molecularSystemTranslator;

	@Resource
	protected Translator<Map<Job.JobParameter, Object>, JAXBElement<Cml>> parameterTranslator;

	/**
	 * read in cml file, and convert it to molecule system and parameters for
	 * building a new adaptive qmmm job.
	 *
	 * @param input
	 *            , the file name to be read
	 * @return job, the new built YoinkJob
	 *         {@link Job }
	 */
	@Override
	public Job<JAXBElement> process(JAXBElement input) {
		return new AdaptiveQMMMJob(input,
								   molecularSystemTranslator.translate(input),
								   parameterTranslator.translate(input));
	}

}
