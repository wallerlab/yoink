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
package org.wallerlab.yoink.region.service.partitioners;

import java.util.Map;
import java.util.Set;

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

/**
 * this interface contains method partition() and enum Partitioner.Type.

 * When V is {@link DensityPoint.DensityType }
 * , the return type K is a List of grid points or a Map,
 * {@link Region.Name} as Key,
 * {@link Region} as Value.
 * {@link VoronoiPoint }. when V is a List of
 * grid points {@link VoronoiPoint }, the return
 * type K is a Map,
 * {@link Region.Name} as Key,
 * {@link Region} as Value
 *
 * @author Min Zheng
 *
 */
public interface Partitioner {

	/**
	 *
	 * @param job as input
	 * @return map containing results
	 */
	Map<Region.Name,Set<MolecularSystem.Molecule>> partition(Job job);

}
