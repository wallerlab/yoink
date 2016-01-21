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

class MapSorterSpec extends Specification {

	def " map is empty "(){
		when:"the map will be sorted is empty"
		def mapSorter=new MapSorter()
		def map= new HashMap<String,Integer>()
		then:"the size of return value is 0"
		mapSorter.sortByValue(map).size() == 0
	}

	def "sort by value"(){
		when:"new MapSorter, the map will be sorted is not empty"
		def mapSorter=new MapSorter()
		def map= new HashMap<String,Integer>()
		map.put("one", 1)
		map.put("three", 3)
		map.put("two", 2)
		then:"assert the return value "
		def sortedMap=mapSorter.sortByValue(map)
		sortedMap.keySet()==(Set<String>)["one", "two", "three"]
	}
}
