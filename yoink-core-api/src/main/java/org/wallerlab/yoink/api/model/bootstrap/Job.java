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
package org.wallerlab.yoink.api.model.bootstrap;

import java.util.Map;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.regionizer.Region;

/**
 * this interface is for the Job domain model 
 * 
 * @param <T>, the specified type input for the job
 * @author Min Zheng
 *
 */
public interface Job<T> {

	/**
	 * get the value of molecular system in the job
	 * 
	 * @return molecularSystem -
	 *         {@link org.wallerlab.yoink.api.model.molecular.MolecularSystem }
	 */
	MolecularSystem getMolecularSystem();

	/**
	 * set the value of molecular system
	 * 
	 * @param molecularSystem
	 *            -
	 *            {@link org.wallerlab.yoink.api.model.molecular.MolecularSystem }
	 */
	void setMolecularSystem(MolecularSystem molecularSystem);

	/**
	 * get the parameters during the job
	 * 
	 * @return a Map. -
	 *         {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter} as
	 *         key, Object as value
	 */
	Map<JobParameter, Object> getParameters();

	/**
	 * set the value of parameters
	 * 
	 * @param parameters
	 *            , a Map, -
	 *            {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter}
	 *            as key, Object as value
	 */
	void setParameters(Map<JobParameter, Object> parameters);

	/**
	 * get the value of regions in the job
	 * 
	 * @return a Map. -
	 *         {@link org.wallerlab.yoink.api.model.regionizer.Region.Name} as
	 *         key,{@link org.wallerlab.yoink.api.model.regionizer.Region} as
	 *         value.
	 * 
	 */
	 Map<Region.Name, Region> getRegions();

	/**
	 * set the value of regions in the job
	 * 
	 * @param regions
	 *            ,a Map. -
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 *            as key,{@link org.wallerlab.yoink.api.model.regionizer.Region}
	 *            as value.
	 */
	void setRegions(Map<Region.Name, Region> regions);

	/**
	 * get the value of input in the job
	 * 
	 * @return the specified type input
	 */
	T getInput();

	/**
	 * set the value of input in the job
	 * 
	 * @param input
	 *            , the input to start a job. eg. it can be a file ,or
	 *            JAXBElement from a Cml file
	 */
	 void setInput(T input);

	/**
	 * get the value of properties in the job.
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

}
