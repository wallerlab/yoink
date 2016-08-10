package org.wallerlab.yoink.cluster.data;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

/**
 * Repository Interface for the domain model {@link MolecularSystem}
 *
 * @author Christian Ouali Turki
 *
 */
public interface MolecularSystemRepo extends Neo4jRepository<MolecularSystem> {
}