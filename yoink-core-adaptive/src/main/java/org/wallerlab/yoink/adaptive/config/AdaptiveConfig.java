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
 *//*

package org.wallerlab.yoink.adaptive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wallerlab.yoink.adaptive.services.Smoothner;
import org.wallerlab.yoink.adaptive.services.AdaptiveProcessor;
import org.wallerlab.yoink.adaptive.services.factors.Lambdas;
import org.wallerlab.yoink.adaptive.services.SmoothFunctions;
import org.wallerlab.yoink.adaptive.services.processors.Processor;
import org.wallerlab.yoink.adaptive.services.processors.Processors;
import org.wallerlab.yoink.adaptive.services.WeightFactors;
import org.wallerlab.yoink.adaptive.services.functions.SmoothFunction;

import java.util.EnumMap;
import java.util.Map;

import static org.wallerlab.yoink.adaptive.services.processors.Processor.NAME.*;
import static org.wallerlab.yoink.adaptive.services.factors.Lambdas.NAME.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.NAME.*;
import static org.wallerlab.yoink.adaptive.services.WeightFactors.NAME.*;

*/
/**
 * this class is for adaptive project configuration
 * 
 * @author Min Zheng
 *
 *//*

@Configuration
public class AdaptiveConfig {

    @Bean
    Smoothner bf(){
      return new Smoothner.Builder()
                          .processor(use(BF))
                          .build();
    }

    @Bean
    Smoothner fires(){
        return new Smoothner.Builder()
                            .processor(use(Processor.NAME.FIRES))
                            .build();
    }

    @Bean
    Smoothner hotSpot(){
        return new Smoothner.Builder()
                            .processor(use(HOT_SPOT))
                            .factors(use(DISTANCE_OR_DENSITY))
                            .functions(use(BROOKS))
                            .build();
    }

    @Bean
    Smoothner xs(){
        return new Smoothner.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(XS))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Smoothner scmp(){
        return new Smoothner.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(WeightFactors.NAME.SCMP))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Smoothner das(){
        return new Smoothner.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(DAS))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Smoothner sap(){
        return new Smoothner.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(SAP))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Smoothner pap(){
        return new Smoothner.Builder()
                .processor(use(PAP))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(SAP))
                .functions(use(BROOKS))
                .build();
    }


    @Bean
    AdaptiveProcessor smoothnerService(){
        Map<Smoothner.NAME, Smoothner> smoothners = new EnumMap<>(Processor.NAME.class);
        smoothners.put(Smoothner.NAME.BF, bf());
        smoothners.put(Smoothner.NAME.FIRES,fires());
        smoothners.put(Smoothner.NAME.HOT_SPOT,hotSpot());
        smoothners.put(Smoothner.NAME.XS,xs());
        smoothners.put(Smoothner.NAME.SCMP, scmp());
        smoothners.put(Smoothner.NAME.PAP,pap());
        smoothners.put(Smoothner.NAME.SAP,sap());
        smoothners.put(Smoothner.NAME.DAS,das());
        return new AdaptiveProcessor(smoothners);
    }


    @Autowired Processors processors;
    private Processors use(Processor.NAME name){ return this.processors.use(name);}

    @Autowired
    Lambdas factors;
    private Lambdas use(Lambdas.NAME name){ return this.factors.use(name);}

    @Autowired
    WeightFactors weights;
    private WeightFactors use(WeightFactors.NAME name){ return this.factors.use(name);}

    @Autowired
    SmoothFunctions functions;
    private SmoothFunction use(SmoothFunctions.NAME name){ return this.functions.use(name);}


}
*/
