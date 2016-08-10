package org.wallerlab.yoink.cluster

import org.neo4j.graphdb.DynamicLabel
import org.neo4j.graphdb.Node

import spock.lang.*;

class DatabaseInteractionSpec extends Specification{

/*	def "test run"(){

		setup:

		def service = DatabaseService.createAt("testDb")
		service.startDb()
		expect:
		service.graphDb().isAvailable(10000)


		cleanup:
		service.graphDb().shutdown()
	}


	def "add nodes"(){

		setup:

		def service = DatabaseService.createAt("testDb")
		
		service.clearDb()

		service.startDb()
		
		def graph = service.graphDb()

		def tx = graph.beginTx()

		List<Node> nodes = []
			
		for (j in 0..7) {
			nodes.add(graph.createNode(DynamicLabel.label("INTERACTING_PAIR")))
		}
	
		
		//a -- b -- c -- d
		//     b -- e -- f
		nodes.get(0).createRelationshipTo(nodes.get(1), Relations.INTERACT)
		nodes.get(1).createRelationshipTo(nodes.get(2), Relations.INTERACT)
		nodes.get(2).createRelationshipTo(nodes.get(3), Relations.INTERACT)
		nodes.get(0).createRelationshipTo(nodes.get(4), Relations.INTERACT)
		nodes.get(4).createRelationshipTo(nodes.get(5), Relations.INTERACT)
		nodes.get(5).createRelationshipTo(nodes.get(6), Relations.INTERACT)
		nodes.get(2).createRelationshipTo(nodes.get(4), Relations.INTERACT)
		nodes.get(5).createRelationshipTo(nodes.get(3), Relations.INTERACT)
		nodes.get(4).createRelationshipTo(nodes.get(4), Relations.INTERACT)

		def iter = graph.getAllNodes().iterator()

		int i = 0

		while (iter.hasNext()){
			iter.next()
			i++
		}
		int j = 0;
		
		graph.getAllRelationships().each {j++}

		
		tx.success()
		tx.close()
		
		expect:
		i == 8
		j == 9
		cleanup:
		service.graphDb().shutdown()
	}*/

}
