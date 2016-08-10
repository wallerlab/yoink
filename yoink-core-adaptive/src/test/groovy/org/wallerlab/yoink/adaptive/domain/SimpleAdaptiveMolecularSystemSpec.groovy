package org.wallerlab.yoink.adaptive.domain

import org.apache.commons.math3.geometry.Vector
import org.wallerlab.yoink.adaptive.domain.SimpleAdaptiveMolecularSystem
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import spock.lang.*

/**
 * Created by waller on 10/08/16.
 */
class SimpleAdaptiveMolecularSystemSpec  extends Specification{

    def " ensure Adaptive MS is working "() {

        when:
            def ms = Mock(MolecularSystem)
            def v = Mock(Vector)
            def forces = [v]
            def energy = 0.00001
            def adaptiveMS = new SimpleAdaptiveMolecularSystem(energy, forces, ms)
        then:
            adaptiveMS.energy == 0.00001
            adaptiveMS.forces == [v]
            adaptiveMS.molecularSystem == ms

    }
}
