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
 
package org.wallerlab.yoink.regionizer.service

import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegionFactory

class ParameterRegionizerSpec extends Specification{

	def "test method regionize(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters)"(){

		def regions=new HashMap<Region.Name,Region>()
		def region=Mock(Region)
		def m=Mock(Molecule)
		region.getCenterOfMass()>>Mock(Coord)
		region.getMolecules()>>[m]
		def molecularMap=new HashMap<Molecule, Integer>()
		molecularMap.put(m,0)
		region.getMolecularMap()>>molecularMap
		regions.put(Region.Name.QM_CORE,region)
		regions.put(Region.Name.NONQM_CORE,region)
		def parameters=Mock(Map)
		def simpleRegionFactory=new SimpleRegionFactory()

		when:"set up a new ParameterRegionizer"
		def regionizer=new ParameterRegionizer()
		regionizer.simpleRegionFactory=simpleRegionFactory

		then:"the new parameterRegionizer is executable"
		regionizer.regionize(regions,parameters)
		regions.size()==5
	}
}
