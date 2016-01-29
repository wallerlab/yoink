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
package org.wallerlab.yoink.api.service.bootstrap;


import org.wallerlab.yoink.api.model.bootstrap.Job;

/**
 * this interface is to build a job -
 * {@link org.wallerlab.yoink.api.model.bootstrap.Job}
 * 
 * @author Min Zheng
 *
 * @param <T>
 *            - the input type of Job
 */
public interface JobBuilder<I,O> {

	/**
	 * this method take the builderInput to build a job
	 * 
	 * @param builderInput
	 * @return {@link org.wallerlab.yoink.api.model.bootstrap.Job}
	 */
	Job<O> build(I input);

}
