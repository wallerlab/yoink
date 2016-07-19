package org.wallerlab.yoink.cluster.data;

import org.wallerlab.yoink.api.model.Interaction;

import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by waller on 18/07/16.
 */
public interface InteractionRepo extends GraphRepository<Interaction> {
}
