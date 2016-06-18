package org.wallerlab.yoink.batch.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.wallerlab.yoink.batch.api.model.bootstrap.Job;
import org.wallerlab.yoink.batch.service.request.JmsRequestReader;
import org.wallerlab.yoink.batch.service.response.JmsJobItemWriter;

import javax.jms.ConnectionFactory;
import javax.xml.bind.JAXBElement;

/**
 * This is for JMS integration. It offers a service.
 */
@Profile("jms")
@Configuration
public class JmsConfig extends BatchConfig{

    @Autowired
    private StepBuilderFactory stepBuilderFactor;

    @Autowired
    JobBuilderFactory jobs;





    /**
     * This is a bean that wraps around the standard jmsItemReader. It converts
     * it to a long running service. I.e. it does not return null, because this
     * would terminate the Spring Batch job. Instead, if no message is received
     * it goes to sleep for some time and tries again later.
     *
     * @return a self made bean that wraps a standard JMS bean
     * 			-{@line JmsRequestReader}
     *
     */
    @Bean
    ItemReader<String> jmsRequestReader() {
        JmsRequestReader jmsRequestReader = new JmsRequestReader();
        jmsRequestReader.setJmsItemReader(jmsItemReader());
        return jmsRequestReader;
    }

    /**
     * Standard connection factory
     *   -{@link org.apache.activemq.spring.ActiveMQConnectionFactory}
     *
     *
     * Broker URL is where the ActiveMQ is running.
     *
     * The port used to be 61620 or so, now it is 61616 - careful.
     *
     * @return
     */
    @Bean
    ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        return connectionFactory;
    }

    /**
     * Standard Spring Batch item reader for jms.
     * @return -{@link org.springframework.batch.item.jms.JmsItemReader<T>}
     */
    @Bean
    ItemReader<String> jmsItemReader() {
        JmsItemReader<String> jmsItemReader = new JmsItemReader<String>();
        jmsItemReader.setJmsTemplate(jmsRequestTemplate());
        return jmsItemReader;
    }

    /**
     * Standard JMS template for sending request.
     * @return -{@link org.springframework.jms.core.JmsTemplate}
     */
    @Bean
    JmsOperations jmsRequestTemplate() {
        JmsTemplate jmsRequestTemplate = new JmsTemplate(connectionFactory());
        jmsRequestTemplate.setDefaultDestinationName("yoink-request");
        jmsRequestTemplate.setReceiveTimeout(2000l);
        return jmsRequestTemplate;
    }

    /**
     * This bean delegates to a standard JMS item writer from Spring Batch,
     * after it has converted the job to a string containing the xml output.
     * @return a bean to write back to a jms queue
     */
    @Bean
    ItemWriter<Job> jmsJobItemWriter() {
        ItemWriter jmsJobItemWriter = new JmsJobItemWriter();
        return jmsJobItemWriter;
    }

    /**
     * Standard Spring Batch item writer for jms.
     * @return -{@link org.springframework.batch.item.jms.JmsItemWriter<T>}
     */
    @Bean
    ItemWriter<String> jmsItemWriter() {
        JmsItemWriter<String> jmsItemWriter = new JmsItemWriter<String>();
        jmsItemWriter.setJmsTemplate(jmsResponseTemplate());
        return jmsItemWriter;
    }

    /**
     * Standard JMS template, used to respond.
     * @return -{@link org.springframework.jms.core.JmsTemplate}
     */
    @Bean
    JmsOperations jmsResponseTemplate() {
        JmsTemplate jmsResponseTemplate = new JmsTemplate(connectionFactory());
        jmsResponseTemplate.setDefaultDestinationName("yoink-response");
        jmsResponseTemplate.setReceiveTimeout(2000l);
        return jmsResponseTemplate;
    }

}
