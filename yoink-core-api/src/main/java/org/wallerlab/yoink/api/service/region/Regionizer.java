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

import org.wallerlab.yoink.api.model.Job;

import javax.xml.bind.JAXBElement;

/**
 * this interface is for region operations
 * 
 * @author Min Zheng
 *
*/
@FunctionalInterface
public interface Regionizer {

	/**
	 *
	 * @param job with inputs
	 * @return job populated with results
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
		CLUSTER
    }
}
