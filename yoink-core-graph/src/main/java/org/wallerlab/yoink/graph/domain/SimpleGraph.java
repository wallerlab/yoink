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

package org.wallerlab.yoink.graph.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.wallerlab.yoink.api.model.graph.Graph;

@Component
public class SimpleGraph implements Graph {

	private List<List<Integer>> edges = new ArrayList<List<Integer>>();;

	private List<Double> weights = new ArrayList<Double>();

	public SimpleGraph() {

	}

	@Override
	public void setEdges(List<List<Integer>> edges) {
		this.edges = edges;
	}

	@Override
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}

	@Override
	public void writeGraphFile(String graphFilePath, Boolean includeWeights) {
		System.out.println("graphFilePath: " + graphFilePath);
		List<Double> weights = this.weights;
		try {
			System.out.println("graphFilePath: " + graphFilePath);
			File statText = new File(graphFilePath);
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);
			for (int i = 0; i < weights.size(); i++) {
				List<Integer> edge = this.edges.get(i);
				w.write(edge.get(0).toString() + "," + edge.get(1).toString());
				if (includeWeights) {
					w.write("  " + weights.get(i).toString());
				}
				w.flush();
				w.write("\n");
			}
			w.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file: " + graphFilePath);
		}
	}

	@Override
	public List<List<Integer>> getEdges() {
		return this.edges;
	}

	@Override
	public List<Double> getWeights() {
		return this.weights;
	}

}
