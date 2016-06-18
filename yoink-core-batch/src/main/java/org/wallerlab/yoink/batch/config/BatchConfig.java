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
public class BatchConfig  implements ApplicationContextAware{

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
	public ItemProcessor<JAXBElement, JAXBElement> jobBuilder;

	@Autowired
	@Qualifier("adaptiveProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> adaptiveProcessor;

	@Autowired
	@Qualifier("clusterProcessor")
	public ItemProcessor<Job<JAXBElement>, Job> clusterProcessor;


	@Autowired
	ItemWriter jmsWriter;

	@Autowired
	ItemWriter fileWriter;


	@Bean
	org.springframework.batch.core.Job jmsAdaptive(){ return job(jmsStep(adaptiveProcessor),"jmsAdaptive");}

	@Bean
	org.springframework.batch.core.Job jmsCluster(){ return job(jmsStep(clusterProcessor),"jmsCluster");}

	@Bean
	org.springframework.batch.core.Job fileAdaptive(){ return job(fileStep(adaptiveProcessor),"fileAdaptive");}

	@Bean
	org.springframework.batch.core.Job fileCluster(){ return job(fileStep(clusterProcessor),"fileCluster");}




	/**
	 * build executing steps
	 *
	 * @param stepBuilderFactory
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
	 * @param cmlFilereader
	 *            -{@link org.springframework.batch.item.ItemReader}
	 * @param serialAdaptiveQMMMProcessor
	 *            -{@link org.springframework.batch.item.ItemProcessor}
	 * @param cmlFileResponseWriter
	 *            -{@link org.springframework.batch.item.ItemWriter}
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
	 * @param jobs
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
	 *
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
	 * @param jobs
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
	 *
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	@Bean
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
	 * @param stepBuilderFactory
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
	 * @return Step -{@link org.springframework.batch.core.Step}
	 */
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