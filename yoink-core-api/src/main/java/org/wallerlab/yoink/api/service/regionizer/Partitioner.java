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
package org.wallerlab.yoink.api.service.regionizer;

import java.util.Map;

import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.regionizer.Region;

/**
 * this interface contains method partition() and enum Partitioner.Type. When
 * V is {@link org.wallerlab.yoink.api.model.density.DensityPoint.DensityType }
 * , the return type K is a List of grid points or a Map,
 * {@link org.wallerlab.yoink.api.model.regionizer.Region.Name} as Key,
 * {@link org.wallerlab.yoink.api.model.regionizer.Region} as Value.
 * {@link org.wallerlab.yoink.api.model.cube.GridPoint }. when V is a List of
 * grid points {@link org.wallerlab.yoink.api.model.cube.GridPoint }, the return
 * type K is a Map,
 * {@link org.wallerlab.yoink.api.model.regionizer.Region.Name} as Key,
 * {@link org.wallerlab.yoink.api.model.regionizer.Region} as Value
 * 
 * @author Min Zheng
 *
 * @param <K>
 *            -the return type.
 * @param <V>
 *            -the specified type for argument.
 */
public interface Partitioner<K, V> {

	/**
	 * 
	 * @param regions
	 *            , a Map,
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 *            as Key,
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} as
	 *            Value
	 * @param parameters
	 *            , a Map,
	 *            {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter}
	 *            as Key, {@link java.lang.Object} as Value
	 * @param v
	 *            -the specified argument
	 * @return return type is K
	 */
	K partition(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, V v);

	public enum Type {

		DORI, NUMBER, DISTANCE,SIZE,FIRES;

	}
}
