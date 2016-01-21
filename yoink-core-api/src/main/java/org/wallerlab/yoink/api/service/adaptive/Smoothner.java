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
package org.wallerlab.yoink.api.service.adaptive;

import javax.xml.bind.JAXBElement;

import org.wallerlab.yoink.api.model.bootstrap.Job;

/**
 * this interface contains method smooth and enum smoothner type
 * 
 * @author Min Zheng
 *
 */
public interface Smoothner {

	/**
	 * calculate smoothing factors for buffer region
	 * 
	 * @param job
	 *            {@link org.wallerlab.yoink.api.model.bootstrap.Job}
	 */
	void smooth(Job<JAXBElement> job);

	public enum Type {
		BUFFERED_FORCE, DISTANCE_DAS, DISTANCE_XS, ABRUPT, DISTANCE_HOTSPOT, DISTANCE_PAP, DISTANCE_SAP, DISTANCE_SCMP,FIRES;
	}
}
