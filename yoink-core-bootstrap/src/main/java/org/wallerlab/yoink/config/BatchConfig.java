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
package org.wallerlab.yoink.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.xml_cml.schema.Cml;

import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.batch.item.jms.JmsItemReader;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.wallerlab.yoink.service.request.JmsRequestReader;
import org.wallerlab.yoink.service.response.JmsJobItemWriter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

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
public class BatchConfig {

	@Autowired
	ApplicationContext appContext;

	@Autowired
	@Qualifier("serviceStep")
	private Step serviceStep;

	
	/**
	 * build whole job using a service based job
	 *
	 * @param jobs
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
	 *
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	@Bean
	public org.springframework.batch.core.Job importServiceJob(JobBuilderFactory jobs) {
		return jobs.get("service")
				.incrementer(new RunIdIncrementer())
				.flow(serviceStep)
				.end()
				.build();
	}

	@Autowired
	@Qualifier("batchStep")
	private Step batchStep;

	
	/**
	 * build a batch job using a batch based approach.
	 *
	 * @param jobs
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
	 *
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	@Bean
	public org.springframework.batch.core.Job importBatchJob(JobBuilderFactory jobs) {
		return jobs.get("batch")
				.incrementer(new RunIdIncrementer())
				.flow(batchStep)
				.end()
				.build();
	}

	@Autowired
	@Qualifier("jmsStep")
	private Step jmsStep;
	
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
	public org.springframework.batch.core.Job importJmsJob(JobBuilderFactory jobs) {
		return jobs.get("jms")
				.incrementer(new RunIdIncrementer())
				.flow(jmsStep)
				.end()
				.build();
	}

	/**
	 * build executing steps
	 *
	 * @param stepBuilderFactory
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
	 * @param cmlFilesRequest
	 *            -{@link org.springframework.batch.item.ItemReader}
	 * @param adaptiveQMMMProcessor
	 *            -{@link org.springframework.batch.item.ItemProcessor}
	 * @param cmlFilesResponse
	 *            -{@link org.springframework.batch.item.ItemWriter}
	 * @return Step -{@link org.springframework.batch.core.Step}
	 */
	@Bean
	public Step serviceStep(StepBuilderFactory stepBuilderFactory, ItemReader<List<File>> cmlFilesRequest,
			ItemProcessor<List<File>, List<org.wallerlab.yoink.api.model.bootstrap.Job>> adaptiveQMMMProcessor,
			ItemWriter<List<org.wallerlab.yoink.api.model.bootstrap.Job>> cmlFilesResponse) {
		return stepBuilderFactory.get("adaptiveQMMM").<List<File>, List<org.wallerlab.yoink.api.model.bootstrap.Job>> chunk(1)
				.reader(cmlFilesRequest)
				.processor(adaptiveQMMMProcessor)
				.writer(cmlFilesResponse)
				.build();
	}

	/**
	 * build executing steps
	 *
	 * @param stepBuilderFactory
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
	 * @param cmlFilesRequest
	 *            -{@link org.springframework.batch.item.ItemReader}
	 * @param adaptiveQMMMProcessor
	 *            -{@link org.springframework.batch.item.ItemProcessor}
	 * @param cmlFilesResponse
	 *            -{@link org.springframework.batch.item.ItemWriter}
	 * @return Step -{@link org.springframework.batch.core.Step}
	 */
	@Bean
	public Step batchStep(StepBuilderFactory stepBuilderFactory, ItemReader<Cml> cmlFilereader,
			ItemProcessor<JAXBElement, org.wallerlab.yoink.api.model.bootstrap.Job> serialAdaptiveQMMMProcessor,
			ItemWriter<org.wallerlab.yoink.api.model.bootstrap.Job> cmlFileResponseWriter) {
		return stepBuilderFactory
				.get("adaptiveQMMMBatch").<JAXBElement, org.wallerlab.yoink.api.model.bootstrap.Job> chunk(1)
				.reader(cmlFilesReader())
				.processor(serialAdaptiveQMMMProcessor)
				.writer(cmlFileResponseWriter).build();
	}

	/**
	 * Standard Spring Batch bean 
	 * 
	 * @return a bean to read all xml files within a given folder
	 */
	@Bean
	MultiResourceItemReader cmlFilesReader() {
		MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
		try {
			multiResourceItemReader.setResources((Resource[]) appContext.getResources("file:./inputs/*.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		multiResourceItemReader.setDelegate((ResourceAwareItemReaderItemStream) cmlFilereader());
		return multiResourceItemReader;
	}

	/**
	 *  Standard Spring Batch item reader for OXM (Object Unmarshalling)
	 *  
	 * @return Item Reader bean -{@link org.springframework.batch.item.xml.StaxEventItemReader<T>}
	 */
	@Bean
	StaxEventItemReader<JAXBElement> cmlFilereader() {
		StaxEventItemReader reader = new StaxEventItemReader();
		reader.setUnmarshaller(unmarshaller());
		reader.setFragmentRootElementName("cml");
		return reader;
	}

	/**
	 *  Standard Spring Batch item reader for JAXB
	 *  
	 * @return  JAXB reader bean -{@link org.springframework.oxm.jaxb.Jaxb2Marshaller}
	 */
	@Bean
	org.springframework.oxm.Unmarshaller unmarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Cml.class);
		return (Unmarshaller) marshaller;
	}

	/**
	 * build executing steps
	 *
	 * @param stepBuilderFactory
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
	 * @param cmlFilesRequest
	 *            -{@link org.springframework.batch.item.ItemReader}
	 * @param adaptiveQMMMProcessor
	 *            -{@link org.springframework.batch.item.ItemProcessor}
	 * @param cmlFilesResponse
	 *            -{@link org.springframework.batch.item.ItemWriter}
	 * @return Step -{@link org.springframework.batch.core.Step}
	 */
	@Bean
	public Step jmsStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("adaptiveQMMMJms").<String, org.wallerlab.yoink.api.model.bootstrap.Job> chunk(1)
				.reader(jmsRequestReader())
				.processor((ItemProcessor<String, org.wallerlab.yoink.api.model.bootstrap.Job>) appContext
						.getBean("stringAdaptiveQMMMProcessor"))
				.writer(jmsJobItemWriter()).build();
	}

	/**
	 * This is a bean that wraps around the standard jmsItemReader. It converts
	 * it to a long running service. I.e. it does not return null, because this 
	 * would terminate the Spring Batch job. Instead, if no message is received 
	 * it goes to sleep for some time and tries again later.
	 * 
	 * @return a self made bean that wraps a standard JMS bean 
	 * 			-{@line org.wallerlab.yoink.service.request.JmsRequestReader}
     *
	 */
	@Bean
	ItemReader<String> jmsRequestReader() {
		JmsRequestReader jmsRequestReader = new JmsRequestReader();
		jmsRequestReader.setJmsItemReader(jmsItemReader());
		return jmsRequestReader;
	}

	/**
	 * Standard connection factory
	 *   -{@link org.apache.activemq.spring.ActiveMQConnectionFactory}
     *
	 * 
	 * Broker URL is where the ActiveMQ is running. 
	 * 
	 * The port used to be 61620 or so, now it is 61616 - careful.
	 * 
	 * @return
	 */
	@Bean
	ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		return connectionFactory;
	}

	/**
	 * Standard Spring Batch item reader for jms.
	 * @return -{@link org.springframework.batch.item.jms.JmsItemReader<T>}
	 */
	@Bean
	ItemReader<String> jmsItemReader() {
		JmsItemReader<String> jmsItemReader = new JmsItemReader<String>();
		jmsItemReader.setJmsTemplate(jmsRequestTemplate());
		return jmsItemReader;
	}

	/**
	 * Standard JMS template for sending request.
	 * @return -{@link org.springframework.jms.core.JmsTemplate}
	 */	
	@Bean
	JmsOperations jmsRequestTemplate() {
		JmsTemplate jmsRequestTemplate = new JmsTemplate(connectionFactory());
		jmsRequestTemplate.setDefaultDestinationName("yoink-request");
		jmsRequestTemplate.setReceiveTimeout(2000l);
		return jmsRequestTemplate;
	}

	/**
	 * This bean delegates to a standard JMS item writer from Spring Batch,
	 * after it has converted the job to a string containing the xml output.
	 * @return a bean to write back to a jms queue
	 */
	@Bean
	ItemWriter<org.wallerlab.yoink.api.model.bootstrap.Job> jmsJobItemWriter() {
		ItemWriter jmsJobItemWriter = new JmsJobItemWriter();
		return jmsJobItemWriter;
	}

	/**
	 * Standard Spring Batch item writer for jms.
	 * @return -{@link org.springframework.batch.item.jms.JmsItemWriter<T>}
	 */
	@Bean
	ItemWriter<String> jmsItemWriter() {
		JmsItemWriter<String> jmsItemWriter = new JmsItemWriter<String>();
		jmsItemWriter.setJmsTemplate(jmsResponseTemplate());
		return jmsItemWriter;
	}

	/**
	 * Standard JMS template, used to respond.
	 * @return -{@link org.springframework.jms.core.JmsTemplate}
	 */	
	@Bean
	JmsOperations jmsResponseTemplate() {
		JmsTemplate jmsResponseTemplate = new JmsTemplate(connectionFactory());
		jmsResponseTemplate.setDefaultDestinationName("yoink-response");
		jmsResponseTemplate.setReceiveTimeout(2000l);
		return jmsResponseTemplate;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
