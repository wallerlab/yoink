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
package org.wallerlab.yoink.api.service;

/**
 * this interface is to create a new instances of a class.
 * 
 * @author Min Zheng
 *
 * @param <T> -the type of new instance 
 * @param <K> -initial value of new instance
 */
public interface Factory<T, K> {

	/**
	 * use default constructor to generate a new object of T
	 * 
	 * @return a new object of type T
	 */
	T create();

	/**
	 * make a new object of type T with an initial value
	 * @param k -initial value for new object
	 * @return  a new object of type T with an initial value
	 */
	T create(K k);
}
