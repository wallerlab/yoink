package org.wallerlab.yoink.cluster.domain;

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Set;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Creates the graph from the provided interacting pairs.
 * The set has to be in the format
 * [[a,b], [b,d],[a,e],...]
 *
 * Interactions as defined by DORI criteria.
 */
@NodeEntity
public class Interaction {

	@Relationship(type = "INTERACTS")
	Set<MolecularSystem.Molecule> molecules;
	
	public final double weight;

	private final double degree;

	public Interaction(final Set<MolecularSystem.Molecule> molecules, double weight, double degree) {
		this.molecules = molecules;
		this.weight = weight;
		this.degree = degree;
	}

	public double getWeight() {
		return weight;
	}

	public double getDegree() { return degree;}

	public Set<MolecularSystem.Molecule> getMolecules() {
		return molecules;
	}


}
