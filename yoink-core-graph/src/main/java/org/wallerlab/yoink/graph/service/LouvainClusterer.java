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
package org.wallerlab.yoink.graph.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.wallerlab.yoink.api.model.graph.Graph;
import org.wallerlab.yoink.api.model.molecule.Atom;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.graph.Clusterer;
import org.wallerlab.yoink.api.service.graph.Grapher;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.region.partitioner.DensityPartitioner;

/**
 * This class is to get all pairs having interaction(yes or no) base on DORI
 * analysis.
 * 
 * 
 * @author Min Zheng
 *
 */
@Service
public class LouvainClusterer implements Clusterer {

	public LouvainClusterer() {

	}

	@Override
	public void clustering(Job job) {
		// write the graph file
		Graph graph = job.getGraph();
		String name = (String) job.getParameters().get(JobParameter.JOB_NAME);
		String parentDirName = (String) job.getParameters().get(
				JobParameter.OUTPUT_FOLDER)
				+ "/";
		String graphFileName = parentDirName + name + "-graph.txt";
		Boolean includeWeights = false;
		graph.writeGraphFile(graphFileName, includeWeights);
		// call external code for clustering
		// TODO: the command could not be executed
		String command = "/usr/bin/java -jar ./libs/jar/CONCLUDE/CONCLUDE.jar  "
				+ graphFileName
				+ " conclude-clustered \" \""
				+ "  > conclude-clustered.log&";
		try {
			// Runtime rt = Runtime.getRuntime();
			// Process p = rt.exec(command);
			// p.waitFor();
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
