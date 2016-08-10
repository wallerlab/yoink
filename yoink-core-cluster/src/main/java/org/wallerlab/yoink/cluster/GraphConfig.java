package org.wallerlab.yoink.cluster;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableExperimentalNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for setting Neo4J specifications
 *
 * @author Christian Ouali Turki
 */
@Configuration
@ComponentScan(basePackages = "org.wallerlab.yoink.cluster")
@EnableExperimentalNeo4jRepositories(basePackages = "com.wallerlab.yoink.cluster.data")
@EnableTransactionManagement
public class GraphConfig  {

        @Bean
        public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
        }

        /**
         * setting arguments for the SessionFactory
         *
         * @return SessionFectory
         */
        @Bean
        public SessionFactory sessionFactory(){
        // with domain entity base package(s)
        return new SessionFactory("org.wallerlab.yoink.cluster.domain");
    }

}