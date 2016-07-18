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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.service.cluster.Clusterer;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.cluster.service.louvain.LouvainClusteringFacade;
import org.wallerlab.yoink.cluster.service.interaction.InteractionTriple;

import javax.xml.bind.JAXBElement;

/**
 * This class is to build a graph based on DORI interaction (yes or no) and do
 * louvain region.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class DoriClustering implements Clusterer {

	public DoriClustering() {
	}

	@Override
	public Job cluster(Job<JAXBElement> job) {
		Map<Job.JobParameter, Object> parameters = job.getParameters();
		Regionizer.Type regionizer = (Regionizer.Type) parameters.get(Job.JobParameter.PARTITIONER);
		if (regionizer == Regionizer.Type.CLUSTER) {
			List<InteractionTriple<Integer>> interactionTriples = getInteractionTriples(
					job, parameters);
			List<Set<Integer>> clusters = louvainClustering(interactionTriples,
					parameters);
			job.setClusters(clusters);
		}
		return job;
	}

	public List<Set<Integer>> louvainClustering(List<InteractionTriple<Integer>> interactionSet,
			Map<Job.JobParameter, Object> parameters) {
		String parentDirName = (String) parameters
				.get(Job.JobParameter.OUTPUT_FOLDER) + "/";
		LouvainClusteringFacade<Integer> louvain = new LouvainClusteringFacade<Integer>(
				parentDirName + "testDb/databases/graph.db");
		louvain.populate(interactionSet);
		int maxCommunities = (int) parameters.get(Job.JobParameter.MAX_COMMS);
		Map<Long, Integer> result = louvain.cluster(maxCommunities);
		List<Set<Integer>> clusters = louvain.getResult(result.size() - 1);
		louvain.shutdown();
		System.out.println("clusters: " + clusters);
		return clusters;

	}

	private List<InteractionTriple<Integer>> getInteractionTriples(Job job, Map<Job.JobParameter, Object> parameters) {
		List<InteractionTriple<Integer>> interactionTriples = new ArrayList<InteractionTriple<Integer>>();
		List<Double> weightList = job.getInteractionWeight();
		List<List<Integer>> interactionList = job.getInteractionList();
		for (int i = 0; i < interactionList.size(); i++) {
			List<Integer> pair = interactionList.get(i);
			InteractionTriple<Integer> triple = new InteractionTriple<Integer>(
					pair.get(0), pair.get(1), weightList.get(i));
			interactionTriples.add(triple);
		}
		return interactionTriples;
	}


}
