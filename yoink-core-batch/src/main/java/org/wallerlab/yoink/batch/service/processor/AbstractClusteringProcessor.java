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

import org.springframework.batch.item.ItemProcessor;
import org.wallerlab.yoink.batch.api.model.bootstrap.Job;
import org.wallerlab.yoink.batch.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.batch.api.model.regionizer.Region;
import org.wallerlab.yoink.batch.api.service.bootstrap.Clustering;
import org.wallerlab.yoink.batch.api.service.regionizer.RegionizerMath;
import org.wallerlab.yoink.clustering.InteractionSet;

/**
 * This class is to set up and execute clustering based on DORI analysis.
 * 
 * @author Min Zheng
 * @param <I> input
 * @param <O> output
 *
 */
public abstract class AbstractClusteringProcessor<I, O> implements
		ItemProcessor<I, O> {

	@Resource
	private InteractionSet interactionSet;

	@Resource
	private Clustering doriClustering;
	@Resource
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceStarting;

	protected Job executeClustering(Job job) {

		regionizerServiceStarting.regionize(job.getRegions(),
				job.getMolecularSystem());
		interactionSet.getDoriInteractionSet(job);
		doriClustering.cluster(job);

		return job;
	}

}
