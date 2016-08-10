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
 *//*

package org.wallerlab.yoink.batch.service.processor;

import org.springframework.batch.item.ItemProcessor;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.service.cluster.Clusterer;
import org.wallerlab.yoink.cluster.service.InteractionService;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;

import static org.wallerlab.yoink.api.model.Job.JobParameter.PARTITIONER;
import static org.wallerlab.yoink.api.service.region.Regionizer.Type.CLUSTER;

*/
/**
 * This class is to set up and execute region based on DORI analysis.
 * 
 * @author Min Zheng
 *
 *//*

@Service
public class ClusteringProcessor implements ItemProcessor<Job<JAXBElement>, Job> {

	@Resource
	private InteractionService interactionSet;

	@Resource
	private Clusterer doriClustering;

	*/
/**
	 * read in a list of requests and execute them.
	 *
	 * @param job
	 *            - a molecular system and properties to perform clustering on.
	 * @return jobs - a list of YoinkJob
	 *         {@link Job}
	 *//*

	public Job process(Job<JAXBElement> job) throws Exception {
		if (job.getParameter(PARTITIONER) == CLUSTER)
			doriClustering.cluster(job, interactionSet.getDoriInteractionSet(job));
		return job;
	}

}
*/
