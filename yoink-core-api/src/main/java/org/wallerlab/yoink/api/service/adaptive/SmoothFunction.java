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

package org.wallerlab.yoink.api.service.adaptive;

/**
 * this interface is for the smooth function
 * 
 * @author Min Zheng
 *
 */
public interface SmoothFunction {
	/**
	 * 
	 * @param current
	 *            , the current value of variable
	 * @param min
	 *            , the minimum value of the variable
	 * @param max
	 *            , the maximum value of the variable
	 * @return smooth value, {@link java.lang.Double}
	 */
	double evaluate(double current, double min, double max);

}
