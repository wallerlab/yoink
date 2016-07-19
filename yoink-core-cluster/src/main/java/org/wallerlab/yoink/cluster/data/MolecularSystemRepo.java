package org.wallerlab.yoink.cluster.data;

import java.util.List;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import org.springframework.stereotype.Service;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Repository Interfacer for the domain model {@link MolecularSystem}
 *
 * @author Christian Ouali Turki
 *
 */
@Service
public interface MolecularSystemRepo extends GraphRepository<MolecularSystem>{

}