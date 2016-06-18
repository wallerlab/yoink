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
package org.wallerlab.yoink.math.map

import spock.lang.Specification;


class MapDifferenceSpec extends Specification {


	def "two maps in diffrent types, reuslt is the first ma"(){
		def map1=["one":1,"two":2]
		def map2=[1:"one",4:"four"]
		when:
		def map=MapDifference.diff(map1, map2);
		then:
		assert map instanceof Map
		map.size()==2
		map.equals(map1);
	}


	def "two maps in same type, the second map just has elements in the first map "(){
		def map1=["one":1,"two":2]
		def map2=["one":1]
		when:
		def map=MapDifference.diff(map1, map2);

		then:
		map.size()==1
		map.equals(["two":2])
	}

	def "two maps in same type, the second map has elements in the first map, also has elements not in the first map"(){
		def map1=["one":1,"two":2]
		def map2=["one":1,"four":4]
		when:
		def map=MapDifference.diff(map1, map2);

		then:
		map.size()==1;
		map.equals(["two":2])
	}
}
