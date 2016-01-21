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
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * this class is configuration for spring batch
 * 
 * @author Min Zheng
 *
 */
@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class BatchConfig {

	/**
	 * build whole job
	 * 
	 * @param jobs
	 *            -
	 *            {@link org.springframework.batch.core.configuration.annotation.JobBuilderFactory}
	 * @param s1
	 *            -{@link org.springframework.batch.core.Step}
	 * @return Job -{@link org.springframework.batch.core.Job}
	 */
	@Bean
	public org.springframework.batch.core.Job importJob(JobBuilderFactory jobs,
			Step s1) {
		return jobs.get("importYoinkJob").incrementer(new RunIdIncrementer())
				.flow(s1).end().build();
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
	public Step step1(
			StepBuilderFactory stepBuilderFactory,
			ItemReader<List<File>> cmlFilesRequest,
			ItemProcessor<List<File>, List<org.wallerlab.yoink.api.model.bootstrap.Job>> adaptiveQMMMProcessor,
			ItemWriter<List<org.wallerlab.yoink.api.model.bootstrap.Job>> cmlFilesResponse) {
		return stepBuilderFactory
				.get("adaptiveQMMM")
				.<List<File>, List<org.wallerlab.yoink.api.model.bootstrap.Job>> chunk(
						1).reader(cmlFilesRequest)
				.processor(adaptiveQMMMProcessor).writer(cmlFilesResponse)
				.build();
	}

}
