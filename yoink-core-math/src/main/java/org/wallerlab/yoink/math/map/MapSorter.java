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

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * This calss is to sort a map
 * 
 * @author Min Zheng
 *
 */

public class MapSorter {

	/**
	 * This method is to sort a map by its value in ascending way.
	 * 
	 * @param <K>
	 *            - the data type of Key of a Map
	 * @param <V>
	 *            - the data type of Value of a Map
	 * @param map
	 *            - {@link java.util.Map}
	 * @return a map - {@link java.util.Map}
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		Map<K, V> result = new LinkedHashMap<>();
		Stream<Entry<K, V>> st = map.entrySet().stream();
		st.sorted(Comparator.comparing(e -> e.getValue())).forEach(
				e -> result.put(e.getKey(), e.getValue()));
		return result;
	}

}
