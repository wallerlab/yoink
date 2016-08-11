package org.wallerlab.yoink.cluster.config

import org.neo4j.ogm.session.SessionFactory
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager
import org.wallerlab.yoink.cluster.GraphConfig
import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class GraphConfigSpec extends  Specification{

    def "assert that the config is right"() {

        when:
        def graphConfig = new GraphConfig()

        then:
        graphConfig.sessionFactory()  instanceof  SessionFactory
        graphConfig.transactionManager() instanceof  Neo4jTransactionManager

    }
}
