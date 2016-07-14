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

import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.region.service.RegionizerService;
import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;
import org.wallerlab.yoink.region.service.partitioners.Partitioner;

import java.util.Map;
import java.util.EnumMap;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This class is the configuration for region project.
 * 
 * @author Min Zheng
 *
 */
@Configuration
@PropertySource("classpath:region.properties")
public class RegionConfig {

    @Resource Partitioner distancePartitioner;
    @Resource Partitioner numberPartitioner;
    @Resource Partitioner densityPartitioner;

    @Bean
    Regionizer regionizer(){
       Map<Regionizer.Type,Partitioner> partitioners =
                new EnumMap<Regionizer.Type,Partitioner>(Regionizer.Type.class);
       partitioners.put(DISTANCE,distancePartitioner);
       partitioners.put(NUMBER,densityPartitioner);
       partitioners.put(DENSITY,densityPartitioner);
       Regionizer regionizerService = new RegionizerService(partitioners);
       return regionizerService;
    }

}
