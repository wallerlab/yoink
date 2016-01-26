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
package org.wallerlab.yoink.api.model.molecular;

import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this interface for domain model Coord
 * 
 * @author Min Zheng
 *
 */
public interface Coord {

	/**
	 * get the value of coordinate
	 * 
	 * @return {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector getCoords();

	/**
	 * set the value of coordinate
	 * 
	 * @param coords
	 *            {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	void setCoords(Vector coords);

}
