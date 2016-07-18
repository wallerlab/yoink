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
package org.wallerlab.yoink.api.service.math;

/**
 * It is an interface for external Matrix libraries. It contains methods about
 * matrix and enum Matrix.Type.
 * 
 * @author Min Zheng
 * @param <T>
 *            - the matrix type in the library
 */

public interface Matrix<T> {

	/**
	 * initialize this matrix with two-dimensional double array
	 * 
	 * @param d
	 *            , a two dimensional double array
	 */
	void array2DRowRealMatrix(double[][] d);

	/**
	 * set the value of one element in this matrix
	 * 
	 * @param row
	 *            -integer, the row index
	 * @param column
	 *            -integer, the column index
	 * @param value
	 *            - the value of the element, {@link java.lang.Double}
	 */
	void setEntry(int row, int column, double value);

	/**
	 * add a value to one element in this matrix
	 * 
	 * @param row
	 *            -integer, the row index
	 * @param column
	 *            -integer, the column index
	 * @param increment
	 *            - {@link java.lang.Double},the value added to the element
	 */
	void addToEntry(int row, int column, double increment);

	/**
	 * get the value of one element in this matrix
	 * 
	 * @param row
	 *            -integer, the row index
	 * @param column
	 *            -integer, the column index
	 * @return the element value -{@link java.lang.Double}
	 */
	double getEntry(int row, int column);

	/**
	 * add one matrix to this matrix
	 * 
	 * @param m
	 *            -{@link Matrix} will be
	 *            added to this matrix
	 * @return a matrix -{@link Matrix}
	 */
	Matrix add(Matrix m);

	/**
	 * 
	 * subtract one matrix from this matrix
	 * 
	 * @param m
	 *            -{@link Matrix} will be
	 *            subtracted from this matrix
	 * @return a matrix -{@link Matrix}
	 */
	Matrix subtract(Matrix m);

	/**
	 * scalar this matrix with a double value
	 * 
	 * @param d
	 *            , scalar value, -{@link java.lang.Double}
	 * @return a matrix -{@link Matrix}
	 */
	Matrix scalarMultiply(double d);

	/**
	 * transpose this matrix
	 * 
	 * @return a matrix -{@link Matrix}
	 */
	Matrix transpose();

	/**
	 * multiply this matrix with another matrix m element by element
	 * 
	 * @param m
	 *            ,a matrix -{@link Matrix}
	 * @return a matrix -{@link Matrix}
	 */
	Matrix ebeMultiply(Matrix m);

	/**
	 * get the value of this matrix in the form a two-dimensional double array
	 * 
	 * @return a two-dimensional double array
	 */
	double[][] getData();

	/**
	 * get the value of one row in this matrix in the form of one-dimensional
	 * double array.
	 * 
	 * @param i
	 *            , the row index
	 * @return one-dimensional double array
	 */
	double[] getRow(int i);

	/**
	 * get the dot product of this matrix. it works when matrix is a vector
	 * 
	 * @return {@link java.lang.Double}
	 */
	double dotProduct();

	/**
	 * get the dot product of this matrix and another matrix m. it works when matrix is a vector
	 *
	 * @param m to be used for operation
	 * @return {@link java.lang.Double}
	 */
	double dotProduct(Matrix m);
	
	/**
	 * get the distance between this matrix and another matrix m.it works when
	 * matrix is a vector
	 * 
	 * @param m
	 *            -{@link Matrix}
	 * @return {@link java.lang.Double}
	 */
	double distance(Matrix m);

	/**
	 * get the norm of this matrix. it works when matrix is a vector
	 * 
	 * @return {@link java.lang.Double}
	 */
	double getNorm();

	/**
	 * check if this matrix equals another matrix m.
	 * 
	 * @param m
	 *            -{@link Matrix}
	 * @return {@link java.lang.Boolean}
	 */
	boolean equals(Matrix m);

	/**
	 * get the value of internal matrix of this matrix
	 * 
	 * @return a matrix in the matrix type of the library
	 */
	T getInternalMatrix();

	/**
	 * set the value of internal matrix of this matrix
	 * 
	 * @param internalMatrix
	 *            , a matrix of the library
	 */
	void setInternalMatrix(T internalMatrix);

	/**
	 * matrix type is named after the library it is in.
	 * 
	 * @author Min Zheng
	 *
	 */
	 enum Type {

		COMMONS, COMMONS_VECTOR3D
	}

}
