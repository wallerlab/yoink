package org.wallerlab.yoink.region.service

import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.region.Regionizer
import org.wallerlab.yoink.math.SetOps
import org.wallerlab.yoink.region.service.partitioners.Partitioner
import spock.lang.Specification

/**
 * Created by waller on 16/07/16.
 */
class RegionizerServiceSpec extends Specification{


    def " test the service has all the beans needed"(){

        when:
        def partitioners = [:]
        def partitioner = Mock(Partitioner)
        partitioners.put(Regionizer.Type.DISTANCE,partitioner)

        def regionizerService = new  SimpleRegionizer( partitioners)


        def m1 = Mock(MolecularSystem.Molecule)
        def m2 = Mock(MolecularSystem.Molecule)
        def m3 = Mock(MolecularSystem.Molecule)

        def qm = [m1] as Set
        def mm = [m1,m3] as Set


        then:
        def non_qm = SetOps.diff(mm,qm)
        println non_qm
        non_qm.size()== 1
    }


}
