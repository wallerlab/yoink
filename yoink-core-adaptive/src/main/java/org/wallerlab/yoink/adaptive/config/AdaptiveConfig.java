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

import javax.annotation.Resource;
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
public class AdaptiveConfig {

	@Resource
	SmoothFunction brooksSmoothFunction;
	@Resource
	SmoothFunction buloSmoothFunction;
	@Resource
	SmoothFunction morokumaSmoothFunction;
	@Resource
	SmoothFunction permutedSmoothFunction;

	@Bean
	public Smoothner densitySmoothnerBF() {
		return new DensitySmoothner(brooksSmoothFunction);
	}

	@Bean
	public Smoothner distanceSmoothnerBF() {
		return new DistanceSmoothner(brooksSmoothFunction);
	}

	@Bean
	public Smoothner densitySmoothnerSPOT() {
		return new DensitySmoothner(brooksSmoothFunction);
	}

	@Bean
	public Smoothner distanceSmoothnerSPOT() {
		return new DistanceSmoothner(brooksSmoothFunction);
	}

	@Bean
	public Smoothner densitySmoothnerDAS() {
		return new DensitySmoothner(buloSmoothFunction);
	}

	@Bean
	public Smoothner distanceSmoothnerDAS() {
		return new DistanceSmoothner(buloSmoothFunction);
	}

	@Bean
	public Smoothner densitySmoothnerXS() {
		return new DensitySmoothner(morokumaSmoothFunction);
	}

	@Bean
	public Smoothner distanceSmoothnerXS() {
		return new DistanceSmoothner(morokumaSmoothFunction);
	}

	@Bean
	public Smoothner densitySmoothnerPAP() {
		return new DensitySmoothner(permutedSmoothFunction);
	}

	@Bean
	public Smoothner distanceSmoothnerPAP() {
		return new DistanceSmoothner(permutedSmoothFunction);
	}

}
