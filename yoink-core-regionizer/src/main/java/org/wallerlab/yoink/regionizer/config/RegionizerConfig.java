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
package org.wallerlab.yoink.regionizer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.model.regionizer.Region.Name;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.api.service.regionizer.RegionizerComponent;
import org.wallerlab.yoink.api.service.regionizer.RegionizerMath;
import org.wallerlab.yoink.regionizer.service.AdaptiveRegionizer;
import org.wallerlab.yoink.regionizer.service.RegionizerService;

/**
 * This class is the configuration for regionizer project.
 * 
 * @author Min Zheng
 *
 */
@Configuration
@ComponentScan("org.wallerlab.yoink")
public class RegionizerConfig {

@Resource
private Partitioner densityOverlapRegionsIndicatorPartitioner;

@Resource
private Partitioner singleExponentialDecayDetectorPartitioner;

	/**
	 * regionizer math service before adaptive partitioning
	 * 
	 * @return regionizerService
	 *         {@link org.wallerlab.yoink.regionizer.service.RegionizerService}
	 */
	@Bean
	RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceStarting() {
		RegionizerService regionizerService = new RegionizerService();
		List<Region.Name> regionReqeusts = new ArrayList<Region.Name>();
		regionReqeusts.add(Region.Name.QM_CORE_FIXED);
		regionReqeusts.add(Region.Name.SYSTEM);
		regionReqeusts.add(Region.Name.QM_CORE);
		regionReqeusts.add(Region.Name.QM);
		regionReqeusts.add(Region.Name.NONQM_CORE);
		regionizerService.setRegionNames(regionReqeusts);
		return (RegionizerMath<Map<Region.Name, Region>, MolecularSystem>) regionizerService;
	}

	/**
	 * regionizer math service after adaptive partitioning
	 * 
	 * @return regionizerService
	 *         {@link org.wallerlab.yoink.regionizer.service.RegionizerService}
	 */
	@Bean
	RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceEnding() {
		RegionizerService regionizerService = new RegionizerService();
		List<Region.Name> regionReqeusts = new ArrayList<Region.Name>();
		regionReqeusts.add(Region.Name.MM);
		regionReqeusts.add(Region.Name.MM_NONBUFFER);
		regionizerService.setRegionNames(regionReqeusts);
		return (RegionizerMath<Map<Region.Name, Region>, MolecularSystem>) regionizerService;
	}

	/**
	 * regionizer for adaptive QM in density based adaptive partitioning
	 * 
	 * @return adaptiveQMRegionizer
	 *         {@link org.wallerlab.yoink.api.service.regionizer.Regionizer}
	 */
	@Bean
	RegionizerComponent<Map<Region.Name, Region>, Map<String, Object>> adaptiveQMRegionizer() {
		Partitioner doriPartitioner = densityOverlapRegionsIndicatorPartitioner;
		RegionizerComponent adaptiveQMRegionizer = new AdaptiveRegionizer(
				DensityType.DORI, doriPartitioner);
		return adaptiveQMRegionizer;
	}

	/**
	 * regionizer for adaptive QM core in density based adaptive partitioning
	 * 
	 * @return adaptiveQMCoreRegionizer
	 *         {@link org.wallerlab.yoink.api.service.regionizer.Regionizer}
	 */
	@Bean
	RegionizerComponent<Map<Region.Name, Region>, Map<String, Object>> adaptiveQMCoreRegionizer() {
		Partitioner seddPartitioner =singleExponentialDecayDetectorPartitioner;
		RegionizerComponent adaptiveQMCoreRegionizer = new AdaptiveRegionizer(
				DensityType.SEDD, seddPartitioner);
		return adaptiveQMCoreRegionizer;
	}
	
}
