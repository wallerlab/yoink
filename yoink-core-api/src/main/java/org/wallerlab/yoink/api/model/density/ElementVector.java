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
package org.wallerlab.yoink.api.model.density;

import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this interface is to get the element density parameters in the form of vector
 * 
 * @author Min Zheng
 *
 */
public interface ElementVector {

	/**
	 * get the value of inverse of zVector. zVector contains z1,z2,z3 of an
	 * element {@link org.wallerlab.yoink.api.model.molecular.Element}.
	 * 
	 * @return inverse of zVector -
	 *         {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector inverseZVector();

	/**
	 * get value of the cVector. cVector contains c1,c2,c3 of an element
	 * {@link org.wallerlab.yoink.api.model.molecular.Element}.
	 * 
	 * @return cVector - {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector cVector();

	/**
	 * set the value of element.
	 * 
	 * @param element
	 *            -{@link org.wallerlab.yoink.api.model.molecular.Element}
	 */
	void setElementType(Element element);

	/**
	 * get the value of element.
	 * 
	 * @return element -{@link org.wallerlab.yoink.api.model.molecular.Element}
	 */
	Element getElementType();
}
