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
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.molecule.service.IDistanceCalculator
import spock.lang.Ignore
import spock.lang.Specification;


import org.wallerlab.yoink.api.model.Coord;

import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator

import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;

@Ignore
class DistancePartitionerSpec extends Specification{

	//This class really has only a few features.
	//A distance calculator is autowired in, mock that.
	//Parameters are take from the job. Mock that.
	// A Molecular system. Mock that.
	def "test method regionize(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters)"(){

		def job = Mock(Job)
		def regions = [:]
		def region=Mock(Region)

		def molecule=Mock(MolecularSystem.Molecule)
		region.getCenterOfMass()>>Mock(Coord)
		def region2=Mock(Region)

		def a1=Mock(MolecularSystem.Molecule.Atom)
		def m1=Mock(MolecularSystem.Molecule)
		m1.getAtoms()>>[a1]

		def a2=Mock(MolecularSystem.Molecule.Atom)
		def m2=Mock(MolecularSystem.Molecule)

		m2.getAtoms()>>[a2]
		def a3=Mock(MolecularSystem.Molecule.Atom)
		def m3=Mock(MolecularSystem.Molecule)
		m3.getAtoms()>>[a3]

		region2.getMolecules()>>[m1, m2, m3]

		def distanceCalculator=Mock(IDistanceCalculator)
		distanceCalculator.calculate(_,m1)>> 1.0d
		distanceCalculator.calculate(_,m2)>> 2.0d
		distanceCalculator.calculate(_,m3)>> 3.0d

		job.getParameter(DISTANCE_QM)>> 2.5d
		job.getParameter(DISTANCE_BUFFER)>> 1.0d
		job.getParameter(PARTITIONER)>> DISTANCE


		when:"set up a new DistanceRegionizer"
		def regionizer = new DistancePartitioner()
		regionizer.distanceCalculator = distanceCalculator

		then:"the new distanceRegionizer is executable and gets right results"
		def results = regionizer.partition(job)
		results.get(QM_ADAPTIVE).size()==2
		results.get(BUFFER).size()==1
	}
}
