package org.wallerlab.yoink.clustering.louvain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;

class NodeAggregator {

	private GraphDatabaseService graph;
	private RelationshipType edgeType;
	private String attributeName = "community";

	NodeAggregator(GraphDatabaseService graph,
			RelationshipType edgeType) {
		this.graph = graph;
		this.edgeType = edgeType;
	}

	List<Node> aggregateCommunities(Collection<Node> startnodes, long hierarchylevel) {

		String level = String.valueOf(hierarchylevel);// + "-";

		HashMap<String, Node> parentIndex = new HashMap<>();

		Set<Long> nodeSet = new HashSet<>();

		HashMap<AggregationTuple, Double> aggregation = new HashMap<>();

		TraversalDescription traversal = graph.traversalDescription()
				.breadthFirst().uniqueness(Uniqueness.RELATIONSHIP_GLOBAL)
				.relationships(edgeType);

		for (Relationship edge : traversal.traverse(startnodes).relationships()) {

			//System.out.println(edge);

			AggregationTuple tuple = new AggregationTuple(edge);

			Double weight = Double.valueOf((double) edge.getProperty("weights",
					1.0));

			if (aggregation.containsKey(tuple)) {

				Double sum = aggregation.get(tuple);
				aggregation.put(tuple, sum + weight);

			} else {

				aggregation.put(tuple, weight);

			}

			for (int i = 0; i <= 1; i++) {

				Node node = edge.getNodes()[i];

				if (!nodeSet.contains(node.getId())) {

					Node parent;

					String community = (String) node.getProperty(attributeName);

					if (parentIndex.containsKey(community)) {

						parent = parentIndex.get(community);

					} else {

						String newlabel = level + community.substring(level.length());
						
						parent = graph.createNode(DynamicLabel.label(newlabel));
						parent.setProperty(attributeName, newlabel);

						parentIndex.put(community, parent);

					}
					nodeSet.add(node.getId());
					node.createRelationshipTo(parent,
							LouvainRelation.LOUVAIN_ACCUMULATED_BY);

				}
			}

		}

		
		for(AggregationTuple tuple : aggregation.keySet()){
			
			Node one = parentIndex.get( tuple.getStart() );
			Node two = parentIndex.get( tuple.getEnd() );
			
			one.createRelationshipTo(two, edgeType).setProperty("weights", aggregation.get(tuple));;
			
		}
		
		Set<Node> set = new HashSet<>();
		
		set.addAll(parentIndex.values());
		
		List<Node> list = new ArrayList<Node>();
		list.addAll(set);
		return list;

	}

}
