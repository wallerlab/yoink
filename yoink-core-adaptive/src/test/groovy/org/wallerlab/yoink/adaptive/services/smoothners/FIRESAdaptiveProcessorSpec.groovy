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

import org.wallerlab.yoink.adaptive.services.FiresSmoothner
import org.wallerlab.yoink.api.model.molecule.Atom
import org.wallerlab.yoink.api.model.molecule.Coord
import spock.lang.Specification
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region
import org.wallerlab.yoink.api.service.plugin.QmMmWrapper
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;
import org.wallerlab.yoink.api.service.Calculator;
class FIRESAdaptiveProcessorSpec  extends Specification{
	def "test FIRESAdaptiveProcessor smooth" (){
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
		regions.put(Region.Name.MM,region)
		regions.put(Region.Name.QM_CORE,region)
		job.getRegions()>>regions



		def qmmmProcessor =Mock(QmMmWrapper)

		def list=Mock(List)
		list.get(_)>>vector
		qmmmProcessor.getForces()>>list

		def mmProcessor =Mock(QmMmWrapper)
		mmProcessor.getForces()>>list

		def distanceCalculator=Mock(Calculator)
		distanceCalculator.calculate(_,_)>>(double)1.0

		def properties=new HashMap<String,Object>()
		ArrayList<Double> smoothfactors = new ArrayList<Double>(
				Arrays.asList((double)0.1,(double)0.2));
		properties.put("smoothfactors",smoothfactors)
		job.getProperties()>>properties
		when:
		def bf=new FiresSmoothner();
		bf.myVector3D=myVector3D
		bf.qmmmProcessor=qmmmProcessor
		bf.distanceCalculator=distanceCalculator
		then:
		bf.smooth(job)
	}
}
