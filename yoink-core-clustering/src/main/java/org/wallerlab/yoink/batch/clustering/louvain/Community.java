package org.wallerlab.yoink.batch.clustering.louvain;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;

interface Community {

	double getInternalEdgeWeightSum();
	
	double getTotalEdgeWeightSum();
	
	double getEdgeWeightSumTo(Node incidentNode);
	
	ResourceIterator<Node> getNodes();
	
	Label getLabel();
	
	String getId();
	
	long size();
	
	
}
