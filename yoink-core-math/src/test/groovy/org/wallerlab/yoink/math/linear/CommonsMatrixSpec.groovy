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
package org.wallerlab.yoink.math.linear

import org.wallerlab.yoink.api.service.math.Matrix;

import spock.lang.Ignore;
import spock.lang.Specification;


/**
 * The Class CommonsMatrixSpec.
 */
class CommonsMatrixSpec extends Specification{


	/**
	 * Default constructor makes a new 2 d matrix.
	 *
	 * @return the object
	 */
	def"default constructor makes a new 2D matrix"(){

		when:

		def matrix= new CommonsMatrix()
		then:

		assert matrix instanceof Matrix

		matrix.getInternalMatrix()

		matrix.internalMatrix.getClass().getName()=="org.apache.commons.math3.linear.Array2DRowRealMatrix"
	}

	/**
	 * Creates the a matri(0x0).
	 *
	 * @return the object
	 */
	@Ignore
	def"create a matri(0x0)"(){
		when:
		def matrix= new CommonsMatrix()
		then:
		matrix.CommonsMatrix(0, 0)
	}

	/**
	 * Creates the a matri when column is zero.
	 *
	 * @return the object
	 */
	@Ignore
	def"create a matri when column is zero"(){
		when:
		def matrix= new CommonsMatrix()

		then:
		matrix.CommonsMatrix(3, 0)
	}

	/**
	 * Creates the a matri(0x3).
	 *
	 * @return the object
	 */
	@Ignore
	def"create a matri(0x3)"(){
		when:
		def matrix= new CommonsMatrix()
		then:
		matrix.CommonsMatrix(0, 3)
	}

	def"create a matrix, row and column both are positive integer"(){
		when:
		def matrix= new CommonsMatrix(1,3)
		then:

		matrix.data==[[0.0, 0.0, 0.0]]
	}


	def"convert a double[][] to a 2D Matrix"(){
		when:
		def matrix= new CommonsMatrix()
		double[][] d= [[1.0, 2.0], [1.0, 2.0]]
		then:
		matrix.array2DRowRealMatrix(d)
		matrix.data==d
	}

	def"change(set/get/add) the value of a element in matrix"(){
		when:
		def matrix= new CommonsMatrix(1,3)

		then:
		matrix.setEntry(0,0,25)
		matrix.getEntry(0, 0)==25
		matrix.addToEntry(0, 0, 25)
		matrix.getEntry(0, 0)==50
	}

	def"test mehtod transpose"(){
		when:
		Matrix matrix= new CommonsMatrix(1,3)

		matrix.setEntry(0, 0, 1)
		Matrix resultMatrix=new CommonsMatrix(1,3)

		resultMatrix.setEntry(0,0,1)
		then:
		Matrix t=matrix.transpose()
		assert t.class==CommonsMatrix
		assert	t instanceof Matrix
	}

	def"sum two matrices"(){
		when:
		double[][] d= [[1.0, 2.0, 3.0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		Matrix n=m
		then:
		Matrix sum=m.add(n)
		sum.data==[[2.0, 4.0, 6.0]]
	}

	def"substraction of two matrices"(){
		when:
		double[][] d= [[1.0, 2.0, 3.0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		Matrix n=m
		then:
		Matrix sub=m.subtract(n)
		sub.data==[[0, 0, 0]]
	}

	def"scalar multiply"(){
		when:
		double[][] d= [[1.0, 2.0, 3.0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		Matrix result=m.scalarMultiply(2)
		then:
		result.data==[[2.0, 4.0, 6.0]]
	}

	def"dot product"(){
		when:
		double[][] d= [[1.0, 1.0, 1.0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		then:
		m.dotProduct()==3
	}

	def"get rew"(){
		when:
		double[][] d= [[1.0, 1.0, 1.0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		then:
		m.getRow(0)==[1.0, 1.0, 1.0]
	}

	def"element by element multiply"(){
		when:
		double[][] d= [[1.0, 1.0, 1.0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		then:
		Matrix n=m.ebeMultiply(m)
		n.data==[[1.0, 1.0, 1.0]]
	}

	def "distance between two vectors"(){
		when:
		double[][] d= [[1, 2, 0]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		d=[[-2, 3, 5]]
		Matrix n=new CommonsMatrix()
		n.array2DRowRealMatrix(d)
		then:
		(m.distance(n)-Math.sqrt(35))<1.0E-5
	}

	def "calculate norm of a vector"(){
		when:
		double[][] d= [[1, 2, 3]]
		Matrix m= new CommonsMatrix()
		m.array2DRowRealMatrix(d)
		then:
		(m.norm-Math.sqrt(14))<1.0E-5
	}
}
