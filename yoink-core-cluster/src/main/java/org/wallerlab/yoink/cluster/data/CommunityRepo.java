package org.wallerlab.yoink.cluster.data;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wallerlab.yoink.cluster.domain.Community;

public interface CommunityRepo extends Neo4jRepository<Community> {
}
