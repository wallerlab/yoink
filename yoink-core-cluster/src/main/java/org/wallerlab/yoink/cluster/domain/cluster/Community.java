package org.wallerlab.yoink.cluster.domain.cluster;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;

public interface Community {

	double getInternalEdgeWeightSum();
	
	double getTotalEdgeWeightSum();
	
	double getEdgeWeightSumTo(Node incidentNode);
	
	ResourceIterator<Node> getNodes();
	
	Label getLabel();
	
	String getId();
	
	long size();
	
	
}
