package org.wallerlab.yoink.cluster.domain

import org.wallerlab.yoink.api.model.molecular.MolecularSystem.Molecule
import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class InteractionSpec extends Specification{

    def "simple domain spec"() {
        when:
        def m1 =  Mock(Molecule)
        def m2 =  Mock(Molecule)
        def molecules = [m1,m2] as Set
        def weight = 0.01
        def degree = 4
        def interaction= new Interaction(molecules,weight,degree)

        then:
        interaction.degree     ==  4
        interaction.weight     ==  0.01
        interaction.molecules  ==  molecules

    }

}
