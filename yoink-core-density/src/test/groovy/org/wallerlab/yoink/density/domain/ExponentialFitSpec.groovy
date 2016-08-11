package org.wallerlab.yoink.density.domain

import org.wallerlab.yoink.api.service.math.Vector;

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by waller on 11/08/16.
 */
class ExponentialFitSpec extends Specification {

    @Unroll
    def "test the density here"() {

        expect:
        ExponentialFit.Al.C().getEntry(0) == 1319.0
        ExponentialFit.H.C().getEntry(0) == 0.2815
        for(ExponentialFit e : ExponentialFit.values()) {
            e.C() != null
            e.C() instanceof Vector
            e.Z() != null
            e.Z() instanceof Vector
        }

    }
}
