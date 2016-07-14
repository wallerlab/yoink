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
package org.wallerlab.yoink.batch.domain;

import java.util.*;

import javax.xml.bind.JAXBElement;

import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.xml_cml.schema.Cml;

/**
 * The domain model for job in adaptive qm/mm partitioning
 * 
 * @author Min Zheng
 *
 */
public class AdaptiveQMMMJob implements Job<JAXBElement> {

	// keep original cml in order to wrap results back to properties in the JAXB
	private JAXBElement<Cml> input;

	// Internal domain model
	private MolecularSystem molecularSystem;

	// Results from RegionizerService
	private Map<Region.Name,Region> regions;
	//
	private Map<JobParameter, Object> parameters = new HashMap<JobParameter, Object>();

	private Map<String, Object> properties = new HashMap<String, Object>();

	// Results from Clusterer
	private List<List<Integer>> interactionList;
	private List<Set<Integer>> clusters;
	private List<Double> interactionWeight;

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
	public Map<Region.Name,Region> getRegions() {
		return regions;
	}

	@Override
	public void addRegion(Region region) {
	}

	@Override
	public void setRegions(Map<Region.Name, Region> regions) {
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
	public Object getProperty(String string) {
		return null;
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
	public void setClusters(List<Set<Integer>> clusters) {
		this.clusters = clusters;
	}

	@Override
	public List<Set<Integer>> getClusters() {
		return this.clusters;
	}

	@Override
	public Set<Molecule> getMoleculesInRegion(Region.Name regionName) {
		return null;
	}

	@Override
	public Region getRegion(Region.Name name) {
		return null;
	}

	@Override
	public JobParameter getParameter(JobParameter jobParameter) {
		return null;
	}

	@Override
	public List<Double> getInteractionWeight() {
		return this.interactionWeight;
	}

	@Override
	public void SetInteractionWeight(List<Double> interactionWeight) {
		this.interactionWeight=interactionWeight;
	}

}
