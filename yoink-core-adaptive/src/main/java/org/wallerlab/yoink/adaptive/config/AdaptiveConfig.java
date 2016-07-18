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
import org.wallerlab.yoink.adaptive.services.Adaptive;
import org.wallerlab.yoink.adaptive.services.AdaptiveProcessor;
import org.wallerlab.yoink.adaptive.services.factors.Lambdas;
import org.wallerlab.yoink.adaptive.services.SmoothFunctions;
import org.wallerlab.yoink.adaptive.services.factors.Processor;
import org.wallerlab.yoink.adaptive.services.factors.Processors;
import org.wallerlab.yoink.adaptive.services.WeightFactors;
import org.wallerlab.yoink.adaptive.services.functions.SmoothFunction;

import java.util.EnumMap;
import java.util.Map;

import static org.wallerlab.yoink.adaptive.services.factors.Processor.NAME.*;
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
    Adaptive bf(){
      return new Adaptive.Builder()
                          .processor(use(BF))
                          .build();
    }

    @Bean
    Adaptive fires(){
        return new Adaptive.Builder()
                            .processor(use(Processor.NAME.FIRES))
                            .build();
    }

    @Bean
    Adaptive hotSpot(){
        return new Adaptive.Builder()
                            .processor(use(HOT_SPOT))
                            .factors(use(DISTANCE_OR_DENSITY))
                            .functions(use(BROOKS))
                            .build();
    }

    @Bean
    Adaptive xs(){
        return new Adaptive.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(XS))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Adaptive scmp(){
        return new Adaptive.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(WeightFactors.NAME.SCMP))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Adaptive das(){
        return new Adaptive.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(DAS))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Adaptive sap(){
        return new Adaptive.Builder()
                .processor(use(CONFIG))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(SAP))
                .functions(use(BROOKS))
                .build();
    }

    @Bean
    Adaptive pap(){
        return new Adaptive.Builder()
                .processor(use(PAP))
                .factors(use(DISTANCE_OR_DENSITY))
                .weights(use(SAP))
                .functions(use(BROOKS))
                .build();
    }


    @Bean
    AdaptiveProcessor smoothnerService(){
        Map<Adaptive.NAME, Adaptive> processors = new EnumMap<>(Processor.NAME.class);
        processors.put(Adaptive.NAME.BF, bf());
        processors.put(Adaptive.NAME.FIRES,fires());
        processors.put(Adaptive.NAME.HOT_SPOT,hotSpot());
        processors.put(Adaptive.NAME.XS,xs());
        processors.put(Adaptive.NAME.SCMP, scmp());
        processors.put(Adaptive.NAME.PAP,pap());
        processors.put(Adaptive.NAME.SAP,sap());
        processors.put(Adaptive.NAME.DAS,das());
        return new AdaptiveProcessor(processors);
    }


    @Autowired Processors factors;
    private Processors use(Processor.NAME name){ return this.factors.use(name);}

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
