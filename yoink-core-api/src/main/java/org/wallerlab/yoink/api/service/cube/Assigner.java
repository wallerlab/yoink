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

package org.wallerlab.yoink.api.service.cube;

import java.util.Map;

/**
 * this interface is to assign one object t to a group/category/object, etc.,
 * and it needs to use another two arguments.
 * 
 * @author Min Zheng
 *
 * @param <T>
 *            ,the type of the object will be assigned. eg.
 *            {@link org.wallerlab.yoink.api.model.molecular.Coord}
 * @param <K>, the type of argument one. eg. , a Map,
 *        {@link org.wallerlab.yoink.api.model.regionizer.Region.Name} as Key,
 *        {@link org.wallerlab.yoink.api.model.regionizer.Region} as Value
 * @param <V>, the type of argument two, eg.
 *        {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
 */
public interface Assigner<T, K, V> {

	/**
	 * the method is to assign t to a group/category/object , etc. and it takes
	 * other two arguments k ,v.
	 * 
	 * @param t
	 *            , the object will be assigned
	 * @param k
	 *            , argument one
	 * @param v
	 *            , argument two
	 * @return a Map {@link java.util.Map}
	 */
	Map assign(T t, K k, V v);
}
