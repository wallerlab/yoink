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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.wallerlab.yoink.batch.api.model.bootstrap.Job;

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
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class BatchConfig  implements ApplicationContextAware {

	ApplicationContext appContext;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	JobBuilderFactory jobs;


	@Autowired
	@Qualifier("jmsRequestReader")
	ItemReader<String> jmsReader;

	@Autowired
	@Qualifier("cmlFileReader")
	ItemReader fileReader;

	@Autowired
	@Qualifier("jobBuilder")
	public ItemProcessor<JAXBElement, JAXBElement> builderProcessor;

	@Autowired
	@Qualifier("adaptiveProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> adaptiveProcessor;

	@Autowired
	@Qualifier("clusterProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> clusterProcessor;


	@Bean
	ItemProcessor<JAXBElement, Job> builderAndAdaptiveProcessor(){
		CompositeItemProcessor compositeProcessor = new CompositeItemProcessor();
		List<ItemProcessor> processors = new ArrayList<ItemProcessor>();
		processors.add(builderProcessor);
		processors.add(adaptiveProcessor);
		compositeProcessor.setDelegates(processors);
		return compositeProcessor;
	}

	@Bean
	ItemProcessor<JAXBElement, Job> builderAndClusterProcessor(){
		CompositeItemProcessor compositeProcessor = new CompositeItemProcessor();
		List<ItemProcessor> processors = new ArrayList<ItemProcessor>();
		processors.add(builderProcessor);
		processors.add(clusterProcessor);
		compositeProcessor.setDelegates(processors);
		return compositeProcessor;
	}

	@Autowired
	ItemWriter jmsWriter;

	@Autowired
	ItemWriter fileWriter;


	@Bean
	org.springframework.batch.core.Job jmsAdaptive(){ return job(jmsStep(builderAndAdaptiveProcessor()),"jmsAdaptive");}

	@Bean
	org.springframework.batch.core.Job jmsCluster(){ return job(jmsStep(builderAndClusterProcessor()),"jmsCluster");}

	@Bean
	org.springframework.batch.core.Job fileAdaptive(){ return job(fileStep(builderAndAdaptiveProcessor()),"fileAdaptive");}

	@Bean
	org.springframework.batch.core.Job fileCluster(){ return job(fileStep(builderAndClusterProcessor()),"fileCluster");}




	/**
	 * build executing steps
	 *
	 * @param processor to do computation
	 *
	 * @return Step -{@link org.springframework.batch.core.Step}
	 */
	@Bean
	public Step fileStep(ItemProcessor processor) {
		return stepBuilderFactory
				.get("adaptiveQMMMBatch").<JAXBElement, org.wallerlab.yoink.batch.api.model.bootstrap.Job> chunk(1)
				.reader(fileReader)
				.processor(processor) // ADAPTIVE OR CLUSTERER
				.writer(fileWriter)
				.build();
	}

	/**
	 * build a batch job using a batch based approach.
	 *
	 * @param step to be executed
	 * @param name string to be used as job ID.
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	private org.springframework.batch.core.Job fileJob(Step step,String name) {
		return jobs.get(name)
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end()
				.build();
	}


	/**
	 * build a batch job using a JMS based approach.
	 *
	 * @param step to be executed
	 * @param name string to be used as job ID.
	 *
	 *
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	public org.springframework.batch.core.Job job( Step step, String name) {
		return jobs.get(name)
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end()
				.build();
	}

	/**
	 * build executing steps
	 *
	 * @param processor for computation
	 *
	 * @return Step -{@link org.springframework.batch.core.Step}
	 */
	@Bean
	public Step jmsStep(ItemProcessor processor) {
		return stepBuilderFactory.get("adaptiveQMMMJms")
				.<String, org.wallerlab.yoink.batch.api.model.bootstrap.Job> chunk(1)
				.reader(jmsReader)
				.processor(processor)
				.writer(jmsWriter)
				.build();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}
}