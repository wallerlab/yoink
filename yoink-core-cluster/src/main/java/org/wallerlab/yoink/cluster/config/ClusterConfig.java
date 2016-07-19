package org.wallerlab.yoink.cluster.config;

import org.springframework.context.annotation.Bean;
import org.wallerlab.yoink.cluster.service.louvain.LouvainClusteringFacade;

import static org.wallerlab.yoink.api.model.Job.JobParameter.OUTPUT_FOLDER;

/**
 * Created by waller on 18/07/16.
 */
public class ClusterConfig {

    private final String DB_DIRECTORY = "yoink-core-cluster/testDb/";
    @Bean
    LouvainClusteringFacade louvainClusteringFacade(){
        LouvainClusteringFacade<Integer> louvain = new LouvainClusteringFacade<Integer>(DB_DIRECTORY + "databases/graph.db");
      return louvain;
    }

}
