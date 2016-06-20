package org.wallerlab.yoink.cluster.service.louvain

import org.neo4j.graphdb.Relationship
import org.neo4j.graphdb.Node
import org.wallerlab.yoink.cluster.domain.cluster.AggregationTuple
import spock.lang.*

class AggregationTupleSpec extends Specification {

	def 'test creation'(){
		
		setup:
		
		Relationship edge = Mock()
		
		Node n1 = Mock()
		n1.getProperty("community") >> "2"
		
		Node n2 = Mock()
		n2.getProperty("community") >> "1"
		
		edge.getStartNode() >> n1
		
		edge.getEndNode() >> n2

		def tuple = new AggregationTuple(edge)
		
		
		expect:
		tuple.getStart() == "2"
		tuple.getEnd() == "1"

	}
	
	
}
