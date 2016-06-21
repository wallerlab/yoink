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
package org.wallerlab.yoink.math.linear;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * Vector Adaptor pattern.
 * 
 * thsi class is to convert from our own implementation to the appache commons
 * maths lib.
 * 
 * @author Min Zheng
 *
 */

public class CommonsVector3D implements Vector<Vector3D> {

	private Vector3D internalVector;

	// this vextor is used as intermediate vector in internalVector operation.
	private Vector3D tempVector;

	public CommonsVector3D(double x, double y, double z) {
		this.internalVector = new Vector3D(x, y, z);

	}

	public CommonsVector3D() {

	}

	public CommonsVector3D(double[] d) {
		this.internalVector = new Vector3D(d);
	}

	@Override
	public double dotProduct() {
		return this.internalVector.dotProduct(this.internalVector);
	}

	@Override
	public double getNorm() {
		return this.internalVector.getNorm();
	}

	@Override
	public Vector add(Vector m) {
		tempVector = this.internalVector.add((Vector3D) m.getInternalVector());
		Vector temp = new CommonsVector3D();
		temp.setInternalVector(tempVector);
		return temp;
	}

	@Override
	public Vector subtract(Vector m) {
		tempVector = this.internalVector.subtract((Vector3D) m
				.getInternalVector());
		Vector temp = new CommonsVector3D();
		temp.setInternalVector(tempVector);
		return temp;
	}

	@Override
	public Vector scalarMultiply(double d) {
		Vector temp = new CommonsVector3D();
		temp.setInternalVector(this.internalVector.scalarMultiply(d));
		return temp;
	}

	@Override
	public double getX() {
		return this.internalVector.getX();
	}

	@Override
	public double getY() {
		return this.internalVector.getY();
	}

	@Override
	public double getZ() {
		return this.internalVector.getZ();
	}

	@Override
	public double distance(Vector m) {
		return this.internalVector.distance((Vector3D) m.getInternalVector());
	}

	@Override
	public Vector ebeMultiply(Vector m) {
		Vector3D tempM = (Vector3D) m.getInternalVector();
		double x = tempM.getX() * this.internalVector.getX();
		double y = tempM.getY() * this.internalVector.getY();
		double z = tempM.getZ() * this.internalVector.getZ();
		tempVector = new Vector3D(x, y, z);
		Vector temp = new CommonsVector3D();
		temp.setInternalVector(tempVector);
		return temp;

	}

	@Override
	public double getEntry(int i) {
		double element = 0;
		switch (i) {
		case 0:
			element = this.internalVector.getX();
			break;
		case 1:
			element = this.internalVector.getY();
			break;
		case 2:
			element = this.internalVector.getZ();
			break;
		}
		return element;
	}

	@Override
	public Vector3D getInternalVector() {
		return this.internalVector;
	}

	@Override
	public void setInternalVector(Vector3D internalVector) {
		this.internalVector = internalVector;
	}

	@Override
	public boolean equals(Vector m) {
		return this.internalVector.equals(m.getInternalVector());
	}

	@Override
	public double[] toArray() {
		return this.internalVector.toArray();
	}

	@Override
	public Vector exp() {

		double x = Math.exp(this.internalVector.getX());
		double y = Math.exp(this.internalVector.getY());
		double z = Math.exp(this.internalVector.getZ());
		tempVector = new Vector3D(x, y, z);
		Vector temp = new CommonsVector3D();
		temp.setInternalVector(tempVector);
		return temp;

	}
}
