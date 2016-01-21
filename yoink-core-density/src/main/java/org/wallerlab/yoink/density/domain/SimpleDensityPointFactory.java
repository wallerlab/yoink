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
package org.wallerlab.yoink.density.domain;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.service.Factory;

@Service
public class SimpleDensityPointFactory implements Factory<DensityPoint, Coord> {

	@Override
	public DensityPoint create() {
		return new SimpleDensityPoint();
	}

	@Override
	public DensityPoint create(Coord currentCoord) {
		return new SimpleDensityPoint(currentCoord);
	}

}
