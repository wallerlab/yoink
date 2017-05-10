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
package org.wallerlab.yoink.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Component;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.graph.Graph;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;
import org.xml_cml.schema.Cml;

/**
 * the domain model for job in adaptive qm/mm partitioning
 * 
 * @author Min Zheng
 *
 */
@Component
public class YoinkJob implements Job<JAXBElement> {

	// keep original cml in order to wrap results back to properties in the JAXB
	private JAXBElement<Cml> input;

	private MolecularSystem molecularSystem;
	
	private Graph graph;

	private Map<JobParameter, Object> parameters = new HashMap<JobParameter, Object>();

	private Map<String, Object> properties = new HashMap<String, Object>();

	private Map<Region.Name, Region> regions = new HashMap<Region.Name, Region>();
	private List<List<Integer>> interactionList = new ArrayList<List<Integer>>();

	private  List<Double> interactionWeight = new ArrayList<Double>();

	@Override
	public MolecularSystem getMolecularSystem() {
		return molecularSystem;
	}

	@Override
	public void setMolecularSystem(MolecularSystem molecularSystem) {
		this.molecularSystem = molecularSystem;
	}

	@Override
	public Map<JobParameter, Object> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(Map<JobParameter, Object> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Map<Region.Name, Region> getRegions() {
		return regions;
	}

	@Override
	public void setRegions(Map<Region.Name, Region> regions) {
		this.regions = regions;
	}

	@Override
	public JAXBElement<Cml> getInput() {
		return this.input;
	}

	@Override
	public void setInput(JAXBElement input) {
		this.input = input;
	}

	@Override
	public Map<String, Object> getProperties() {
		return this.properties;
	}

	@Override
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public List<List<Integer>> getInteractionList() {

		return this.interactionList;
	}

	@Override
	public void SetInteractionList(List<List<Integer>> interactionSet) {
		this.interactionList = interactionSet;

	}

	@Override
	public List<Double> getInteractionWeight() {
		return this.interactionWeight;
	}

	@Override
	public void SetInteractionWeight(List<Double> interactionWeight) {
		this.interactionWeight=interactionWeight;
		
	}

	@Override
	public Graph getGraph() {
		return this.graph;
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph=graph;
		
	}
}
