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
package org.wallerlab.yoink;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.wallerlab.yoink.adaptive.config.AdaptiveConfig;
import org.wallerlab.yoink.density.config.DensityConfig;
import org.wallerlab.yoink.math.config.MathConfig;
import org.wallerlab.yoink.molecular.config.MolecularConfig;
import org.wallerlab.yoink.regionizer.config.RegionizerConfig;

/**
 * This class is the main class of Yoink
 *
 * @author Min Zheng
 */
@Configuration
@EnableAutoConfiguration
@Import({AdaptiveConfig.class,
        MathConfig.class,
        RegionizerConfig.class,
        MolecularConfig.class,
        DensityConfig.class})
@ComponentScan("org.wallerlab.yoink")
public class Yoink {

    /**
     * use Spring boot to start Yoink application
     *
     * @param args - an array of strings
     * @throws IOException          -{@link java.io.IOException}
     * @throws InterruptedException -{@link java.lang.InterruptedException}
     */
    public static void main(String[] args) throws IOException,
            InterruptedException {
        ApplicationContext ctx = SpringApplication.run(Yoink.class, args);
    }
}
