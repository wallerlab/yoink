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
package org.wallerlab.yoink.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.molecular.RadialGrid;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.bootstrap.Clustering;
import org.wallerlab.yoink.api.service.molecular.FilesReader;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.clustering.InteractionTriple;
import org.wallerlab.yoink.clustering.LouvainClusteringFacade;
import org.wallerlab.yoink.molecular.domain.SimpleRadialGrid;
import org.wallerlab.yoink.regionizer.partitioner.DensityPartitioner;
import org.wallerlab.yoink.service.clustering.InteractionSet;

/**
 * This class is to do density interaction analysis (dori/sedd ) for those grid
 * points in the intersection between QM core region and non-QM core region
 * based on Voronoi partition.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class DoriClustering implements Clustering {

	public DoriClustering() {

	}

	@Override
	public void cluster(Job job) {
		Map<JobParameter, Object> parameters = job.getParameters();

		Partitioner.Type partitionType = (Partitioner.Type) parameters
				.get(JobParameter.PARTITIONER);
		if (partitionType == Partitioner.Type.CLUSTER) {
			List<InteractionTriple<Integer>> interactionTriples = getInteractionTriples(
					job, parameters);
			List<Set<Integer>> clusters = louvainClustering(interactionTriples,
					parameters);
			job.setClusters(clusters);
		}

	}

	private List<InteractionTriple<Integer>> getInteractionTriples(Job job,
			Map<JobParameter, Object> parameters) {
		List<InteractionTriple<Integer>> interactionTriples = new ArrayList<InteractionTriple<Integer>>();
		List<Double> weightList = new ArrayList<Double>();
		List<List<Integer>> interactionList = job.getInteractionList();
		if ((Boolean) parameters.get(JobParameter.INTERACTION_WEIGHT)) {
			weightList = job.getInteractionWeight();
			double weightMin=Collections.min(weightList);
			double weightMax=Collections.max(weightList);
			System.out.println(weightMax+"  "+weightMin);
			double normal = 1.0/(weightMax-weightMin);
			for(int i=0;i<weightList.size();i++){
				
				weightList.set(i,weightList.get(i)*normal);
			}
			
		} else{
		
			Double[] weightArray = new Double[interactionList.size()];
			Arrays.fill(weightArray, 1.0);

			weightList.addAll(Arrays.asList(weightArray));
		
		}
		for (int i = 0; i < interactionList.size(); i++) {
			List<Integer> pair = interactionList.get(i);
			System.out.println(pair.get(0)+"  "+ pair.get(1)+"  "+  weightList.get(i));
			InteractionTriple<Integer> triple = new InteractionTriple<Integer>(
					pair.get(0), pair.get(1), weightList.get(i));
			interactionTriples.add(triple);
		}
		return interactionTriples;
	}

	public List<Set<Integer>> louvainClustering(
			List<InteractionTriple<Integer>> interactionSet,
			Map<JobParameter, Object> parameters) {
		String parentDirName = (String) parameters
				.get(JobParameter.OUTPUT_FOLDER) + "/";
		LouvainClusteringFacade<Integer> louvain = new LouvainClusteringFacade<Integer>(
				parentDirName + "testDb/databases/graph.db");
		louvain.populate(interactionSet);
		int maxCommunities = (int) parameters.get(JobParameter.MAX_COMMS);
		Map<Long, Integer> result = louvain.cluster(maxCommunities);
		List<Set<Integer>> clusters = louvain.getResult(result.size() - 1);
		louvain.shutdown();
		System.out.println("clusters: " + clusters);
		return clusters;

	}

}
