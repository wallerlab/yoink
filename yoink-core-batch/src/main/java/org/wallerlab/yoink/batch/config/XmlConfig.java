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
package org.wallerlab.yoink.batch.config;

import javax.xml.bind.JAXBElement;
import java.io.IOException;

import org.cml_v3.generated.ObjectFactory;
import org.xml_cml.schema.Cml;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.xml.StaxEventItemReader;

//@Profile("batch")
@Configuration
public class XmlConfig {

    private PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

    /**
     * Standard Spring Batch bean
     *
     * @return a bean to read all xml files within a given folder
     */
    @Bean
    MultiResourceItemReader itemReader() {
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        try {
            multiResourceItemReader.setResources(resolver.getResources("file:/Users/waller/merge/yoink/inputs/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        multiResourceItemReader.setDelegate((ResourceAwareItemReaderItemStream) cmlFileReader());
        return multiResourceItemReader;
    }

    /**
     *  Standard Spring Batch item reader for OXM (Object Unmarshalling)
     *
     * @return Item Reader bean -{@link org.springframework.batch.item.xml.StaxEventItemReader<T>}
     */
    @Bean
    StaxEventItemReader<JAXBElement> cmlFileReader() {
        StaxEventItemReader reader = new StaxEventItemReader();
        reader.setUnmarshaller(unmarshaller());
        reader.setFragmentRootElementName("cml");
        return reader;
    }

    /**
     *  Standard Spring Batch item reader for JAXB
     *
     * @return  JAXB reader bean -{@link org.springframework.oxm.jaxb.Jaxb2Marshaller}
     */
    @Bean
    org.springframework.oxm.Unmarshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Cml.class);
        return marshaller;
    }

}