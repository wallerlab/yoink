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
 * JobParameter is used to describe the type of parameter in parameters of job
 * {@link org.wallerlab.yoink.api.model.bootstrap.Job}.
 * 
 * @author Min Zheng
 *
 */
public enum JobParameter {
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
	DENSITY_QM, 
	DENSITY_BUFFER,
	SEDD_STEPSIZE, 
	DORI_STEPSIZE, 
	SMOOTHNER, 
	PARTITIONER, 
	DENSITY_RATIO_MIN, 
	DENSITY_RATIO_MAX, 
	REGION_CUBE, 
	JOB_NAME, 
	INPUT_FOLDER, 
	OUTPUT_FOLDER,
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
