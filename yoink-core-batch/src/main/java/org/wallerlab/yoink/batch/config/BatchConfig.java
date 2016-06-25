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
package org.wallerlab.yoink.batch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.wallerlab.yoink.api.model.batch.Job;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is configuration for spring batch
 *
 * @author Min Zheng
 */
@Lazy
@Configuration
@EnableBatchProcessing
@ComponentScan("org.wallerlab.yoink.batch")
@PropertySource("classpath:batch.properties")
public class BatchConfig  {

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	@Qualifier("itemReader")
	ItemReader itemReader;

	@Autowired
	@Qualifier("builderProcessor")
	public ItemProcessor<JAXBElement, Job<JAXBElement>> builderProcessor;

	@Autowired
	@Qualifier("partitionerProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> regionizerProcessor;

	@Autowired
	@Qualifier("smoothnerProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> smoothnerProcessor;

	@Autowired
	@Qualifier("clusteringProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> clusterProcessor;

	@Autowired
	@Qualifier("wrapperProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> wrapperProcessor;

	@Bean
	ItemProcessor<JAXBElement, Job> compositeProcessor(){
		CompositeItemProcessor compositeProcessor = new CompositeItemProcessor();
		List<ItemProcessor> processors = new ArrayList<ItemProcessor>();
		processors.add(builderProcessor);
		processors.add(regionizerProcessor);
		processors.add(smoothnerProcessor);
		processors.add(clusterProcessor);
		processors.add(wrapperProcessor);
		compositeProcessor.setDelegates(processors);
		return compositeProcessor;
	}

	@Autowired
	ItemWriter itemWriter;


	/**
	 * build a batch job using a batch based approach.
	 *
	 * @param step to be executed
	 * @param name string to be used as job ID.
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	@Bean
	public org.springframework.batch.core.Job fileJob(@Qualifier("compositeStep") Step step) {
		return jobBuilderFactory.get("yoink")
								.incrementer(new RunIdIncrementer())
								.flow(step)
								.end()
								.build();
	}

	/**
	 * build executing steps
	 *
	 * @param processor to do computation
	 *
	 * @return Step -{@link Step}
	 */
	@Bean
	public Step compositeStep(@Qualifier("compositeProcessor") ItemProcessor processor) {
		return stepBuilderFactory.get("step")
							  	 .<JAXBElement, Job> chunk(1)
								 .reader(itemReader)
								 .processor(processor)
								 .writer(itemWriter)
								 .build();
	}

}