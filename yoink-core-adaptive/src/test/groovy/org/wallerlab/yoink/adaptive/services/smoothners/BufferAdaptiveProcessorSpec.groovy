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
package org.wallerlab.yoink.adaptive.services.smoothners

import org.wallerlab.yoink.adaptive.services.BufferSmoothner
import spock.lang.Specification
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.molecule.Coord
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region
import org.wallerlab.yoink.api.service.plugin.QmMmWrapper

class BufferAdaptiveProcessorSpec  extends Specification{

	def "test BufferAdaptiveProcessor smooth" (){
		def job=Mock(Job)
		def regions=new HashMap<Region.Name,Region>()
		
		def region=Mock(Region)
		def m=Mock(Molecule)
		region.getCenterOfMass()>>Mock(Coord)


		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.QM,region)

		job.getRegions()>>regions
		
		def properties=new HashMap<String,Object>()
		job.getProperties()>>properties
		
		def qmmmProcessor =Mock(QmMmWrapper)
		qmmmProcessor.getForces()>>Mock(List)
		
		def mmProcessor =Mock(QmMmWrapper)
		mmProcessor.getForces()>>Mock(List)
		
		when:
		def bf=new BufferSmoothner();
		bf.mmProcessor=mmProcessor
		bf.qmmmProcessor=qmmmProcessor
		then:
		bf.smooth(job)
	}
	
}
