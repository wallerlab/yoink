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
package org.wallerlab.yoink.density.service.density;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.wallerlab.yoink.api.model.density.ElementVector;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

@Component
@Scope("prototype")
public class ElementVectorImpl implements ElementVector {

	@Resource
	private SimpleVector3DFactory myVector3D;

	private Element elementType;

	public Vector inverseZVector() {

		Vector inverseZVector = myVector3D.create(
				Math.pow(elementType.z1(), -1), Math.pow(elementType.z2(), -1),
				Math.pow(elementType.z3(), -1));
		return inverseZVector;
	}

	public Vector cVector() {
		Vector cVector = myVector3D.create(elementType.c1(), elementType.c2(),
				elementType.c3());
		return cVector;
	}

	public Element getElementType() {
		return elementType;
	}

	public void setElementType(Element elementType) {
		this.elementType = elementType;
	}

}
