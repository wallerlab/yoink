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
package org.wallerlab.yoink.regionizer.domain;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.model.regionizer.Region.Name;
import org.wallerlab.yoink.api.service.Computer;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

/**
 * factory to generate new SimpleRegion instance
 * 
 * @author Min Zheng
 *
 */
@Service
public class SimpleRegionFactory implements Factory<Region, Region.Name> {

	@Resource
	private Computer<Coord, Set<Molecule>> centerOfMassComputer;

	@Override
	public Region create() {
		Region region = new SimpleRegion();
		region.setCenterOfMassComputer(centerOfMassComputer);
		return region;

	}

	@Override
	public Region create(Name name) {
		Region region = new SimpleRegion(name);
		region.setCenterOfMassComputer(centerOfMassComputer);
		return region;
	}

}
