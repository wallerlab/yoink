package org.wallerlab.yoink.clustering;

public class InteractionTriple<T> {

	public final T first;
	
	public final T second;
	
	public final double weight;

	public InteractionTriple(T first, T second, double weight) {
		this.first = first;
		this.second = second;
		this.weight = weight;
	}

}
