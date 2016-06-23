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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.api.service.region.RegionizerComponent;
import org.wallerlab.yoink.api.service.region.RegionizerMath;
import org.wallerlab.yoink.region.service.regionizer.density.components.AdaptiveRegionizer;
import org.wallerlab.yoink.region.service.regionizer.utils.RegionizerUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

import static org.wallerlab.yoink.api.model.density.DensityPoint.DensityType.DORI;
import static org.wallerlab.yoink.api.model.density.DensityPoint.DensityType.SEDD;
import static org.wallerlab.yoink.api.model.region.Region.Name.*;

/**
 * This class is the configuration for region project.
 * 
 * @author Min Zheng
 *
 */
@Configuration
@PropertySource("classpath:region.properties")
public class RegionConfig {

	@Resource
	@Qualifier("doriPartitioner")
	private Partitioner doriPartitioner;

	@Resource
	@Qualifier("seddPartitioner")
	private Partitioner seddPartitioner;

	/**
	 * region math service before adaptive partitioning
	 * 
	 * @return regionizerService
	 *         {@link RegionizerUtil}
	 */
	@Bean
	RegionizerMath<Map<Region.Name, Region>, MolecularSystem> preRegionizer() {
		RegionizerUtil regionizerService = new RegionizerUtil();
		Region.Name[] regionReqeusts = {QM_CORE_FIXED,SYSTEM,QM_CORE,QM,NONQM_CORE};
		regionizerService.setRegionNames(Arrays.asList(regionReqeusts));
		return (RegionizerMath<Map<Region.Name, Region>, MolecularSystem>) regionizerService;
	}

	/**
	 * Adaptive QM core region in density based adaptive partitioning
	 *
	 * @return adaptiveQMCoreRegionizer
	 *         {@link Regionizer}
	 */
	@Bean
	RegionizerComponent<Map<Region.Name, Region>, Map<String, Object>> adaptiveQMCoreRegionizer() {
		return (RegionizerComponent) new AdaptiveRegionizer(SEDD, seddPartitioner);
	}

	/**
	 *  Adaptive QM region in density based adaptive partitioning
	 * 
	 * @return adaptiveQMRegionizer
	 *         {@link Regionizer}
	 */
	@Bean
	RegionizerComponent<Map<Region.Name, Region>, Map<String, Object>> adaptiveQMRegionizer() {
		return (RegionizerComponent) new AdaptiveRegionizer(DORI, doriPartitioner);
	}


	/**
	 * region math service after adaptive partitioning
	 *
	 * @return regionizerService
	 *         {@link RegionizerUtil}
	 */
	@Bean
	RegionizerMath<Map<Region.Name, Region>, MolecularSystem> postRegionizer() {
		RegionizerUtil regionizerService = new RegionizerUtil();
		Region.Name[] regionReqeusts = {MM,MM_NONBUFFER};
		regionizerService.setRegionNames(Arrays.asList(regionReqeusts));
		return (RegionizerMath<Map<Region.Name, Region>, MolecularSystem>) regionizerService;
	}
}
