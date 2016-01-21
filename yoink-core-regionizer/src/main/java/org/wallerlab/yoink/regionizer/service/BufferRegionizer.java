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
package org.wallerlab.yoink.regionizer.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.api.service.regionizer.RegionizerComponent;

/**
 * this class is to locate buffer region based on the densiyt of qm core
 * 
 * @author Min Zheng
 *
 */
@Service
public class BufferRegionizer implements
		RegionizerComponent<Map<Region.Name, Region>, Map<JobParameter, Object>> {

	@Resource
	private Partitioner<Map<Region.Name, Region>, DensityType> densityPartitioner;

	/**
	 * set up buffer region partitioning
	 */
	@Override
	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType==Partitioner.Type.DORI){
		 densityPartitioner.partition(regions, parameters,
				DensityType.ELECTRONIC);
	}
		return regions;
	}
	
}
