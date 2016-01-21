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
package org.wallerlab.yoink.api.service.molecular;

/**
 * this interface contains enum UnitConverterType
 * 
 * @author Min Zheng
 *
 */
public interface Converter {

	/**
	 * unit convert coefficient for angstrom to bohr and bohr to angstrom
	 * 
	 * @author Min Zheng
	 *
	 */
	public enum UnitConverterType {

		AngstromToBohr(1.8897259885789), BohrToAngstrom(0.529177249);

		private final double constant;

		UnitConverterType(double constant) {
			this.constant = constant;
		}

		public double value() {
			return constant;
		}
	}

}
