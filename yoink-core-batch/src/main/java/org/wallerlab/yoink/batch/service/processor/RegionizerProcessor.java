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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.model.region.Region.Name;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.api.service.region.RegionizerMath;

/**
 * This class is to set up and execute adaptive QM/MM partitioning.
 *
 * Spring batch should wrap this class in a processor
 * 
 * @author Min Zheng
 *
 */
@Service
public class RegionizerProcessor implements ItemProcessor<Job<JAXBElement>,Job>{
	
	@Resource
	@Qualifier("preRegionizer")
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> preRegionizer;

	@Resource
	@Qualifier("postRegionizer")
	protected RegionizerMath<Map<Region.Name, Region>, MolecularSystem> postRegionizer;

	//This is our composite
	@Autowired
	protected List<Regionizer<Map<Name, Region>, Map<JobParameter, Object>>> compositeRegionizers;

	/**
	 * read in a list of requests and execute them.
	 *
	 * @param job
	 *            - a job to be processed
	 * @return jobs - a list of YoinkJob
	 *         {@link Job}
	 */
	public Job process(Job<JAXBElement> job) throws Exception {
		Map<Region.Name, Region> regions = job.getRegions();
		preRegionizer.regionize(regions, job.getMolecularSystem());
		for (Regionizer<Map<Region.Name, Region>, Map<JobParameter, Object>> regionizer : compositeRegionizers) {
			regions = (Map<Region.Name, Region>) regionizer.regionize(regions, job.getParameters());
		}
		postRegionizer.regionize(regions, job.getMolecularSystem());
		return job;
	}

}
