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
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegionFactory

class SingleRegionizerServiceSpec extends Specification{

	def" test method regionize(Map<Region.Name, Region> regions,Region.Name name)"(){
		Region.Name name;
		def regionizerService=new SingleRegionizerService()
		def region=Mock(Region)
		def mMap=new HashMap<Molecule,Integer>()
		def m=Mock(Molecule)
		mMap.put(m, 0)
		region.getMolecularMap()>>mMap
		def regions=Mock(Map)
		regions.get(Region.Name.QM_CORE_FIXED)>>region
		def simpleRegionFactory=new SimpleRegionFactory()
		regionizerService.simpleRegionFactory=simpleRegionFactory

		when:"region name is QM_CORE"
		name=Region.Name.QM_CORE
		then:"QM_CORE region is a copy of QM_CORE_FIXED "
		regionizerService.regionize(regions, name).getSize()==1

		when:"region name is QM_CORE_ADAPTIVE"
		name=Region.Name.QM_CORE_ADAPTIVE
		def region2=Mock(Region)
		def mMap2=new HashMap<Molecule,Integer>()
		def m1=Mock(Molecule)
		mMap2.put(m, 0)
		mMap2.put(m1, 0)
		region2.getMolecularMap()>>mMap2
		regions.get(Region.Name.QM_CORE)>>region2
		then:"QM_CORE_ADAPTIVE region is the difference between QM_CORE and QM_CORE_FIXED"
		regionizerService.regionize(regions, name).getSize()==1

		
	}
}
