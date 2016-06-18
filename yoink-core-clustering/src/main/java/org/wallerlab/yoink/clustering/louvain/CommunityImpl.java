package org.wallerlab.yoink.clustering.louvain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;

class CommunityImpl implements Community {

	Label label;
	GraphDatabaseService graph;
	RelationshipType edgeType;
	Set<Node> memberset;
	double totalEdgeWeightSum;
	double internalEdgeWeightSum;
	long size;
	String id;
	
	CommunityImpl(ResourceIterator<Node> members, Label label, String id,
			RelationshipType edgeType, GraphDatabaseService graph) {
		this.graph = graph;
		this.label = label;
		this.id = id;
		this.edgeType = edgeType;
		memberset = new HashSet<Node>();
		members.forEachRemaining(b -> memberset.add(b));
		size = 0;
		calculate();
	}

	private void calculate() {

		double totalsum = 0.;
		double internalsum = 0.;
		
		ResourceIterator<Node> iterator = getNodes();
		while (iterator.hasNext()) {

			Node node = iterator.next();
			size++;
			for (Relationship edge : node.getRelationships(edgeType)) {

				double weight = (double) edge.getProperty("weights", 1.0);
				totalsum += weight;

				if (edge.getOtherNode(node).hasLabel(label)) {
			
					internalsum += weight;
				//	System.out.println("internal sum: " + internalsum);
					
				}
			}
		}

		totalEdgeWeightSum = totalsum;
		internalEdgeWeightSum = internalsum;
	}

	@Override
	public double getInternalEdgeWeightSum() {

		return internalEdgeWeightSum;
	}

	@Override
	public double getTotalEdgeWeightSum() {

		return totalEdgeWeightSum;
	}

	@Override
	public ResourceIterator<Node> getNodes() {

		return graph.findNodes(label);
	}

	@Override
	public double getEdgeWeightSumTo(Node incidentNode) {

		/*
		 * TraversalDescription traversal = graph.traversalDescription()
		 * .relationships(edgeType) .evaluator(Evaluators.atDepth(1))
		 * .evaluator(Evaluators.includeWhereEndNodeIs(incidentNode));
		 */
		double sum = 0;
		ResourceIterator<Node> iterator = getNodes();
		while (iterator.hasNext()) {

			Node node = iterator.next();

			for (Relationship edge : node.getRelationships(edgeType)) {

				if (edge.getOtherNode(node).getId() == incidentNode.getId()) {

					sum += (double) edge.getProperty("weights", 1.0);

				}
			}
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
		
		return "Community(Size: " + size + ", ID: " + id + ", Label: " +label + ", Sum_int: " + internalEdgeWeightSum + ", Sum_tot: " + totalEdgeWeightSum + ", members: " + memberset+")";
	}
	
}
