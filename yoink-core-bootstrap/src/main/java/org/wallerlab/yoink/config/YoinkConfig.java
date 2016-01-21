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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.wallerlab.yoink.adaptive.config.AdaptiveConfig;
import org.wallerlab.yoink.density.config.DensityConfig;
import org.wallerlab.yoink.math.config.MathConfig;
import org.wallerlab.yoink.molecular.config.MolecularConfig;
import org.wallerlab.yoink.regionizer.config.RegionizerConfig;

/**
 * Configuration class for project yoink-core-bootstrap
 * 
 * @author Min Zheng
 *
 */
@Configuration
@ComponentScan("org.wallerlab.yoink")
@Import({ AdaptiveConfig.class, MathConfig.class, RegionizerConfig.class,
		MolecularConfig.class, DensityConfig.class })
public class YoinkConfig implements ApplicationContextAware {

	private ApplicationContext appContext;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
	}

}
