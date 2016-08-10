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

import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.model.adaptive.Region
import org.wallerlab.yoink.api.model.Job
import org.wallerlab.yoink.api.service.molecule.Calculator
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class WeightFactorsSpec extends Specification {

	def job
	def properties
	def distanceCalculator

	def setup(){
		job = Mock(Job)

		def m1=Mock(MolecularSystem.Molecule)
		def m2=Mock(MolecularSystem.Molecule)

		m1.getIndex()>>(int)1
		m2.getIndex()>>(int)2

		def region=Mock(Region)
		region.getMolecules()>>[m1, m2]
		def regions=Mock(Map)
		regions.get(Region.Name.BUFFER)>>region
		job.getRegions()>>regions

		properties= [:]

		def smoothfactors = [0.1d,0.2d]
		properties.put("smoothfactors",smoothfactors)
		job.getProperties()>>properties

		distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,m1)>>(double)0.1
		distanceCalculator.calculate(_,m2)>>(double)0.2
	}

	def " DAS weights"(){
		when:"make DAS weights"
		def weightFactors= new WeightFactors().dasWeights

		then:"call method compute, assert the size of calculated value"
		weightFactors.compute(job)
		//properties.get("weightfactors").size()==3
	}


	def " FIRES weights"(){
		when:"make a weightFactors"
		def smoothner=new SmoothFactors()
		smoothner.distanceCalculator=distanceCalculator
		then:"call method compute, assert the size fo calculated value"
		smoothner.compute(job)
		properties.get("smoothfactors").size()==1
	}

	def " SAP weights"() {

		when: "make a weights"
		def weightFactors = new WeightFactors().sapWeights
		weightFactors.distanceCalculator = closestDistanceToMoleculeCalculator
		then: "call method compute, assert the size fo calculated value"
		weightFactors.compute(job)
		properties.get("weightfactors").size() == 3

	}

	def " XS weights"() {
		when: "make a weights"
		def weightFactors = new WeightFactors().xsWeights

		then: "call method compute, assert the size fo calculated value"
		weightFactors.compute(job)
		properties.get("weightfactors").size() == 2
	}
}
