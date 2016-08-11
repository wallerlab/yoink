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
package org.wallerlab.yoink.region.service.partitioners

import org.wallerlab.yoink.api.model.Job
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.molecule.service.IDistanceCalculator

import static org.wallerlab.yoink.api.model.Job.JobParameter.*
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.BUFFER
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.QM_ADAPTIVE
import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;

import spock.lang.Specification;

class  NumberPartitionerSpec extends Specification{

	def "test method regionize(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters)"(){

		def job = Mock(Job)

		def regions=new HashMap<Region.Name,Region>()

		def region=Mock(Region)
		region.getCenterOfMass() >> Mock(Coord)

		def region2=Mock(Region)

		def m=Mock(MolecularSystem.Molecule)

		def m1 = createMolecule()
        def m2 = createMolecule()
        def m3 = createMolecule()
        def m4 = createMolecule()

        def molecules = [m1, m2, m3, m4] as Set
		region2.getMolecules() >>  molecules

		def ms = Mock(MolecularSystem)
		job.getMolecularSystem() >> ms
		ms.getMolecules() >>(Set<MolecularSystem.Molecule>)[m1, m2, m3, m4]
		ms.getMolecules("QM_CORE_FIXED") >> [m1]

		job.getParameter(NUMBER_QM) >> 2
		job.getParameter(NUMBER_BUFFER) >> 1
		job.getParameter(PARTITIONER) >> NUMBER

		when:"start up a new NumberRegionizer"
			def partitioner=new NumberPartitioner()
			partitioner.distanceCalculator= Mock(IDistanceCalculator)

		then:"the new numberRegionizer is executable and gets right results"
			def qmAdaptiveAndBuffer = partitioner.partition(job)
			qmAdaptiveAndBuffer.get(QM_ADAPTIVE).size()==2
			qmAdaptiveAndBuffer.get(BUFFER).size()==1
	}

    private MolecularSystem.Molecule createMolecule() {
        def atom = Mock(MolecularSystem.Molecule.Atom)
        def molecule = Mock(MolecularSystem.Molecule)
        molecule.getAtoms() >> [atom]
        molecule
    }

}
