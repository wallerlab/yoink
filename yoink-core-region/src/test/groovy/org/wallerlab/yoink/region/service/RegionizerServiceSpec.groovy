/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wallerlab.yoink.region.service

import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.region.Regionizer
import org.wallerlab.yoink.math.SetOps
import org.wallerlab.yoink.region.service.partitioners.Partitioner
import spock.lang.Specification

class RegionizerServiceSpec extends Specification{


    def " test the service is working properly with SetOps"(){

        when: " we create the service"
            def partitioners = [:]
            def partitioner = Mock(Partitioner)
            partitioners.put(Regionizer.Type.DISTANCE,partitioner)

            def regionizerService = new  SimpleRegionizer( partitioners)

            def m1 = Mock(MolecularSystem.Molecule)
            def m2 = Mock(MolecularSystem.Molecule)
            def m3 = Mock(MolecularSystem.Molecule)

            def qm = [m1] as Set
            def mm = [m1,m2,m3] as Set

        then: "we can regionize properly for simple mocked systems"
            def non_qm = SetOps.diff(mm,qm)
            non_qm.size()== 2
    }

    // TODO int test we need to assert that there are 3 beans made.

}
