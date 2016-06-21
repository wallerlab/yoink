package org.wallerlab.yoink.batch.config;

import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml_cml.schema.Cml;

import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class XmlConfig extends BatchConfig{

    /**
     * Standard Spring Batch bean
     *
     * @return a bean to read all xml files within a given folder
     */
    @Bean
    MultiResourceItemReader cmlFilesReader() {
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        try {
            multiResourceItemReader.setResources((Resource[]) appContext.getResources("classpath:inputs/*.xml"));
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
        return (Unmarshaller) marshaller;
    }

}