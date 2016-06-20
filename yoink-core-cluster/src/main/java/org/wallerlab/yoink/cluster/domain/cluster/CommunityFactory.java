package org.wallerlab.yoink.cluster.domain.cluster;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

public class CommunityFactory {

	/**
	 * @param graph
	 * @param edgeType
	 */
	public CommunityFactory(GraphDatabaseService graph,
			RelationshipType edgeType) {
		this.graph = graph;
		this.edgeType = edgeType;
	}

	GraphDatabaseService graph;

	RelationshipType edgeType;

	public Community of(Node node) {

		String id = (String) node.getProperty("community");

		Label label = DynamicLabel.label(String.valueOf(id));

		return new CommunityImpl(graph.findNodes(label), label, id, edgeType,
				graph);

	}
	
	public Community of(String id) {

		Label label = DynamicLabel.label(String.valueOf(id));

		return new CommunityImpl(graph.findNodes(label), label, id, edgeType,
				graph);

	}

}
