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

import java.util.Map;

import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;

import javax.xml.bind.JAXBElement;

/**
 * this interface is for region operations
 * 
 * @author Min Zheng
 *
 * @param <T>
 *            -specified return type, can be
 *            {@link Region} or a
 *            {@link Region} List
 * @param <K>
 *            -specified argument, can be a
 *            {@link Region.Name} or
 *            {@link MolecularSystem}
 */
@FunctionalInterface
public interface Regionizer {

	/**
	 * 
	 * @param regions
	 *            , a Map,
	 *            {@link Region.Name}
	 *            as Key,
	 *            {@link Region} as
	 *            Value
	 *
	 * @return return type is specified T
	 */
	Job<JAXBElement> regionize(Job<JAXBElement> job);


	enum Type {
		NUMBER,
		DISTANCE,
		SIZE,
		DENSITY,
		DORI,
		SEDD,
		FIRES,
		CLUSTER;
	}
}
