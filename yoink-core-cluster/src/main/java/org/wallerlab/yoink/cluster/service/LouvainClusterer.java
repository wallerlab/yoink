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
package org.wallerlab.yoink.cluster.service;

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.Interaction;
import org.wallerlab.yoink.api.service.cluster.Clusterer;
import org.wallerlab.yoink.cluster.service.louvain.LouvainClusteringFacade;

import java.util.*;
import javax.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is to build a graph based on DORI interaction (yes or no)
 * and then louvain clustering.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class LouvainClusterer implements Clusterer {

	@Autowired
	LouvainClusteringFacade<Integer> louvain;

	public Job<JAXBElement> cluster(Job<JAXBElement> job, Set<Interaction> interactions) {
		louvain.populate(interactions);
		int maximumNumberOfCommunities = (int) job.getParameter(Job.JobParameter.MAX_COMMS);
		Map<Long, Integer> result = louvain.cluster(maximumNumberOfCommunities);
		List<Set<Integer>> clusters = louvain.getResult(result.size() - 1);
		louvain.shutdown();
		job.setClusters(clusters);
		return job;
	}

}
