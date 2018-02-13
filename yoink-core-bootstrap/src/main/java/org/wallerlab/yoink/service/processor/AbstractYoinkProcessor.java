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
package org.wallerlab.yoink.service.processor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.model.region.Region.Name;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.bootstrap.Wrapper;
import org.wallerlab.yoink.api.service.graph.Clusterer;
import org.wallerlab.yoink.api.service.graph.Grapher;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.api.service.region.RegionizerMath;

/**
 * This class is to set up and execute adaptive QM/MM partitioning.
 * 
 * @author Min Zheng
 * @param <I>
 *            input
 * @param <O>
 *            output
 *
 */
public abstract class AbstractYoinkProcessor<I, O> implements
		ItemProcessor<I, O> {

	@Resource
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceStarting;

	@Resource
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceEnding;

	@Resource
	protected Smoothner adaptiveQMMMSmoothnerRouter;

	@Resource
	protected Wrapper propertyWrapper;

	@Resource
	protected Grapher dORIGrapher;

	@Resource
	protected Clusterer clusterer;

	@Autowired
	protected List<Regionizer<Map<Name, Region>, Map<JobParameter, Object>>> adaptiveQMMMRegionizers;
	
	protected static final Log log = LogFactory.getLog(AbstractYoinkProcessor.class);

	protected Job executeQMMMPartitioning(Job job) {
		log.debug("regionzie:...");
		regionize(job);
		log.debug("smooth:...");
		smooth(job);
		log.debug("graph:...");
		graphing(job);
		log.debug("cluster:...");
		clustering(job);
		log.debug("result:...");
		wrapResult(job);
		return job;
	}

	protected void regionize(Job job) {
		Map<Region.Name, Region> regions = job.getRegions();
		log.debug("regionizerServiceStarting");
		regionizerServiceStarting.regionize(regions, job.getMolecularSystem());
		log.debug("adaptiveQMMMRegionizers: "+ adaptiveQMMMRegionizers.size());
		regions = adaptiveQMMMRegionizers(job, regions);
		log.debug("regionizerServiceEnding");
		regionizerServiceEnding.regionize(regions, job.getMolecularSystem());
	}

	private Map<Region.Name, Region> adaptiveQMMMRegionizers(Job job,
			Map<Region.Name, Region> regions) {

		for (Regionizer<Map<Region.Name, Region>, Map<JobParameter, Object>> regionizer : adaptiveQMMMRegionizers) {

			regions = (Map<Region.Name, Region>) regionizer.regionize(regions,
					job.getParameters());
		}
		return regions;
	}

	private void smooth(Job job) {
		adaptiveQMMMSmoothnerRouter.smooth(job);
	}

	private void clustering(Job job) {
		clusterer.clustering(job);
	}

	private void graphing(Job job) {
		dORIGrapher.graphing(job);
	}

	private void wrapResult(Job job) {
		propertyWrapper.wrap(job);
	}

}
