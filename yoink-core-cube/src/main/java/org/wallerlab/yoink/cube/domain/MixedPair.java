package org.wallerlab.yoink.cube.domain;

import java.util.Set;


public class MixedPair<S,T> {

    private final S first;
    private final T second;


    public MixedPair(final S first, final T second){
        this.first = first;
        this.second = second;
    }

    public S getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
