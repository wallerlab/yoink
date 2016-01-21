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
package org.wallerlab.yoink.processor.service

import spock.lang.Specification
import spock.lang.Ignore;
import spock.lang.Specification;
import org.wallerlab.yoink.api.model.bootstrap.*;
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegion

import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;

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
		
		def qmmmProcessor =Mock(AdaptiveProcessor)
		qmmmProcessor.getForces()>>Mock(List)
		
		def mmProcessor =Mock(AdaptiveProcessor)
		mmProcessor.getForces()>>Mock(List)
		
		when:
		def bf=new BufferAdaptiveProcessor();
		bf.mmProcessor=mmProcessor
		bf.qmmmProcessor=qmmmProcessor
		then:
		bf.smooth(job)
	}
	
}
