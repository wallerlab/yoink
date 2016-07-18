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
package org.wallerlab.yoink.api.model;

import org.wallerlab.yoink.api.model.adaptive.AdaptiveMolecularSystem;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * get the parameters during the job
	 * 
	 * @return a Map. -
	 *         {@link JobParameter} as
	 *         key, Object as value
	 */
	Map<JobParameter, Object> getParameters();

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
	 * get the properties from the job.
	 * 
	 * @return a Map contains the result of job. - {@link java.lang.String} as
	 *         key, {@link java.lang.Object} as value.
	 */
	Map<String, Object> getProperties();

	/**
	 * get the properties from the job.
	 *
	 * @return a Map contains the result of job. - {@link java.lang.String} as
	 *         key, {@link java.lang.Object} as value.
	 */
	Object getProperty(String string);

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

	Set<MolecularSystem.Molecule> getMoleculesInRegion(Region.Name regionName);

	Region getRegion(Region.Name name);

	Object getParameter(JobParameter jobParameter);

	void setAdaptiveMolecularSystem(AdaptiveMolecularSystem adaptiveMolecularSystem);

	/**
     * <p>JobParameter is used to describe the type of parameter in parameters of job
     * {@link Job}.</p>
     *
     *
     *<p>  PARTITIONER: the partitioner type DISTANCE/NUMBER/DORI</p>
     *<p>  OUTPUT_FOLDER: the folder for out put files</p>
     *<p>	JOB_NAME: the name of the job, and the prefix of the result file</p>
     *<p> parameters for distance and number-based partitioning </p>
     *<p>  NUMBER_QM: number of qm molecules in number-based partitioning</p>
     *<p>	NUMBER_BUFFER: number of buffer(transition) molecules in number-based partitioning</p>
     *<p>	DISTANCE_QM: number of  qm molecules in distance-based partitioning</p>
     *<p>	DISTANCE_BUFFER: number of buffer(transition) molecules in distance-based partitioning </p>
     *<p> parameters for density-based partitioning</p>
     *<p>	DORI: DORI threshold in density-based partitioning</p>
     *<p>	SEDD: SEDD threshold in density-based partitioning</p>
     *<p>	DENSITY_SEDD: density threshold in SEDD analysis</p>
     *<p>	DENSITY_DORI: density threshold in DORI analysis</p>
     *<p>	DENSITY_ASR_QMCORE: density threshold of adaptive search region for adaptive qm core (SEDD analysis)</p>
     *<p>	DENSITY_ASR_QM:  density threshold of adaptive search region for adaptive qm (DORI analysis)</p>
     *<p>	DENSITY_RATIO_MIN: the minimum of density ratio of two atoms having inter-atomic interaction</p>
     *<p>	DENSITY_RATIO_MAX: the maximum of density ratio of two atoms having inter-atomic interaction </p>
     *<p>	SEDD_STEPSIZE: the step size of the cube in SEDD analysis.</p>
     *<p>	DORI_STEPSIZE:  the step size of the cube in DORI analysis.</p>
     *<p>	REGION_CUBE: the region to construct a cube for the DORI/SEDD calculation. The suggested one is ADAPTIVE_SEARCH.</p>
     *<p>	DENSITY_BUFFER: the density threshold for the buffer(transition) region in density-based partitioning</p>
     *<p>	DENSITY_QM: the density threshold for the QM region in density-based partitioning, used in smoothing function.</p>
     *<p>smoothing </p>
     *<p>	SMOOTHNER: the smoothing type.</p>
     *<p>smoothing DISTANCE_SCMP</p>
     *<p>	DISTANCE_S_QM_IN: distance threshold for QM molecules into QM region</p>
     *<p>	DISTANCE_T_QM_IN: distance threshold for QM molecules into transition region</p>
     *<p>	DISTANCE_S_QM_OU: distance threshold for QM molecules out of QM region</p>
     *<p>	DISTANCE_T_QM_OUT: distance threshold for QM molecules out of transition</p>
     *<p>	DISTANCE_S_MM_IN:  distance threshold for MM molecules into QM region</p>
     *<p>	DISTANCE_T_MM_IN: distance threshold for MM molecules into transition region</p>
     *<p>	DISTANCE_S_MM_OUT: distance threshold for MM molecules  out of QM region</p>
     *<p>	DISTANCE_T_MM_OUT: distance threshold for MM molecules out of transition</p>
     *<p>	NUMBER_PARTITION: number of partitioning in DISTANCE_SCMP	</p>
     *<p>	DGRID: adopt radial grid for promolecular density 	</p>
     *<p>	MAX_COMMS:  number of max communities of region	</p>
     *<p> INTERACTION_WEIGHT: True or False to ratio the strength(weights) of the interaction  </p>
     *<p>	WFC_PATH: the database path of radial grid for promolecular density  	</p>
     * @author Min Zheng
     *
     */
	enum JobParameter {
        PARTITIONER,
        OUTPUT_FOLDER,
        JOB_NAME,
        NUMBER_QM,
        NUMBER_BUFFER,
        DISTANCE_QM,
        DISTANCE_BUFFER,
        DORI,
        SEDD,
        DENSITY_SEDD,
        DENSITY_DORI,
        DENSITY_ASR_QMCORE,
        DENSITY_ASR_QM,
        SEDD_STEPSIZE,
        DORI_STEPSIZE,
        DENSITY_RATIO_MIN,
        DENSITY_RATIO_MAX,
        REGION_CUBE,
        DENSITY_QM,
        DENSITY_BUFFER,
        SMOOTHNER,
        DISTANCE_S_QM_IN,
        DISTANCE_T_QM_IN,
        DISTANCE_S_QM_OUT,
        DISTANCE_T_QM_OUT,
        DISTANCE_S_MM_IN,
        DISTANCE_T_MM_IN,
        DISTANCE_S_MM_OUT,
        DISTANCE_T_MM_OUT,
        NUMBER_PARTITION,
        WFC_PATH,
        MAX_COMMS,
        INTERACTION_WEIGHT,
        DGRID,
        SMOOTH_FUNCTION;

    }
}
