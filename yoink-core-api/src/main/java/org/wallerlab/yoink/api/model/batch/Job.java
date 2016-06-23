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
package org.wallerlab.yoink.api.model.batch;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;

/**
 * this interface is for the Job domain model
 * 
 * @param <T>, the specified type input for the job
 * @author Min Zheng
 *
 */
public interface Job<T> {

	/**
	 * get the molecule system from the job
	 * 
	 * @return molecularSystem -
	 *         {@link MolecularSystem }
	 */
	MolecularSystem getMolecularSystem();

	/**
	 * set the molecule system into the job
	 * 
	 * @param molecularSystem
	 *            -
	 *            {@link MolecularSystem }
	 */
	void setMolecularSystem(MolecularSystem molecularSystem);

	/**
	 * get the parameters during the job
	 * 
	 * @return a Map. -
	 *         {@link JobParameter} as
	 *         key, Object as value
	 */
	Map<JobParameter, Object> getParameters();

	/**
	 * set the parameters in the job
	 * 
	 * @param parameters
	 *            , a Map, -
	 *            {@link JobParameter}
	 *            as key, Object as value
	 */
	void setParameters(Map<JobParameter, Object> parameters);

	/**
	 * get the regions out of the job
	 * 
	 * @return a Map. -
	 *         {@link Region.Name} as
	 *         key,{@link Region} as
	 *         value.
	 * 
	 */
	Map<Region.Name, Region> getRegions();

	/**
	 * set the regions inside the job
	 * 
	 * @param regions
	 *            ,a Map. -
	 *            {@link Region.Name}
	 *            as key,{@link Region}
	 *            as value.
	 */
	void setRegions(Map<Region.Name, Region> regions);

	/**
	 * get the input from the job
	 * 
	 * @return the specified type input
	 */
	T getInput();

	/**
	 * set the input in the job
	 * 
	 * @param input
	 *            , the input to start a job. eg. it can be a file ,or
	 *            JAXBElement from a Cml file
	 */
	void setInput(T input);

	/**
	 * get the properties from the job.
	 * 
	 * @return a Map contains the result of job. - {@link java.lang.String} as
	 *         key, {@link java.lang.Object} as value.
	 */
	Map<String, Object> getProperties();

	/**
	 * set the value of properties in the job.
	 * 
	 * @param properties
	 *            , a Map contains the result of job. - {@link java.lang.String}
	 *            as key, {@link java.lang.Object} as value.
	 */
	void setProperties(Map<String, Object> properties);

	/**
	 * get the interaction pairs
	 * 
	 * @return list of pairs
	 */
	List<List<Integer>> getInteractionList();

	/**
	 * set the interaction pairs
	 * 
	 * @param interactionSet after analysis
	 */
	void SetInteractionList(List<List<Integer>> interactionSet);

	/**
	 * get the weights(strength) of the interaction
	 * 
	 * @return list of weights
	 */
	List<Double> getInteractionWeight();

	/**
	 * set the weights(strength) of the interaction
	 * 
	 * @param interactionWeight weights of interaction
	 */
	void SetInteractionWeight(List<Double> interactionWeight);

	/**
	 * set the result from the clustering
 	 *
	 * @param clusters set after analysis
	 */
	void setClusters(List<Set<Integer>> clusters);

	/**
	 * get the clustering result
	 * 
	 * @return result of region
	 */
	List<Set<Integer>> getClusters();
}
