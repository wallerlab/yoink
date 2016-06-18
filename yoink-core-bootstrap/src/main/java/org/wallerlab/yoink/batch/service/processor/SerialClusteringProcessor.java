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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.batch.api.model.bootstrap.Job;
import org.wallerlab.yoink.batch.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.batch.api.model.regionizer.Region;
import org.wallerlab.yoink.batch.api.service.bootstrap.Clustering;
import org.wallerlab.yoink.batch.api.service.bootstrap.JobBuilder;
import org.wallerlab.yoink.batch.api.service.regionizer.RegionizerMath;
import org.wallerlab.yoink.batch.clustering.InteractionSet;


/**
 * This class is to set up and execute clustering.
 * 
 * @author Min Zheng
 *
 */
@Service
public class SerialClusteringProcessor extends  AbstractClusteringProcessor<JAXBElement, Job> {

	@Autowired
	@Qualifier("jobJaxbBuilderImpl")
	private JobBuilder<JAXBElement,JAXBElement> jobJaxbBuilderImpl;
	@Resource
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceStarting;
	
	@Resource
	private InteractionSet interactionSet;
	
	@Resource
	private Clustering doriClustering;
	protected static final Log log = LogFactory.getLog(AbstractAdaptiveQMMMProcessor.class);

	/**
	 * read in a list of requests and execute them.
	 * 
	 * @param input
	 *            - a list of files
	 * @return jobs - a list of YoinkJob
	 *         {@link Job}
	 */
	@Override
	public Job process(JAXBElement input) throws Exception {
		
		return buildAndExecute(input);
	}

	private Job buildAndExecute(JAXBElement input) {
		Job job = jobJaxbBuilderImpl.build(input);
		executeClustering( job) ;
		return job;
	}
	
}
