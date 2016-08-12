package org.wallerlab.yoink.cluster.domain

import spock.lang.Specification

/**
 * Created by waller on 12/08/16.
 */
class MolecularSystemSpec extends Specification{

    def "test neo4j ms"() {

        when:
          def ms = new MolecularSystem("test1", 20,10)

        then:
           ms.nameOfSystem == "test1"
           ms.numberOfAtoms ==10
           ms.numberOfMolecules == 20


    }
}
