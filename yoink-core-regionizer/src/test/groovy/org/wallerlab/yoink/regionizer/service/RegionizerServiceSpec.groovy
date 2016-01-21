
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
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.regionizer.*;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.regionizer.domain.SimpleRegionFactory

class RegionizerServiceSpec extends Specification{
	def "test method regionize(Map<Region.Name, Region> regions,MolecularSystem molecularSystem)"(){


		Region.Name SYSTEM = Region.Name.SYSTEM;
		Region.Name MM = Region.Name.MM;
		Region.Name QM_CORE_ADAPTIVE = Region.Name.QM_CORE_ADAPTIVE;
		Region.Name QM = Region.Name.QM;
		Region.Name ADAPTIVE_SEARCH = Region.Name.ADAPTIVE_SEARCH;
		Region.Name NONQM_CORE = Region.Name.NONQM_CORE;
		Region.Name NONQM_CORE_ADAPTIVE_SEARCH = Region.Name.NONQM_CORE_ADAPTIVE_SEARCH;
		Region.Name QM_CORE_FIXED = Region.Name.QM_CORE_FIXED;
		Region.Name QM_CORE = Region.Name.QM_CORE;
		Region.Name QM_ADAPTIVE = Region.Name.QM_ADAPTIVE;
		List<Region.Name> regionNames =[
			SYSTEM,
			MM,
			QM_CORE_ADAPTIVE,
			NONQM_CORE,
			NONQM_CORE_ADAPTIVE_SEARCH,
			QM_CORE_FIXED,
			QM_CORE,
			QM_ADAPTIVE
		]
		def region=Mock(Region)
		def m=Mock(Molecule)


		def molecularSystem=Mock(MolecularSystem)
		molecularSystem.getMolecules()>>[m]
		def singleRegionizerService=Mock(RegionizerMath)
		singleRegionizerService.regionize(_, _)>>Mock(Region)

		def regions=new HashMap<Region.Name, Region>()
		def simpleRegionFactory=new SimpleRegionFactory()

		def service = new RegionizerService()

		when:"set up a new RegionizerService "

		service.setRegionNames(regionNames)
		service.simpleRegionFactory=simpleRegionFactory
		service.singleRegionizerService=singleRegionizerService

		then:"method regionize is executable, no error thrown"
		service.regionize(regions,molecularSystem)

		when:"regionName contains elements not in the switch of source code"
		regionNames =[QM, ADAPTIVE_SEARCH]
		service.setRegionNames(regionNames)
		service.regionize(regions,molecularSystem)
		then:"throw exception"
		Exception ex= thrown()
		ex.message=="invalid region name"
	}
}
