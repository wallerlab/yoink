package org.wallerlab.yoink.cluster.louvain;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

class CommunityFactory {

	/**
	 * @param graph
	 * @param edgeType
	 */
	CommunityFactory(GraphDatabaseService graph,
			RelationshipType edgeType) {
		this.graph = graph;
		this.edgeType = edgeType;
	}

	GraphDatabaseService graph;

	RelationshipType edgeType;

	Community of(Node node) {

		String id = (String) node.getProperty("community");

		Label label = DynamicLabel.label(String.valueOf(id));

		return new CommunityImpl(graph.findNodes(label), label, id, edgeType,
				graph);

	}
	
	Community of(String id) {

		Label label = DynamicLabel.label(String.valueOf(id));

		return new CommunityImpl(graph.findNodes(label), label, id, edgeType,
				graph);

	}

}