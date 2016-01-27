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

/**
 * <p>JobParameter is used to describe the type of parameter in parameters of job
 * {@link org.wallerlab.yoink.api.model.bootstrap.Job}.</p>
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
 * @author Min Zheng
 *
 */
public enum JobParameter {
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
	NUMBER_PARTITION;
	
}
