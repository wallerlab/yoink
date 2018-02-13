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
package org.wallerlab.yoink.region.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.model.region.Region.Name;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.api.service.region.RegionizerComponent;

/**
 * this class is to find adaptive QM core region, adaptive QM region and buffer
 * region using density based adaptive qm/mm partitioning.
 * 
 * @author Min Zheng
 *
 */
@Service
public class DensityBasedRegionizer implements
		Regionizer<Map<Region.Name, Region>, Map<JobParameter, Object>> {

	@Resource
	private RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>> adaptiveQMCoreRegionizer;

	@Resource
	private RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>> adaptiveQMRegionizer;

	@Resource
	private RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>> bufferRegionizer;

	/**
	 * execute density based adaptive regionizer by looping over all regionizer
	 * components in it
	 * 
	 * @return regions - a list of region
	 *         {@link org.wallerlab.yoink.api.model.region.Region}
	 */
	@Override
	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType == Partitioner.Type.DORI) {
		
			for (RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>> regionizerComponent : getRegionizerComponents()) {
				regionizerComponent.regionize(regions, parameters);
			}
		}
		return regions;
	}

	/*
	 * get all regionizer components(adaptive qm core, adaptive qm and buffer
	 * regionizers) for density based adaptive QM/MM partitioning.
	 */
	private List<RegionizerComponent<Map<Name, Region>, Map<JobParameter, Object>>> getRegionizerComponents() {
		List<RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>>> partitionRegionizers = new ArrayList<RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>>>();
		partitionRegionizers.add(adaptiveQMCoreRegionizer);
		partitionRegionizers.add(adaptiveQMRegionizer);
		partitionRegionizers.add(bufferRegionizer);
		return partitionRegionizers;
	}
}
