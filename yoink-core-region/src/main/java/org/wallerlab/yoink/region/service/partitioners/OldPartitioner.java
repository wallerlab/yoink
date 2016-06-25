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
package org.wallerlab.yoink.region.service.partitioners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.api.service.region.Regionizer;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.wallerlab.yoink.api.model.density.DensityPoint.DensityType.*;

/**
 * this class is to find:
 * - Adaptive QM core region
 * - Adaptive QM region
 * - Buffer
 * region by using density based adaptive qm/mm partitioning.
 * 
 * @author Min Zheng
 *
 */
//MAYBE we need two variants: one for sedd on for dori?
@Service
public class OldPartitioner implements Regionizer<Map<Region.Name, Region>, Map<JobParameter, Object>> {

	/**
	 * this class is to locate buffer region based on the density of qm core
	 *
	 * @author Min Zheng
	 *
	 */
	@Resource
	private Partitioner<Map<Region.Name, Region>, DensityPoint.DensityType> densityPartitioner;

	@Resource
	@Qualifier("vornoiFilter")
	private Partitioner<List<GridPoint>, DensityPoint.DensityType> voronoiPartitioner;

	//MOVE towards composite. Interaction Partitioners
	@Autowired
	private Partitioner<List<GridPoint>, DensityPoint.DensityType> interactionPartitioners;


	/**
	 * execute density based adaptive region by looping over all
	 * regionizers components.
	 * 
	 * @return regions - a list of region
	 *         {@link Region}
	 */
	/**
	 * set up adaptive QM region partitioning :
	 */
	@Override
	public List<Region> regionize(List<Region> regions, List<JobParameter> parameters) {
		Partitioner.Type partitionType = (Partitioner.Type) parameters.get(JobParameter.PARTITIONER);
		if (partitionType  == Partitioner.Type.DORI || partitionType  == Partitioner.Type.SEDD) {



			//Step 3. partitioner is to do interaction analysis to find the adaptive QM.
			regions = partitioner.partition(regions, parameters, gridPoints);    //
		}
		return regions;
	}

}
