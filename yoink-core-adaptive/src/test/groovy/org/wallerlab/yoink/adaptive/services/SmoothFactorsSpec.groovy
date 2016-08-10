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

import org.wallerlab.yoink.adaptive.services.SmoothFactors
import org.wallerlab.yoink.api.model.molecular.MolecularSystem

import org.wallerlab.yoink.api.service.adaptive.Adaptive
import org.wallerlab.yoink.api.service.density.DensityCalculator
import spock.lang.Ignore
import spock.lang.Specification
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.api.model.Job


@Ignore
class SmoothFactorsSpec extends Specification{

	def distanceCalculator
	def densityCalculator
	def job
	def smoothFunction

	def setup(){

		smoothFunction=Mock(SmoothFunction)
		smoothFunction.evaluate(_,_,_)>>(double)1.0

		job=Mock(Job)

		def a=Mock(MolecularSystem.Molecule.Atom)
		def m1=Mock(MolecularSystem.Molecule)
		m1.getAtoms()>>[a]
		def m2=Mock(MolecularSystem.Molecule)
		m2.getAtoms()>>[a]

		def  region=Mock(Region)
		region.getMolecules()>>[m1, m2]
		def regions=Mock(Map)
		regions.get(_)>>region
		job.getRegions()>>regions
		job.getRegions().get(Region.Name.BUFFER).getMolecules() >> [m1,m2]

		def parameters= [:]
		parameters.put(SMOOTH_FUNCTION,SmoothFunction.Name.BROOKS)
		parameters.put(SMOOTHNER, Adaptive.Type.DISTANCE)

		job.getParameters()>>parameters
		parameters.put(DISTANCE_QM,4.0d)
		parameters.put(DISTANCE_BUFFER,5.0d)
		parameters.put(DENSITY_BUFFER,1.0d)
		parameters.put(DENSITY_QM,1.0d)

		distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,_)>>(double)1.0

		densityCalculator = Mock(DensityCalculator)

		def propertites= [:]
		job.getProperties()>>propertites
	}


	def "test distance smoothner"(){
		when:"set up a new  Adaptive"
		def smoothner = new SmoothFactors()
		smoothner.distanceCalculator = distanceCalculator

		then:"call method compute, assert the return value of method loopOverAllBufferMolecules() "
		smoothner.distance.compute(job,smoothner)
		//smoothner.loopOverAllBufferMolecules(job)==[1.0, 1.0]
		//smoothner.loopOverAllBufferMolecules(job).size()==2
	}


	def "test density smoothner"() {

		when: "set up a new  DensitySmoothnerImpl"
		def smoothner = new SmoothFactors()
		smoothner.centerOfMassComputer = centerOfMassComputer
		smoothner.densityCalculator = densityCalculator

		then: "call method compute, assert the return value of method loopOverAllBufferMolecules() "
		smoothner.smooth(job)
		//smoothner.loopOverAllBufferMolecules(job)==[1.0, 1.0]
		//smoothner.loopOverAllBufferMolecules(job).size()==2
	}

}