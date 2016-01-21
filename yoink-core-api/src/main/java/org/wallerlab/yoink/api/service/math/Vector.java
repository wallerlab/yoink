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
 * It is an interface for external Vector libraries and enum of 3D Vector types.
 * 
 * @author Min Zheng
 * @param <T>
 *            - the vector type inside the library
 */
public interface Vector<T> {

	/**
	 * get the sum of this vector with another vector
	 * 
	 * @param m
	 *            -{@link org.wallerlab.yoink.api.service.math.Vector}
	 * @return the sum of two vectors, -
	 *         {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector add(Vector m);

	/**
	 * get the result of this vector subtracts another vector
	 * 
	 * @param m
	 *            -{@link org.wallerlab.yoink.api.service.math.Vector}
	 * @return the subtraction of two vectors, -
	 *         {@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector subtract(Vector m);

	/**
	 * multiply this vector with a scalar value
	 * 
	 * @param d
	 *            -the scalar value, -{@link java.lang.Double}
	 * @return a vector, -{@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector scalarMultiply(double d);

	/**
	 * multiply this vector with another vector element by element
	 * 
	 * @param m
	 *            -{@link org.wallerlab.yoink.api.service.math.Vector}
	 * @return a vector, -{@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector ebeMultiply(Vector m);

	/**
	 * get the value of x coordinate of this vector
	 * 
	 * @return {@link java.lang.Double}
	 */
	double getX();

	/**
	 * get the value of y coordinate of this vector
	 * 
	 * @return {@link java.lang.Double}
	 */
	double getY();

	/**
	 * get the value of z coordinate of this vector
	 * 
	 * @return {@link java.lang.Double}
	 */
	double getZ();

	/**
	 * get one element in this vector
	 * 
	 * @param i
	 *            , the index of element
	 * @return the element value, {@link java.lang.Double}
	 */
	double getEntry(int i);

	/**
	 * get the dot product of this vector with itself
	 * 
	 * @return {@link java.lang.Double}
	 */
	double dotProduct();

	/**
	 * get the distance between this vector and another vector
	 * 
	 * @param m
	 *            - {@link org.wallerlab.yoink.api.service.math.Vector}
	 * @return distance -{@link java.lang.Double}
	 */
	double distance(Vector m);

	/**
	 * get the norm of this vector
	 * 
	 * @return {@link java.lang.Double}
	 */
	double getNorm();

	/**
	 * check if this vector equals another vector m.
	 * 
	 * @param m
	 *            - {@link org.wallerlab.yoink.api.service.math.Vector}
	 * @return {@link java.lang.Boolean}
	 */
	boolean equals(Vector m);

	/**
	 * get the value of this vector in the form of a double array
	 * 
	 * @return a double array
	 */
	double[] toArray();

	/**
	 * get the value of internal vector of this vector in the vector type in the
	 * library
	 * 
	 * @return a vector in the vector type in the library
	 */
	public T getInternalVector();

	/**
	 * set the value of internal vector
	 * 
	 * @param internalVector
	 *            , a vector in the vector type in the library
	 */
	public void setInternalVector(T internalVector);

	/**
	 * do Math.exp() calculation for every element in the vector
	 * 
	 * @return a vector, -{@link org.wallerlab.yoink.api.service.math.Vector}
	 */
	Vector exp();

	/**
	 * 3D Vector type is named after the library it is in.
	 * 
	 * @author Min Zheng
	 *
	 */
	public enum Vector3DType {

		COMMONS
	}

}
