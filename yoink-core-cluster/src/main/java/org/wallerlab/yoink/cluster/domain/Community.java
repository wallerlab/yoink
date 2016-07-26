package org.wallerlab.yoink.cluster.domain;

import java.util.Set;

import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.wallerlab.yoink.api.model.Interaction;


@NodeEntity
public class Community {

	@GraphId
	Long id;

	private final long size;

	private final double totalSumOfEdgeWeights;

	private final double sumOfInternalEdgeWeights;

	@Relationship(type = "HAS_MEMBERS")
	private Set<Interaction> interactions;

	Community(final Set<Interaction> interactions,
			  final double totalSumOfEdgeWeights,
			  final double sumOfInternalEdgeWeights) {
		this.size = interactions.size();
		this.interactions = interactions;
		this.totalSumOfEdgeWeights = totalSumOfEdgeWeights;
		this.sumOfInternalEdgeWeights = sumOfInternalEdgeWeightsl
	}

	public String getId(){
		return id;
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

	@Override
	public String toString() {
		return "Community(Size: " + size +
						  ", ID: " + id +
					      ", Sum_int: " + sumOfInternalEdgeWeights +
				          ", Sum_tot: " + totalSumOfEdgeWeights +
				          ", members: " + interactions +
				          ")";
	}




	
}
