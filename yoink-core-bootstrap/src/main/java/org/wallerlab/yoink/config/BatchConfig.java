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

/**
 * this class is configuration for spring batch
 *
 * @author Min Zheng
 */
@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class BatchConfig {
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired 
	@Qualifier("serviceStep")
	private Step serviceStep;
	
	@Autowired 
	@Qualifier("batchStep")
	private Step batchStep;
	
	@Autowired 
	@Qualifier("jmsStep")
	private Step jmsStep;
	
    /**
     * build whole job using a service based job
     *
     * @param jobs -
     *             {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
     * @param s1   -{@link org.springframework.batch.core.Step}
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
    
    /**
     * build whole job using a batch based approach.
     *
     * @param jobs -
     *             {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
     * @param s1   -{@link org.springframework.batch.core.Step}
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
    
    /**
     * build whole job using a batch based approach.
     *
     * @param jobs -
     *             {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
     * @param s1   -{@link org.springframework.batch.core.Step}
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
     * @param stepBuilderFactory    -
     *                              {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
     * @param cmlFilesRequest       -{@link org.springframework.batch.item.ItemReader}
     * @param adaptiveQMMMProcessor -{@link org.springframework.batch.item.ItemProcessor}
     * @param cmlFilesResponse      -{@link org.springframework.batch.item.ItemWriter}
     * @return Step -{@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step serviceStep(
            StepBuilderFactory stepBuilderFactory,
            ItemReader<List<File>> cmlFilesRequest,
            ItemProcessor<List<File>, List<org.wallerlab.yoink.api.model.bootstrap.Job>> adaptiveQMMMProcessor,
            ItemWriter<List<org.wallerlab.yoink.api.model.bootstrap.Job>> cmlFilesResponse) {
        return stepBuilderFactory
                .get("adaptiveQMMM")
                .<List<File>, List<org.wallerlab.yoink.api.model.bootstrap.Job>>chunk(1)
                .reader(cmlFilesRequest)
                .processor(adaptiveQMMMProcessor)
                .writer(cmlFilesResponse)
                .build();
    }
    
    
    /**
     * build executing steps
     *
     * @param stepBuilderFactory    -
     *                              {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
     * @param cmlFilesRequest       -{@link org.springframework.batch.item.ItemReader}
     * @param adaptiveQMMMProcessor -{@link org.springframework.batch.item.ItemProcessor}
     * @param cmlFilesResponse      -{@link org.springframework.batch.item.ItemWriter}
     * @return Step -{@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step batchStep(
            StepBuilderFactory stepBuilderFactory,
            ItemReader<Cml> cmlFilereader,
            ItemProcessor<JAXBElement, org.wallerlab.yoink.api.model.bootstrap.Job> serialAdaptiveQMMMProcessor,
            ItemWriter<org.wallerlab.yoink.api.model.bootstrap.Job> cmlFileResponseWriter) {
        return stepBuilderFactory
                .get("adaptiveQMMMBatch")
                .<javax.xml.bind.JAXBElement,org.wallerlab.yoink.api.model.bootstrap.Job>chunk(1)
                .reader(cmlFilesReader())
                .processor(serialAdaptiveQMMMProcessor)
                .writer(cmlFileResponseWriter)
                .build();
    }
    
    @Bean 
    MultiResourceItemReader cmlFilesReader(){
    	MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
    	try {
			multiResourceItemReader.setResources((Resource[]) appContext.getResources("file:./inputs/*.xml"));		
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	multiResourceItemReader.setDelegate( (ResourceAwareItemReaderItemStream) cmlFilereader());
    	return multiResourceItemReader;
    }
    
    
    @Bean
	StaxEventItemReader<Cml> cmlFilereader() {
		StaxEventItemReader reader = new StaxEventItemReader();
		reader.setUnmarshaller(unmarshaller());
		reader.setFragmentRootElementName("cml");
		return reader;
	}

	@Bean
	org.springframework.oxm.Unmarshaller unmarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Cml.class);
		return (Unmarshaller) marshaller;
	}
	
	
	 /**
     * build executing steps
     *
     * @param stepBuilderFactory    -
     *                              {@link org.springframework.batch.core.configuration.annotation.StepBuilderFactory}
     * @param cmlFilesRequest       -{@link org.springframework.batch.item.ItemReader}
     * @param adaptiveQMMMProcessor -{@link org.springframework.batch.item.ItemProcessor}
     * @param cmlFilesResponse      -{@link org.springframework.batch.item.ItemWriter}
     * @return Step -{@link org.springframework.batch.core.Step}
     */
    @Bean
    public Step jmsStep( StepBuilderFactory stepBuilderFactory ) {
        return stepBuilderFactory
                .get("adaptiveQMMMJms")
                .<String,org.wallerlab.yoink.api.model.bootstrap.Job>chunk(1)
                .reader(jmsRequestReader())
                .processor((ItemProcessor<String, org.wallerlab.yoink.api.model.bootstrap.Job>) appContext.getBean("stringAdaptiveQMMMProcessor"))
                .writer(jmsJobItemWriter())
                .build();
    }
    
    
    /**
     * This is a bean that wraps around the standard jmsItemReader.
     * It converts it to a long running service.
     * 
     * @return
     */
    @Bean
    ItemReader<String> jmsRequestReader(){
    	JmsRequestReader jmsRequestReader = new JmsRequestReader();
    	jmsRequestReader.setJmsItemReader(jmsItemReader());
    	return jmsRequestReader;
    }
   
    @Bean
    ConnectionFactory connectionFactory(){
    	ActiveMQConnectionFactory connectionFactory  = new ActiveMQConnectionFactory();
    	connectionFactory.setBrokerURL("tcp://localhost:61616"); 	
    	return connectionFactory;
    }
    
    @Bean
    ItemReader<String> jmsItemReader(){
    	JmsItemReader<String> jmsItemReader = new JmsItemReader<String>();
    	jmsItemReader.setJmsTemplate(jmsRequestTemplate());
    	return jmsItemReader;
    }
    

    @Bean
    JmsOperations jmsRequestTemplate(){
    	JmsTemplate jmsRequestTemplate = new JmsTemplate(connectionFactory());
    	jmsRequestTemplate.setDefaultDestinationName("yoink-request");
    	jmsRequestTemplate.setReceiveTimeout(2000l);
    	return jmsRequestTemplate;
    }
	
    @Bean
    ItemWriter<org.wallerlab.yoink.api.model.bootstrap.Job> jmsJobItemWriter(){
    	ItemWriter jmsJobItemWriter = new JmsJobItemWriter();
    	return jmsJobItemWriter;
    }
	
    @Bean
    ItemWriter<String> jmsItemWriter(){
    	JmsItemWriter<String> jmsItemWriter = new JmsItemWriter<String>();
    	jmsItemWriter.setJmsTemplate(jmsResponseTemplate());
    	return jmsItemWriter;
    }
    
    @Bean
    JmsOperations jmsResponseTemplate(){
    	JmsTemplate jmsResponseTemplate = new JmsTemplate(connectionFactory());
    	jmsResponseTemplate.setDefaultDestinationName("yoink-response");
    	jmsResponseTemplate.setReceiveTimeout(2000l);
    	return jmsResponseTemplate;
    }

}
