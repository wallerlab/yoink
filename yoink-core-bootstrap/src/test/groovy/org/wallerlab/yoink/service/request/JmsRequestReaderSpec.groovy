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
package org.wallerlab.yoink.service.request

import spock.lang.Specification;
import spock.lang.Unroll
import spock.lang.Timeout
import spock.lang.Ignore

import java.util.concurrent.TimeoutException;

import org.springframework.batch.item.ItemReader


class JmsRequestReaderSpec extends Specification{

	@Unroll
	def "test to see if the jms  request method read() works"(){
			
		when:
		def requestReader = new JmsRequestReader()
		def ir = Mock(ItemReader)
		ir.read() >> "message"
		requestReader.jmsItemReader = ir
		
		then:"check the request"
		 def message =  requestReader.read()
		message == "message"
		 
	}
	
	@Ignore
	@Timeout(1)
	@Unroll
	def "test to see if the jms request method read() works on null"(){
			
		when:
		def requestReader  =  new JmsRequestReader()
		def ir = Mock(ItemReader)
		ir.read() >> null
		requestReader.jmsItemReader = ir
		
		then:"check the request"
		def message = requestReader.read()
		 thrown(TimeoutException)
	}
}
