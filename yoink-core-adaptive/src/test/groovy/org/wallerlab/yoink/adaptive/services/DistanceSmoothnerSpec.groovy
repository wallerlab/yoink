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
package org.wallerlab.yoink.adaptive.services

import org.wallerlab.yoink.api.model.molecule.Atom
import org.wallerlab.yoink.api.model.molecule.Molecule
import org.wallerlab.yoink.api.service.adaptive.Smoothner
import spock.lang.Specification
import org.wallerlab.yoink.api.model.molecule.Coord
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.molecule.service.Computer;
import org.wallerlab.yoink.adaptive.services.functions.SmoothFunction;
import org.wallerlab.yoink.api.model.batch.Job

import static org.wallerlab.yoink.api.model.batch.JobParameter.*

class DistanceSmoothnerSpec extends Specification{

	def "test distance smoothner"(){
		def smoothFunction=Mock(SmoothFunction)
		smoothFunction.evaluate(_,_,_)>>(double)1.0

		def job=Mock(Job)

		def a=Mock(Atom)
		def m1=Mock(Molecule)
		m1.getAtoms()>>[a]
		def m2=Mock(Molecule)
		m2.getAtoms()>>[a]

		def  region=Mock(Region)
		region.getMolecules()>>[m1, m2]
		def regions=Mock(Map)
		regions.get(_)>>region
		job.getRegions()>>regions
		job.getRegions().get(Region.Name.BUFFER).getMolecules() >> [m1,m2]


		def parameters= [:]
		parameters.put(SMOOTH_FUNCTION,SmoothFunction.Name.BROOKS)
		parameters.put(SMOOTHNER, Smoothner.Type.DISTANCE)
		job.getParameters()>>parameters
		parameters.put(DISTANCE_QM,4.0d)
		parameters.put(DISTANCE_BUFFER,5.0d)

		def centerOfMassComputer=Mock(Computer)
		centerOfMassComputer.calculate(_)>>Mock(Coord)

		def distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,_)>>(double)1.0

		def propertites= [:]
		job.getProperties()>>propertites

		when:"set up a new  Smoothner"
		def smoothner=new SmoothFactors()
		smoothner.centerOfMassComputer=centerOfMassComputer
		smoothner.shortestDistance=distanceCalculator

		then:"call method smooth, assert the return value of method loopOverAllBufferMolecules() "
		smoothner.smooth(job)
		//smoothner.loopOverAllBufferMolecules(job)==[1.0, 1.0]
		//smoothner.loopOverAllBufferMolecules(job).size()==2
	}
}
