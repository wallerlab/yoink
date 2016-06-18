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
package org.wallerlab.yoink.math.set

import spock.lang.Specification;

class SubsetsSpec  extends Specification{

	def "input array is null"(){
		int[] s =null;
		when:"splie an empty array"
		def result=Subsets.split(s);
		then:"result is null"
		result==null;
	}


	def "generate all subsets"(){
		int[] s = [1, 2];
		when:"spile an array"
		def	result=Subsets.split(s);
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
