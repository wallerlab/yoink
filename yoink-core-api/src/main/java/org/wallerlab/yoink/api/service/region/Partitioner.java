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
package org.wallerlab.yoink.api.service.region;

import java.util.List;
import java.util.Map;

import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.model.density.DensityPoint;

/**
 * this interface contains method partition() and enum Partitioner.Type. When
 * V is {@link DensityPoint.DensityType }
 * , the return type K is a List of grid points or a Map,
 * {@link Region.Name} as Key,
 * {@link Region} as Value.
 * {@link GridPoint }. when V is a List of
 * grid points {@link GridPoint }, the return
 * type K is a Map,
 * {@link Region.Name} as Key,
 * {@link Region} as Value
 * 
 * @author Min Zheng
 *
 * @param <K>
 *            -the return type.
 * @param <V>
 *            -the specified type for argument.
 */
public interface Partitioner {

	/**
	 * 
	 * @param regions
	 *            , a Map,
	 *            {@link Region.Name}
	 *            as Key,
	 *            {@link Region} as
	 *            Value
	 * @param parameters
	 *            , a Map,
	 *            {@link JobParameter}
	 *            as Key, {@link java.lang.Object} as Value
	 * @param v
	 *            -the specified argument
	 * @return return type is K
	 */
	List<Region> partition(List<Region> regions, List<JobParameter> parameters);

	public enum Type {

		NUMBER, DISTANCE,SIZE,
		DORI,SEDD,
		FIRES,CLUSTER;

	}
}
