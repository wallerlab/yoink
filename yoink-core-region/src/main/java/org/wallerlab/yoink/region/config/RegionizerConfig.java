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
package org.wallerlab.yoink.region.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wallerlab.yoink.batch.api.model.density.DensityPoint.DensityType;
import org.wallerlab.yoink.batch.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.batch.api.model.regionizer.Region;
import org.wallerlab.yoink.batch.api.service.region.Partitioner;
import org.wallerlab.yoink.batch.api.service.region.Regionizer;
import org.wallerlab.yoink.batch.api.service.region.RegionizerComponent;
import org.wallerlab.yoink.batch.api.service.region.RegionizerMath;
import org.wallerlab.yoink.region.service.regionizer.AdaptiveRegionizer;
import org.wallerlab.yoink.region.service.regionizer.RegionizerService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.wallerlab.yoink.batch.api.model.regionizer.Region.Name.*;

/**
 * This class is the configuration for region project.
 * 
 * @author Min Zheng
 *
 */
@Configuration
public class RegionizerConfig {

	@Resource
	private Partitioner densityOverlapRegionsIndicatorPartitioner;

	@Resource
	private Partitioner singleExponentialDecayDetectorPartitioner;

	/**
	 * region math service before adaptive partitioning
	 * 
	 * @return regionizerService
	 *         {@link RegionizerService}
	 */
	@Bean
	RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceStarting() {
		RegionizerService regionizerService = new RegionizerService();
		List<Region.Name> regionReqeusts = new ArrayList<Region.Name>();
		regionReqeusts.add(QM_CORE_FIXED);
		regionReqeusts.add(SYSTEM);
		regionReqeusts.add(QM_CORE);
		regionReqeusts.add(QM);
		regionReqeusts.add(NONQM_CORE);
		regionizerService.setRegionNames(regionReqeusts);
		return (RegionizerMath<Map<Region.Name, Region>, MolecularSystem>) regionizerService;
	}

	/**
	 * region math service after adaptive partitioning
	 * 
	 * @return regionizerService
	 *         {@link RegionizerService}
	 */
	@Bean
	RegionizerMath<Map<Region.Name, Region>, MolecularSystem> regionizerServiceEnding() {
		RegionizerService regionizerService = new RegionizerService();
		List<Region.Name> regionReqeusts = new ArrayList<Region.Name>();
		regionReqeusts.add(MM);
		regionReqeusts.add(MM_NONBUFFER);
		regionizerService.setRegionNames(regionReqeusts);
		return (RegionizerMath<Map<Region.Name, Region>, MolecularSystem>) regionizerService;
	}

	/**
	 * region for adaptive QM in density based adaptive partitioning
	 * 
	 * @return adaptiveQMRegionizer
	 *         {@link Regionizer}
	 */
	@Bean
	RegionizerComponent<Map<Region.Name, Region>, Map<String, Object>> adaptiveQMRegionizer() {
		Partitioner doriPartitioner = densityOverlapRegionsIndicatorPartitioner;
		RegionizerComponent adaptiveQMRegionizer = new AdaptiveRegionizer(
				DensityType.DORI, doriPartitioner);
		return adaptiveQMRegionizer;
	}

	/**
	 * region for adaptive QM core in density based adaptive partitioning
	 * 
	 * @return adaptiveQMCoreRegionizer
	 *         {@link Regionizer}
	 */
	@Bean
	RegionizerComponent<Map<Region.Name, Region>, Map<String, Object>> adaptiveQMCoreRegionizer() {
		Partitioner seddPartitioner =singleExponentialDecayDetectorPartitioner;
		RegionizerComponent adaptiveQMCoreRegionizer = new AdaptiveRegionizer(
				DensityType.SEDD, seddPartitioner);
		return adaptiveQMCoreRegionizer;
	}
	
}
