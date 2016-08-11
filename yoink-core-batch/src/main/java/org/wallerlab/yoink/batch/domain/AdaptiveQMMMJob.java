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

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.adaptive.AdaptiveMolecularSystem;

import java.util.*;
import org.cml_v3.generated.Cml;
import javax.xml.bind.JAXBElement;

/**
 * The domain model for job in adaptive qm/mm partitioning
 * 
 * @author Min Zheng
 *
 */
public class AdaptiveQMMMJob implements Job<JAXBElement> {

	// keep original cml in order to insert results back into properties in the JAXB
	//TODO how about an input that gets modified to be output. That way always know if they are consistent,
	// putting it back through the code produces same result. Just an idea.
	private final JAXBElement<Cml> input;

	private final MolecularSystem molecularSystem;

	private final Map<JobParameter, Object> parameters;

	private AdaptiveMolecularSystem adaptiveMolecularSystem;

	public AdaptiveQMMMJob(final JAXBElement<Cml> input, final MolecularSystem molecularSystem, final Map<JobParameter, Object> parameters) {
		this.input = input;
		this.molecularSystem = molecularSystem;
		this.parameters = parameters;
	}

	private Map<String, Object> properties = new HashMap<String, Object>();

	private Map<Region.Name,Region> regions;

	// Results from Clusterer
	private List<List<Integer>> interactionList;
	private List<Set<Integer>> clusters;
	private List<Double> interactionWeight;

	@Override
	public MolecularSystem getMolecularSystem() {
		return molecularSystem;
	}

	@Override
	public Map<JobParameter, Object> getParameters() {
		return parameters;
	}

	@Override
	public Map<Region.Name,Region> getRegions() {
		return regions;
	}

	public void setRegions(Map<Region.Name, Region> regions) {
		this.regions = regions;
	}

	@Override
	public JAXBElement<Cml> getInput() {
		return this.input;
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
	public Set<MolecularSystem.Molecule> getMoleculesInRegion(Region.Name regionName) {
		return null;
	}

	@Override
	public Region getRegion(Region.Name name) {
		return null;
	}

	@Override
	public Object getParameter(JobParameter jobParameter) {
		return parameters.get(jobParameter);
	}

	@Override
	public void setAdaptiveMolecularSystem(AdaptiveMolecularSystem adaptiveMolecularSystem) {
		this.adaptiveMolecularSystem = adaptiveMolecularSystem;
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
