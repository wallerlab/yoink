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

import org.wallerlab.yoink.api.model.regionizer.Region;

/**
 * this interface is for region operations
 * 
 * @author Min Zheng
 *
 * @param <T>
 *            -specified return type, can be
 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} or a
 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} List
 * @param <K>
 *            -specified argument, can be a
 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name} or
 *            {@link org.wallerlab.yoink.api.model.molecular.MolecularSystem}
 */
public interface Regionizer<T, K> {

	/**
	 * 
	 * @param regions
	 *            , a Map,
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 *            as Key,
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} as
	 *            Value
	 * @param k
	 *            -specified argument
	 * @return return type is specified T
	 */
	 T regionize(Map<Region.Name, Region> regions, K k);

}
