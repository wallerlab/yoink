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
package org.wallerlab.yoink.math

import org.wallerlab.yoink.math.SetOps
import spock.lang.Specification;

class SetOpsSpec extends Specification{

	def " method diff can read two sets as parameters "(){
		def set1=new HashSet<>()
		def set2=new HashSet<>()
		when:"make a new SetOps"
		def setDifference= new SetOps()
		then:"diff needs two Sets as arguments"
		def setDiff=setDifference.diff(set1,set2)
	}

	def " method diff read two sets in different parametrs"(){

		def set1=new HashSet<String>()
		def set2=new HashSet<Integer>()
		when:"make a new SetOps"
		def setDifference= new SetOps()
		then:"diff can read two different Sets as arguments"
		def setDiff=setDifference.diff(set1,set2)
	}

	def " when the two sets in diffrent types, the difference is the sum of two sets"(){
		given:
		def set1=new HashSet<String>()
		set1.add("one")
		set1.add("two")
		def set2=new HashSet<Integer>()
		set2.add(1)
		set2.add(2)
		when:"make a new SetOps"
		def setDifference= new SetOps()
		then:"difference of two different type Sets is the their sum"
		def setDiff=setDifference.diff(set1,set2)
		def result=new HashSet<>(Arrays.asList("one","two",1,2))
		setDiff==result
	}


	def " when  two sets are in same type, but no  same elements, the difference   is the sum of two sets"(){
		given:"a new SetOps"
		def setDifference= new SetOps()
		when:"two same type Sets have no same elements"
		def set1=new HashSet<String>()
		set1.add("one")
		set1.add("two")
		def set2=new HashSet<String>()
		set2.add("three")
		then:"the difference is their sum"
		def setDiff=setDifference.diff(set1,set2)
		def result=new HashSet<>(Arrays.asList("one","two","three"))
		setDiff==result
	}

	def "when  two sets are in same type and have  same elements,the difference of is the subtraction of two sets"(){
		given:
		def setDifference= new SetOps()
		when:"two same type Sets have same elements"
		def set1=new HashSet<String>()
		set1.add("one")
		set1.add("two")
		def set2=new HashSet<String>()
		set2.add("one")
		def result=new HashSet<>(Arrays.asList("two"))
		def setDiff=setDifference.diff(set1,set2)
		then:"assert the value of difference"
		setDiff==result
		when:"change the order of two sets"
		setDiff=setDifference.diff(set2,set1)
		then:"result does not change."
		setDiff==result
	}

	def "input array is null"(){
		int[] s =null;
		when:"splie an empty array"
		def result= SetOps.split(s);
		then:"result is null"
		result==null;
	}


	def "generate all subsets"(){
		int[] s = [1, 2];
		when:"spile an array"
		def	result= SetOps.split(s);
		then:"check the size of result"
		result.size()==4
		then:"check the content of result"
		List<Integer> subset1=[];
		List<Integer> subset2=[1];
		List<Integer> subset3=[2];
		List<Integer> subset4=[1, 2];
		List<List<Integer>> allSubsets=[
			subset1,
			subset2,
			subset3,
			subset4
		];
		result.size()==allSubsets.size();
		result.toSet().containsAll(allSubsets.toSet())
	}
}
