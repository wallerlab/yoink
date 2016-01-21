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
package org.wallerlab.yoink.regionizer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.api.service.regionizer.RegionizerMath;
import org.wallerlab.yoink.regionizer.domain.SimpleRegion;

import static org.wallerlab.yoink.api.model.regionizer.Region.Name.*;
/**
 * this class is to get molecules for those regions in the List regionNames.
 * 
 * @author Min Zheng
 *
 */
@Service
public class RegionizerService implements
		RegionizerMath<Map<Region.Name, Region>, MolecularSystem> {

	@Resource
	private RegionizerMath<Region, Region.Name> singleRegionizerService;

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	private List<Region.Name> regionNames = new ArrayList<Region.Name>();

	/**
	 * choose the corresponding region for every element in regionNames
	 * 
	 * @param regions
	 *            - a Map, Region.Name
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name }
	 *            as key, Region
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} as
	 *            value
	 * @param molecularSystem
	 *            {@link org.wallerlab.yoink.api.model.molecular.MolecularSystem}
	 * @return regions - a Map, Region.Name
	 *         {@link org.wallerlab.yoink.api.model.regionizer.Region.Name } as
	 *         key, Region
	 *         {@link org.wallerlab.yoink.api.model.regionizer.Region} as value
	 */
	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
			MolecularSystem molecularSystem) {
		for (Region.Name name : regionNames) {
			Region region = simpleRegionFactory.create();
			switch (name) {
			case SYSTEM:
				region = systemRegionize(name, molecularSystem);
				break;
			case QM_CORE_FIXED:
				region = qmCoreFixedRegionize(name, molecularSystem);
				break;
			case QM_CORE:
				region = singleRegionizerService.regionize(regions, QM_CORE);
				break;
			case QM:
				region = singleRegionizerService.regionize(regions, QM);
				break;
			case QM_CORE_ADAPTIVE:
				region = singleRegionizerService.regionize(regions,
						QM_CORE_ADAPTIVE);
				break;
			case QM_ADAPTIVE:
				region = singleRegionizerService
						.regionize(regions, QM_ADAPTIVE);
				break;
			case MM:
				region = singleRegionizerService.regionize(regions, MM);
				break;
			case MM_NONBUFFER:
				region = singleRegionizerService.regionize(regions,
						MM_NONBUFFER);
				break;
			case NONQM_CORE:
				region = singleRegionizerService.regionize(regions, NONQM_CORE);
				break;
			case NONQM_CORE_ADAPTIVE_SEARCH:
				region = singleRegionizerService.regionize(regions,
						NONQM_CORE_ADAPTIVE_SEARCH);
				break;
			default:
				throw new IllegalArgumentException("invalid region name");
			}
			regions.put(name, region);
		}
		return regions;
	}

	/**
	 * find the Qm_Core_Fixed molecules in molecular system
	 */
	private Region qmCoreFixedRegionize(Region.Name name,
			MolecularSystem molecularSystem) {
		Region region = simpleRegionFactory.create(name);
		List<Molecule> molecules = molecularSystem.getMolecules();
		for (int i = 0; i < molecules.size(); i++) {
			if (name == molecules.get(i).getName()) {
				region.addMolecule(molecules.get(i), i + 1);
			}
		}
		return region;
	}

	/**
	 * put all molecules in molecular system in SYSTEM region
	 */
	private Region systemRegionize(Region.Name name,
			MolecularSystem molecularSystem) {
		Region region = simpleRegionFactory.create(name);
		List<Molecule> molecules = molecularSystem.getMolecules();
		for (int i = 0; i < molecules.size(); i++) {
			region.addMolecule(molecules.get(i), i + 1);
		}
		return region;
	}

	/**
	 * get the region names
	 * 
	 * @return a list of region names
	 *         {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 */
	public List<Region.Name> getRegionNames() {
		return regionNames;
	}

	/**
	 * set the region names
	 * 
	 * @param regionNames
	 *            , a list of region names
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}
	 */
	public void setRegionNames(List<Region.Name> regionNames) {
		this.regionNames = regionNames;
	}
}
