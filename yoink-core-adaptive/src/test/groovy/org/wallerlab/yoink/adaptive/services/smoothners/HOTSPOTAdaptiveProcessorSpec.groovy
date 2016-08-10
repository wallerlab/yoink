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

import org.wallerlab.yoink.adaptive.services.HOTSPOTSmoothner
import org.wallerlab.yoink.api.model.molecule.Coord
import spock.lang.Specification
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region
import org.wallerlab.yoink.api.service.plugin.QmMmWrapper
import org.wallerlab.yoink.api.service.math.Vector.Vector3DType;
class HOTSPOTAdaptiveProcessorSpec  extends Specification{

	def "test HOTSPOTAdaptiveProcessor smooth" (){
		def job=Mock(Job)
		def regions=new HashMap<Region.Name,Region>()

		def region=Mock(Region)
		def m=Mock(Molecule)
		region.getCenterOfMass()>>Mock(Coord)


		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.QM,region)
		regions.put(Region.Name.BUFFER,region)
		job.getRegions()>>regions

	

		def qmmmProcessor =Mock(QmMmWrapper)
		def vector=new SimpleVector3DFactory( Vector3DType.COMMONS).create((double)0,(double)0,(double)0)
		def list=Mock(List)
		list.get(_)>>vector
		qmmmProcessor.getForces()>>list

		def mmProcessor =Mock(QmMmWrapper)
		mmProcessor.getForces()>>list

		def properties=new HashMap<String,Object>()
		ArrayList<Double> smoothfactors = new ArrayList<Double>(
				Arrays.asList((double)0.1,(double)0.2));
		properties.put("smoothfactors",smoothfactors)
		job.getProperties()>>properties
		when:
		def bf=new HotspotSmoothner();
		bf.mmProcessor=mmProcessor
		bf.qmmmProcessor=qmmmProcessor
		then:
		bf.smooth(job)
	}
}
