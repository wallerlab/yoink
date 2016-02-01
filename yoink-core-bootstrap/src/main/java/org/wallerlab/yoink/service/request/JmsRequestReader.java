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
package org.wallerlab.yoink.service.request;

import java.util.Optional;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * this class is for job request, to get all xml files off JMS
 * 
 *
 */
public class JmsRequestReader implements ItemReader<String> {

	private ItemReader<String> jmsItemReader;
	
	private boolean jmsResquestReaderServiceRunning = true;

	private Optional<String> item;
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		while (jmsResquestReaderServiceRunning) {
			item = Optional.ofNullable(jmsItemReader.read());		
			if (!item.isPresent()) {
				Thread.sleep(1000);
			} else
				break;
		}
		return item.get();
	}

	public ItemReader<String> getJmsItemReader() {
		return jmsItemReader;
	}

	public void setJmsItemReader(ItemReader<String> jmsItemReader) {
		this.jmsItemReader = jmsItemReader;
	}

}
