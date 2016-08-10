package org.wallerlab.yoink.cluster.domain;

import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

@NodeEntity
public class Community {

	private final long size;

	private final double totalSumOfEdgeWeights;

	private final double sumOfInternalEdgeWeights;

	@Relationship(type = "MEMBERS")
	private Set<Interaction> interactions;

	public Community(final Set<Interaction> interactions,
			  final double totalSumOfEdgeWeights,
			  final double sumOfInternalEdgeWeights) {
		this.size = interactions.size();
		this.interactions = interactions;
		this.totalSumOfEdgeWeights = totalSumOfEdgeWeights;
		this.sumOfInternalEdgeWeights = sumOfInternalEdgeWeights;
	}

	public long size(){
		return size;
	}

	public double getSumOfInternalEdgeWeights() {
		return sumOfInternalEdgeWeights;
	}

	public double getTotalSumOfEdgeWeights() {
		return totalSumOfEdgeWeights;
	}

	public Set<MolecularSystem.Molecule> getMolecules() {
		return interactions.stream().flatMap( (Interaction interaction) ->
									interaction.getMolecules().stream()).collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "Community(Size: " + size +
					      ", Sum_int: " + sumOfInternalEdgeWeights +
				          ", Sum_tot: " + totalSumOfEdgeWeights +
				          ", members: " + interactions +
				          ")";
	}

}
