package org.wallerlab.yoink.clustering.louvain

import org.wallerlab.yoink.clustering.GraphPopulator;
import spock.lang.*

import org.neo4j.graphdb.GraphDatabaseService
import org.wallerlab.yoink.clustering.DatabaseService
import org.wallerlab.yoink.clustering.NodeLabel;
import org.wallerlab.yoink.clustering.Relations
;

class CommunityAssignmentSpec extends Specification {

	def "one node assignment"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)
		louvain.m = 7.0

		def tx = graph.beginTx()
		populate(graph)

		
		louvain.init()

		def node = graph.getNodeById(0)
		
		String community = node.getProperty("community")

		louvain.communityAssignment(node)
		
		tx.success()
		tx.close()

		expect:
		
		community == "0#0"
		
		cleanup:
		service.graphDb().shutdown()
	}

	
	def "full iterator"(){

		setup:


		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()

		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)

		def tx = graph.beginTx()
		populate(graph)
		louvain.init()

		def dq =  louvain.assignmentIterator(graph.getAllNodes().asList())

		Set<String> comms = [];
		
		graph.getAllNodes().each{it -> comms.add(it.getProperty('community').toString())} 		
				
		tx.success()
		tx.close()

		expect:

		dq == 0.0
		comms.size() == 2

		cleanup:
		service.graphDb().shutdown()
	}


	def "hierarchy accumulator"(){

		setup:


		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()

		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)

		def tx = graph.beginTx()
		populate(graph)
		louvain.init()

		def nodes = graph.getAllNodes().toList()

		def aggregator = new NodeAggregator(graph, Relations.INTERACT)
		
		def set = aggregator.aggregateCommunities(nodes, 1)

		tx.success()
		tx.close()

		expect:

		set.size() == 6

		cleanup:
		service.graphDb().shutdown()
	}

	
	def "complete"(){
		
				setup:
		
		
				def service = new DatabaseService("testDb")
		
				service.clearDb().startDb()
		
				def graph = service.graphDb()
		
				def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)
		
				def tx = graph.beginTx()
				populate(graph)
				louvain.init()
		
				def nodes = graph.findNodes(NodeLabel.MOLECULE).toList()
				
				louvain.assignmentIterator(nodes)
		
				def aggregator = new NodeAggregator(graph, Relations.INTERACT)
				
				def set = aggregator.aggregateCommunities(nodes, 1)
				
				louvain.assignmentIterator(set)
				
				
				
				aggregator.aggregateCommunities(set, 2)
								
				tx.success()
				tx.close()
		
				expect:
		
				set.size() == 2
		
				cleanup:
				service.graphDb().shutdown()
			}
	
	
	def populate(GraphDatabaseService graph) {

		/*
		 * a - b - d - e
		 *   \ |     \ |
     	 *     c       f
		 * 
		 * 
		 */
		
		Set<Set<String>> interactionSet = [
			["a", "b"].toSet(),
			["d", "f"].toSet(),
			["a", "c"].toSet(),
			["d", "e"].toSet(),
			["b", "c"].toSet(),
			["b", "d"].toSet(),
			["e", "f"].toSet()
		].toSet()

		def populator = new GraphPopulator<String>(graph)
		populator.createRelationships(interactionSet)
	}
}
