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
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.wallerlab.yoink.adaptive.config.AdaptiveConfig;
import org.wallerlab.yoink.density.config.DensityConfig;
import org.wallerlab.yoink.math.config.MathConfig;
import org.wallerlab.yoink.molecular.config.MolecularConfig;
import org.wallerlab.yoink.regionizer.config.RegionizerConfig;
import org.xml_cml.schema.Cml;



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
@ComponentScan("org.wallerlab.yoink")
@Import({AdaptiveConfig.class,
        MathConfig.class,
        RegionizerConfig.class,
        MolecularConfig.class,
        DensityConfig.class})
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
	 *            - jobs
	 *            {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
	 *
	 * @return Job - job {@link org.springframework.batch.core.Job}
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
	 * @param cmlFilereader
	 *            -{@link org.springframework.batch.item.ItemReader}
	 * @param serialAdaptiveQMMMProcessor
	 *            -{@link org.springframework.batch.item.ItemProcessor}
	 * @param cmlFileResponseWriter
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
	

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
 
    public void setApplicationContext(ApplicationContext applicationContext)
                        throws BeansException {
                this.appContext = applicationContext;
        }

}
