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
package org.wallerlab.yoink.adaptive.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wallerlab.yoink.adaptive.smooth.DensitySmoothner;
import org.wallerlab.yoink.adaptive.smooth.DistanceSmoothner;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptive.SmoothFunction;

/**
 * this class is for adaptive project configuration
 * 
 * @author Min Zheng
 *
 */
@Configuration
public class AdaptiveConfig implements ApplicationContextAware {

	private ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
	}

	@Bean
	public Smoothner densitySmoothnerBF() {
		Smoothner densityBufferedForceSmoothner = new DensitySmoothner(
				(SmoothFunction) appContext.getBean("brooksSmoothFunction"));
		return densityBufferedForceSmoothner;
	}

	@Bean
	public Smoothner distanceSmoothnerBF() {
		Smoothner distanceBufferedForceSmoother = new DistanceSmoothner(
				(SmoothFunction) appContext.getBean("brooksSmoothFunction"));
		return distanceBufferedForceSmoother;
	}

	

	@Bean
	public Smoothner densitySmoothnerSPOT() {
		Smoothner densityBufferedForceSmoothner = new DensitySmoothner(
				(SmoothFunction) appContext.getBean("brooksSmoothFunction"));
		return densityBufferedForceSmoothner;
	}

	@Bean
	public Smoothner distanceSmoothnerSPOT() {
		Smoothner distanceBufferedForceSmoother = new DistanceSmoothner(
				(SmoothFunction) appContext.getBean("brooksSmoothFunction"));
		return distanceBufferedForceSmoother;
	}

	
	@Bean
	public Smoothner densitySmoothnerDAS() {

		Smoothner densitySmoothnerDAS = new DensitySmoothner(
				(SmoothFunction) appContext.getBean("buloSmoothFunction"));

		return densitySmoothnerDAS;
	}

	@Bean
	public Smoothner distanceSmoothnerDAS() {
		Smoothner distanceSmoothnerDAS = new DistanceSmoothner(
				(SmoothFunction) appContext.getBean("buloSmoothFunction"));
		return distanceSmoothnerDAS;
	}

	
	@Bean
	public Smoothner densitySmoothnerXS() {

		Smoothner densitySmoothnerXS = new DensitySmoothner(
				(SmoothFunction) appContext.getBean("morokumaSmoothFunction"));

		return densitySmoothnerXS;
	}

	@Bean
	public Smoothner distanceSmoothnerXS() {
		Smoothner distanceSmoothnerXS = new DistanceSmoothner(
				(SmoothFunction) appContext.getBean("morokumaSmoothFunction"));
		return distanceSmoothnerXS;
		
	}
	
	@Bean
	public Smoothner densitySmoothnerPAP() {

		Smoothner densitySmoothnerPAP = new DensitySmoothner(
				(SmoothFunction) appContext.getBean("permutedSmoothFunction"));

		return densitySmoothnerPAP;
	}

	@Bean
	public Smoothner distanceSmoothnerPAP() {
		Smoothner distanceSmoothnerPAP = new DistanceSmoothner(
				(SmoothFunction) appContext.getBean("permutedSmoothFunction"));
		return distanceSmoothnerPAP;
		
	}
	
}
