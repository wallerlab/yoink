package org.wallerlab.yoink.cluster.domain;

import org.neo4j.ogm.annotation.Relationship;
import org.wallerlab.yoink.api.model.Interaction;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.wallerlab.yoink.api.model.molecular.*;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Set;

/**
 * Creates the graph from the provided interacting pairs.
 * The set has to be in the format
 * [[a,b], [b,d],[a,e],...]
 *
 * Interactions as defined by DORI criteria.
 */
@NodeEntity
public class Interaction {

	@GraphId
	private Long id;

	@Relationship(type = "INTERACTS")
	Set<MolecularSystem.Molecule> molecules;
	
	public final double weight;

	public Interaction(final Set<MolecularSystem.Molecule> molecules, double weight) {
		this.molecules = molecules;
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public Set<MolecularSystem.Molecule> getMolecules() {
		return molecules;
	}


}
