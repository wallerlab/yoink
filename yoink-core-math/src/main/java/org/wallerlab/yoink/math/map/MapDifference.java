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
package org.wallerlab.yoink.math.map;

import java.util.HashMap;
import java.util.Map;

/**
 * this class is to get the difference of two maps
 * 
 * @author Min Zheng
 *
 */

public class MapDifference {

	/**
	 * this method is to get the difference between map1 and map2. the result is
	 * to remove the same elements between map1 and map2 in map1
	 * 
	 * @param <K>
	 *            - the data type of Key of a Map
	 * @param <V>
	 *            - the data type of Value of a Map
	 * @param map1
	 *            - {@link java.util.Map}
	 * @param map2
	 *            - {@link java.util.Map}
	 * @return map -- {@link java.util.Map}
	 */
	public static <K, V> Map<K, V> diff(Map<? extends K, ? extends V> map1,
			Map<? extends K, ? extends V> map2) {
		Map<K, V> result = new HashMap<>();
		result.putAll(map1);
		result.putAll(map2);
		result.entrySet().removeAll(map2.entrySet());
		return result;
	}

}
