package org.wallerlab.yoink.math.set;

import java.util.HashSet;
import java.util.Set;

/**
 * this class is to calculate the difference of two sets
 * 
 *
 */
public class SetDifference {

	/**
	 * This method is to calculate the difference of two Sets. If there is no
	 * same element in two sets, the difference is the sum of the two sets
	 * 
	 * @param <T>
	 *            - the data type of a Set
	 * @param s1
	 *            - a specified type Set,{@link java.util.Set}
	 * @param s2
	 *            - a specified type Set,{@link java.util.Set}
	 * @return the difference of two sets, {@link java.util.Set}
	 */
	public static <T> Set<T> diff(Set<? extends T> s1, Set<? extends T> s2) {
		Set<T> symmetricDiff = new HashSet<T>(s1);
		symmetricDiff.addAll(s2);
		Set<T> tmp = new HashSet<T>(s1);
		tmp.retainAll(s2);
		symmetricDiff.removeAll(tmp);
		return symmetricDiff;
	}

}
