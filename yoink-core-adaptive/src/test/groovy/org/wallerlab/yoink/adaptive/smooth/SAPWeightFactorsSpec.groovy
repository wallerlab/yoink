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

import org.wallerlab.yoink.api.service.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.molecular.Molecule
import org.wallerlab.yoink.api.model.regionizer.Region
import org.wallerlab.yoink.api.model.bootstrap.Job
import spock.lang.Specification
import org.wallerlab.yoink.api.model.molecular.Coord;
class SAPWeightFactorsSpec extends Specification {

	def "test method execute(YoinkJob<JAXBElement> job)"(){

		given:"a Job"
		def job=Mock(Job)
		def m1=Mock(Molecule)
		def m2=Mock(Molecule)
		def map=new HashMap<Molecule,Integer>();
		map.put(m1, (int)1);
		map.put(m2, (int)2);
		
		def region=Mock(Region)
		region.getMolecules()>>[m1, m2]
		region.getMolecularMap()>>map
		
		def qmcore=Mock(Region)
		qmcore.getCenterOfMass()>>Mock(Coord)
		
		def regions=Mock(Map)
		
		regions.get(Region.Name.BUFFER)>>region
		regions.get(Region.Name.QM_CORE)>>qmcore
		
		job.getRegions()>>regions
		
		def properties=new HashMap<String,Object>()
		ArrayList<Double> smoothfactors = new ArrayList<Double>(
				Arrays.asList((double)0.2,(double)0.1));
		properties.put("smoothfactors",smoothfactors)
		job.getProperties()>>properties
		
		def closestDistanceToMoleculeCalculator=Mock(Calculator)
		closestDistanceToMoleculeCalculator.calculate(_,m1)>>(double)0.1
		closestDistanceToMoleculeCalculator.calculate(_,m2)>>(double)0.2

		when:"make a weightFactors"
		def weightFactors=new SAPWeightFactors()
		weightFactors.closestDistanceToMoleculeCalculator=closestDistanceToMoleculeCalculator
		then:"call method smooth, assert the size fo calculated value"
		weightFactors.smooth(job)
		properties.get("weightfactors").size()==3
		
	}
}
