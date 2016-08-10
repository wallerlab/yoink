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
package org.wallerlab.yoink.adaptive.domain

import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import spock.lang.Specification

class ConfigurationSpec extends Specification{

    def "assert domain model behaves itself"(){

        when: " we construct a configuration"
          def molecule = Mock(MolecularSystem.Molecule)
          def molecules = [molecule, molecule] as Set
          def configuration = new Configuration(0.01,0.2,molecules)

        then: " its properties stay fixed, and are available."
           configuration.bufferWeight
           configuration.qmWeight
           configuration.getBufferMolecules().size() == 1
    }

}
