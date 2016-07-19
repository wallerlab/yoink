package org.wallerlab.yoink.cluster.domain.interaction;

import org.wallerlab.yoink.api.model.Interaction;

public class SimpleInteraction<T> implements Interaction<T> {

	public final T first;
	
	public final T second;
	
	public final double weight;

	public SimpleInteraction(T first, T second, double weight) {
		this.first = first;
		this.second = second;
		this.weight = weight;
	}

	@Override
	public T getFirst() {
		return first;
	}

	@Override
	public T getSecond() {
		return second;
	}

	@Override
	public double getWeight() {
		return weight;
	}
}
