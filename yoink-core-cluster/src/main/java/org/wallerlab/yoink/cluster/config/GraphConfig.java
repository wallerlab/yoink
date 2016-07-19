package org.wallerlab.yoink.cluster.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for setting Neo4J specifications
 *
 * @author Christian Ouali Turki
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "org.gradle.dataBaseRepositories")
@EnableTransactionManagement
public class GraphConfig extends Neo4jConfiguration {

    /**
     * Bean for setting driver class (http or embedded) and connection uri
     *
     * @return Configuration
     */
    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
              .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
              .setURI("http://neo4j:password@localhost:7474");
        return config;
    }

    /**
     * setting arguments for the SessionFactory
     *
     * @return SessionFectory
     */
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "org.gradle.domain");
    }

}