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

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.batch.Clustering;
import org.wallerlab.yoink.api.service.region.RegionizerMath;
import org.wallerlab.yoink.cluster.service.interaction.InteractionSet;

/**
 * This class is to set up and execute region based on DORI analysis.
 * 
 * @author Min Zheng
 *
 */
@Service
public class ClusteringProcessor implements ItemProcessor<Job<JAXBElement>, Job> {

	@Resource
	private InteractionSet interactionSet;

	@Resource
	private Clustering doriClustering;

	@Resource
	@Qualifier("preRegionizer")
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> preRegionizer;

	/**
	 * read in a list of requests and execute them.
	 *
	 * @param job
	 *            - a moelcular system and properties to perform clustering on.
	 * @return jobs - a list of YoinkJob
	 *         {@link Job}
	 */
	@Override
	public Job process(Job<JAXBElement> job) throws Exception {
		preRegionizer.regionize(job.getRegions(), job.getMolecularSystem());
		interactionSet.getDoriInteractionSet(job);
		doriClustering.cluster(job);
		return job;
	}

}
