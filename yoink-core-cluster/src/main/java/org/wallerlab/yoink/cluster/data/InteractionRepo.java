package org.wallerlab.yoink.cluster.data;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wallerlab.yoink.cluster.domain.Interaction;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface InteractionRepo extends Neo4jRepository<Interaction> {
}
