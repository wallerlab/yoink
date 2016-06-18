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

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import spock.lang.Specification;
class CommonsVector3DSpec extends Specification{

	def" create a 3D vector using three double value "(){
		when:
		def commonsVector= new CommonsVector3D(0,0,0);
		def vectorFromLiarary=new Vector3D(0,0,0);
		then:
		commonsVector.internalVector.equals(vectorFromLiarary);
	}

	def" create a 3D vector using double array "(){
		double[] d=[0.1, 0.1, 0.1]
		when:
		def commonsVector= new CommonsVector3D(d);
		def vectorFromLiarary=new Vector3D(d);
		then:
		commonsVector.internalVector.equals(vectorFromLiarary);
	}

	def"test dot procut"(){
		when:
		def commonsVector= new CommonsVector3D(1,2,2);
		then:
		assert Math.abs(commonsVector.dotProduct()-9.0)<=1.0E-6
	}

	def"test vector norm"(){
		when:
		def commonsVector= new CommonsVector3D(1,2,2);
		then:
		assert Math.abs(commonsVector.getNorm()-3.0)<=1.0E-6
	}


	def "test operator add"(){
		when:
		def v1= new CommonsVector3D(1,1,1);
		def v2=new CommonsVector3D(2,2,2);
		def v=v1.add(v2);
		then:
		v.internalVector.equals(new Vector3D(3,3,3));
	}

	def "test operator substract"(){
		when:
		def v1= new CommonsVector3D(1,1,1);
		def v2=new CommonsVector3D(2,2,2);
		def v=v2.subtract(v1);
		then:
		v.internalVector.equals(new Vector3D(1,1,1));
	}

	def "test operator scalrMultiply"(){
		when:
		def v1= new CommonsVector3D(1,1,1);
		def v=v1.scalarMultiply(2);
		then:
		v.internalVector.equals(new Vector3D(2,2,2));
	}


	def "test element getter methods"(){
		when:
		def v= new CommonsVector3D(1,2,3);
		then:
		assert	Math.abs(v.getX()-1)<=1.0E-6;
		assert	Math.abs(v.getY()-2)<=1.0E-6;
		assert	Math.abs(v.getZ()-3)<=1.0E-6;
	}

	def "test method getEntry(int i)"(){
		when:
		def v= new CommonsVector3D(1,2,3);
		then:
		assert	Math.abs(v.getEntry(0)-1)<=1.0E-6;
		assert	Math.abs(v.getEntry(1)-2)<=1.0E-6;
		assert	Math.abs(v.getEntry(2)-3)<=1.0E-6;
	}
	
	def "test method ebeMultiply()"(){
		when:
		def v1= new CommonsVector3D(1,1,1);
		def v2=new CommonsVector3D(2,2,2);
		def v=v1.ebeMultiply(v2)
		then:
		v.internalVector.equals(new Vector3D(2,2,2))
	}
	
	
}
