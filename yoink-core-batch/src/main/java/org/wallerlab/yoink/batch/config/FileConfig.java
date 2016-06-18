package org.wallerlab.yoink.batch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.wallerlab.yoink.batch.api.model.bootstrap.Job;
import org.xml_cml.schema.Cml;

import javax.xml.bind.JAXBElement;
import java.io.IOException;


@Profile("batch")
@Configuration
public class FileConfig extends BatchConfig{

    @Autowired
    @Qualifier("batchStep")
    private Step batchStep;




    /**
     * Standard Spring Batch bean
     *
     * @return a bean to read all xml files within a given folder
     */
    @Bean
    MultiResourceItemReader cmlFilesReader() {
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        try {
            //TODO remove appContext refernece
            multiResourceItemReader.setResources((Resource[]) appContext.getResources("file:./inputs/*.xml"));
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