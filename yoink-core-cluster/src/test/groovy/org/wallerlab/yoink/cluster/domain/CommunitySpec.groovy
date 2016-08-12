package org.wallerlab.yoink.cluster.domain

import org.junit.Test
import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class CommunitySpec extends Specification{

    def "test community"() {

        when:
        def m1 =  Mock(org.wallerlab.yoink.api.model.molecular.MolecularSystem.Molecule)
        def m2 =  Mock(org.wallerlab.yoink.api.model.molecular.MolecularSystem.Molecule)
        def molecules = [m1,m2] as Set
        def weight = 0.01
        def degree = 4
        def i1=  new Interaction(molecules,weight,degree)
        def i2=  new Interaction(molecules,weight,degree)
        def interactions = [i1,i2] as Set
        def community = new Community(interactions,10.0, 5.0)
        then:
        community.sumOfInternalEdgeWeights ==  5.0
        community.totalSumOfEdgeWeights    == 10.0
        community.getMolecules()    ==  [m2,m1] as Set
        community.toString() instanceof String
        community.size() == 2

    }
}
