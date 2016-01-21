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
import spock.lang.Specification
import spock.lang.Specification
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import org.wallerlab.yoink.api.model.molecular.*;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;
import org.wallerlab.yoink.api.service.Calculator;
class PAPAdaptiveProcessorSpec  extends Specification{
	def "test PAPAdaptiveProcessor smooth" (){
		def job=Mock(Job)
		def regions=new HashMap<Region.Name,Region>()
		def myVector3D=new SimpleVector3DFactory( Vector3DType.COMMONS)

		def vector=myVector3D.create((double)0,(double)0,(double)0)
		def region=Mock(Region)
		def c=Mock(Coord)
		c.getCoords()>>vector
		def a=Mock(Atom)
		a.getCoordinate()>>c
		def m=Mock(Molecule)
		m.getAtoms()>>[a]
		region.getAtoms()>>[a]
		region.getCenterOfMass()>>Mock(Coord)


		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.BUFFER,region)
		
		job.getRegions()>>regions



		def qmmmProcessor =Mock(AdaptiveProcessor)

		def list=Mock(List)
		list.get(_)>>vector
		qmmmProcessor.getForces()>>list

		def mmProcessor =Mock(AdaptiveProcessor)
		mmProcessor.getForces()>>list

		def distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,_)>>(double)1.0

		def properties=new HashMap<String,Object>()
		ArrayList<Double> smoothfactors = new ArrayList<Double>(
				Arrays.asList((double)0.1,(double)0.2));
		properties.put("smoothfactors",smoothfactors)
		job.getProperties()>>properties
		when:
		def bf=new PAPAdaptiveProcessor();
		bf.myVector3D=myVector3D
		bf.qmmmProcessor=qmmmProcessor
		
		then:
		bf.smooth(job)
	}
}
