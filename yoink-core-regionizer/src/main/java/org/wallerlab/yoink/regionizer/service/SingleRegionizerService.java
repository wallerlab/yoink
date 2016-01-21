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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.api.service.regionizer.RegionizerMath;
import org.wallerlab.yoink.math.map.MapDifference;
import org.wallerlab.yoink.regionizer.domain.SimpleRegion;

import static org.wallerlab.yoink.api.model.regionizer.Region.Name.*;

/**
 * this class is to get molecules in one region
 * 
 * @author Min Zheng
 *
 */
@Service
public class SingleRegionizerService implements RegionizerMath<Region, Region.Name> {

	@Resource
	private Factory<Region, Region.Name> simpleRegionFactory;

	/**
	 * get the region content for a given region name
	 * 
	 * @param regions
	 *            - a Map, Region.Name
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region.Name }
	 *            as key, Region
	 *            {@link org.wallerlab.yoink.api.model.regionizer.Region} as
	 *            value
	 * @param name
	 *            - {@link org.wallerlab.yoink.api.model.regionizer.Region.Name }
	 * @return region - {@link org.wallerlab.yoink.api.model.regionizer.Region}
	 */
	public Region regionize(Map<Region.Name, Region> regions, Region.Name name) {
		Region region = simpleRegionFactory.create();
		switch (name) {
		case QM_CORE:
			region.addAll(regions.get(QM_CORE_FIXED).getMolecularMap());
			break;
		case QM:
			region.addAll(regions.get(QM_CORE_FIXED).getMolecularMap());
			break;
		case QM_CORE_ADAPTIVE:
			region = difference(regions.get(QM_CORE),
					regions.get(QM_CORE_FIXED));
			region.changeMolecularId(QM_CORE_ADAPTIVE);
			break;
		case QM_ADAPTIVE:
			region = difference(regions.get(QM), regions.get(QM_CORE));
			region.changeMolecularId(QM_ADAPTIVE);
			break;
		case MM:
			region = difference(regions.get(SYSTEM), regions.get(QM));
			break;
		case MM_NONBUFFER:
			region = difference(regions.get(MM), regions.get(BUFFER));
			region.changeMolecularId(MM);
			break;
		case NONQM_CORE:
			region = difference(regions.get(SYSTEM), regions.get(QM_CORE));
			break;
		case NONQM_CORE_ADAPTIVE_SEARCH:
			region = difference(regions.get(ADAPTIVE_SEARCH),
					regions.get(QM_CORE));
			break;
		case BUFFER:
			region = difference(regions.get(ADAPTIVE_SEARCH), regions.get(QM));
			break;
		default:
			throw new IllegalArgumentException("invalid region name");
		}
		region.setName(name);
		return region;
	}

	/*
	 * This method is to calculate the difference of two Regions
	 */
	private Region difference(Region regionA, Region regionB) {
		Map<Molecule, Integer> mapA = new HashMap<Molecule, Integer>();
		if (regionA != null) {
			mapA = regionA.getMolecularMap();
		}

		Map<Molecule, Integer> mapB = new HashMap<Molecule, Integer>();
		if (regionB != null) {
			mapB = regionB.getMolecularMap();
		}
		Map<Molecule, Integer> twoMapsDifference = MapDifference.diff(mapA,
				mapB);
		Region regionDifference = simpleRegionFactory.create();
		regionDifference.setMolecularMap(twoMapsDifference);
		return regionDifference;
	}

}
