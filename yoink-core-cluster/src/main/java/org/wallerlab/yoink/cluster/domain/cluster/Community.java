package org.wallerlab.yoink.cluster.domain.cluster;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;

public interface Community {

	long size();

	String getId();

	Label getLabel();

	double getSumOfInternalEdgeWeights();
	
	double getTotalSumOfEdgeWeights();
	
	double getEdgeWeightSumTo(Node incidentNode);
	
	ResourceIterator<Node> getNodes();

}
