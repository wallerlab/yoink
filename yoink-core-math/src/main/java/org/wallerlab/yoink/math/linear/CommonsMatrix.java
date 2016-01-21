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
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.wallerlab.yoink.api.service.math.Matrix;

/**
 * Matrix Adaptor pattern.
 * 
 * this class i sto convert from our own implementation to the appache commons
 * maths lib.
 * 
 * @author Min Zheng
 *
 */

public class CommonsMatrix implements Matrix<RealMatrix> {

	private RealMatrix internalMatrix;

	// this matrix is used as intermediate matrix in internalMatrix operation.
	private RealMatrix tempMatrix;

	public CommonsMatrix() {
		this.internalMatrix = new Array2DRowRealMatrix();
	}

	public CommonsMatrix(int rowDimension, int columnDimension) {
		this.internalMatrix = MatrixUtils.createRealMatrix(rowDimension,
				columnDimension);
	}

	@Override
	public void array2DRowRealMatrix(double[][] d) {
		this.internalMatrix = new Array2DRowRealMatrix(d);

	}

	@Override
	public void setEntry(int row, int column, double value) {
		this.internalMatrix.setEntry(row, column, value);

	}

	@Override
	public void addToEntry(int row, int column, double value) {
		this.internalMatrix.addToEntry(row, column, value);

	}

	@Override
	public double getEntry(int row, int column) {

		return this.internalMatrix.getEntry(row, column);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Matrix transpose() {

		tempMatrix = this.internalMatrix.transpose();
		Matrix newMatrix = new CommonsMatrix();
		newMatrix.setInternalMatrix(tempMatrix);
		return newMatrix;

	}

	@Override
	public Matrix add(Matrix m) {
		tempMatrix = this.internalMatrix
				.add((RealMatrix) m.getInternalMatrix());
		Matrix newMatrix = new CommonsMatrix();
		newMatrix.setInternalMatrix(tempMatrix);
		return newMatrix;
	}

	@Override
	public Matrix subtract(Matrix m) {
		tempMatrix = this.internalMatrix.subtract((RealMatrix) m
				.getInternalMatrix());
		Matrix newMatrix = new CommonsMatrix();
		newMatrix.setInternalMatrix(tempMatrix);
		return newMatrix;
	}

	@Override
	public Matrix scalarMultiply(double d) {
		tempMatrix = this.internalMatrix.scalarMultiply(d);
		Matrix newMatrix = new CommonsMatrix();
		newMatrix.setInternalMatrix(tempMatrix);
		return newMatrix;
	}

	// when the matrix is a 3D vector
	@Override
	public double dotProduct() {
		double[] v = this.internalMatrix.getRow(0);
		Vector3D vector = new Vector3D(v);
		return vector.dotProduct(vector);
	}

	@Override
	public double[][] getData() {
		return this.internalMatrix.getData();
	}

	@Override
	public double[] getRow(int i) {
		return this.internalMatrix.getRow(i);
	}

	public RealMatrix getInternalMatrix() {
		return internalMatrix;
	}

	public void setInternalMatrix(RealMatrix internalMatrix) {
		this.internalMatrix = internalMatrix;
	}

	/**
	 * Element-by-element multiplication of two 3D vectors.
	 */
	@Override
	public Matrix ebeMultiply(Matrix m) {
		double[] v1 = this.internalMatrix.getRow(0);
		RealVector vector1 = new ArrayRealVector(v1);
		double[] v2 = ((RealMatrix) m.getInternalMatrix()).getRow(0);
		RealVector vector2 = new ArrayRealVector(v2);
		RealVector vectorEBE = vector1.ebeMultiply(vector2);
		double[] vEBE = vectorEBE.toArray();
		tempMatrix = MatrixUtils.createRealMatrix(1, 3);
		tempMatrix.setRow(0, vEBE);
		Matrix newMatrix = new CommonsMatrix();
		newMatrix.setInternalMatrix(tempMatrix);
		return newMatrix;
	}

	// when the matrix is a 3D vector
	@Override
	public double distance(Matrix m) {
		double[] v1 = this.internalMatrix.getRow(0);
		Vector3D vector1 = new Vector3D(v1);
		double[] v2 = ((RealMatrix) m.getInternalMatrix()).getRow(0);
		Vector3D vector2 = new Vector3D(v2);
		return vector1.distance(vector2);
	}

	@Override
	public double getNorm() {
		double[] v1 = this.internalMatrix.getRow(0);
		Vector3D vector1 = new Vector3D(v1);
		return vector1.getNorm();
	}

	@Override
	public boolean equals(Matrix m) {
		return this.internalMatrix.equals(m.getInternalMatrix());
	}

}
