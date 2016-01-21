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
package org.wallerlab.yoink.adaptive.smooth

import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.molecular.*
import org.wallerlab.yoink.api.model.regionizer.Region
import org.wallerlab.yoink.api.model.bootstrap.Job
import spock.lang.Specification
import org.wallerlab.yoink.api.service.Calculator;
class FiresWeightFactorsSpec extends Specification {

	def "test method execute(YoinkJob<JAXBElement> job)"(){

		given:"a Job"
		def job=Mock(Job)
		def m1=Mock(Molecule)
		
		m1.getIndex()>>(int)1
		def a1=Mock(Atom)
		def region=Mock(Region)
		region.getMolecules()>>[m1]
		region.getAtoms()>>[a1]
		region.getCenterOfMass()>>Mock(Coord)
		def regions=Mock(Map)
		regions.get(Region.Name.QM)>>region
		regions.get(Region.Name.QM_CORE)>>region
		job.getRegions()>>regions
		
		def distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,_)>>(double)1.0
		
		def properties=new HashMap<String,Object>()
		
		job.getProperties()>>properties

		when:"make a weightFactors"
		def smoothner=new FIRESmoothner()
		smoothner.distanceCalculator=distanceCalculator
		then:"call method smooth, assert the size fo calculated value"
		smoothner.smooth(job)
		properties.get("smoothfactors").size()==1
	}
}
