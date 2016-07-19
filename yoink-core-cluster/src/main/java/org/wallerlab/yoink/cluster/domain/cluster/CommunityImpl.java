package org.wallerlab.yoink.cluster.domain.cluster;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;

class CommunityImpl implements Community {

	String id;
	long size;
	Label label;
	RelationshipType edgeType;
	Set<Node> memberSet;
	double totalSumOfEdgeWeights;
	double sumOfInternalEdgeWeights;

	GraphDatabaseService graph;

	CommunityImpl(ResourceIterator<Node> members,
				  Label label,
				  String id,
				  RelationshipType edgeType,
				  GraphDatabaseService graph) {
		this.graph = graph;
		this.label = label;
		this.id = id;
		this.edgeType = edgeType;
		memberSet = new HashSet<Node>();
		members.forEachRemaining(b -> memberSet.add(b));
		size = 0;
		calculate();
	}

	private void calculate() {
		double totalSum = 0.;
		double internalSum = 0.;
		ResourceIterator<Node> iterator = getNodes();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			size++;
			for (Relationship edge : node.getRelationships(edgeType)) {
				double weight = (double) edge.getProperty("weights", 1.0);
				totalSum += weight;
				if (edge.getOtherNode(node).hasLabel(label)) internalSum += weight;
			}
		}
		totalSumOfEdgeWeights = totalSum;
		sumOfInternalEdgeWeights = internalSum;
	}

	public double getSumOfInternalEdgeWeights() {
		return sumOfInternalEdgeWeights;
	}

	public double getTotalSumOfEdgeWeights() {
		return totalSumOfEdgeWeights;
	}

	@Override
	public ResourceIterator<Node> getNodes() {
		return graph.findNodes(label);
	}

	/*
	 * TraversalDescription traversal = graph.traversalDescription()
	 * .relationships(edgeType) .evaluator(Evaluators.atDepth(1))
	 * .evaluator(Evaluators.includeWhereEndNodeIs(incidentNode));
	*/
	@Override
	public double getEdgeWeightSumTo(Node incidentNode) {
		double sum = 0;
		ResourceIterator<Node> iterator = getNodes();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			for (Relationship edge : node.getRelationships(edgeType))
				if (edge.getOtherNode(node).getId() == incidentNode.getId())
					sum += (double) edge.getProperty("weights", 1.0);
		}
		return sum;
	}

	@Override
	public long size(){
		return size;
	}
	
	@Override
	public String getId(){
		return id;
	}
	
	@Override
	public Label getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return "Community(Size: " + size +
						  ", ID: " + id +
					      ", Label: " +label +
					      ", Sum_int: " + sumOfInternalEdgeWeights +
				          ", Sum_tot: " + totalSumOfEdgeWeights +
				          ", members: " + memberSet +
				          ")";
	}
	
}
