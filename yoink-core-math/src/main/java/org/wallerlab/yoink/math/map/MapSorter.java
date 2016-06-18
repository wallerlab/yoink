package org.wallerlab.yoink.math.map;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * This class is to sort a map
 * 
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
