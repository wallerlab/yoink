package org.wallerlab.yoink.api.model;

/**
 * Created by waller on 18/07/16.
 */
public interface Interaction<T> {
    T getFirst();

    T getSecond();

    double getWeight();
}
