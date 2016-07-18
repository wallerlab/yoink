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

import org.wallerlab.yoink.api.model.Job;

/**
 * this interface contains method compute and enum smoothner type
 * 
 * @author Min Zheng
 *
 */
@FunctionalInterface
public interface Adaptive {

	/**
	 * ratio smoothing factors for buffer region
	 * 
	 * @param job
	 *            {@link Job}
	 */
	Job<JAXBElement> compute(Job<JAXBElement> job);

	enum Type {

		BUFFERED_FORCE,
		DAS,
		XS,
		ABRUPT,
		HOTSPOT,
		PAP,
		SAP,
		SCMP,
		FIRES;
	}
}
