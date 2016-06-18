package org.wallerlab.yoink.clustering.louvain

import org.wallerlab.yoink.clustering.DatabaseService
import org.wallerlab.yoink.clustering.Relations
import org.wallerlab.yoink.clustering.utils.Timer
import org.wallerlab.yoink.clustering.GraphPopulator;
import spock.lang.*

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.Node
import org.neo4j.graphdb.Relationship

class LouvainSpec extends Specification {


		def "test deltaQ"(){

		given:

		GraphDatabaseService service = Mock()

		def louvain = new LouvainAlgoImpl(service, Relations.INTERACT)

		Community c = Mock()
		Node n = Mock()
		n.getDegree() >> 1
		
		louvain.m = 3

		c.getEdgeWeightSumTo(n) >> 1.0
		c.getInternalEdgeWeightSum() >> 1.0
		c.getTotalEdgeWeightSum() >> 2.0
		

		def res =  louvain.deltaQ(c, n)

	
		
		expect:
		0.05555555555555554 == res
	}

		def "test init"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()



		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)


		def tx = graph.beginTx()


		Set<Set<String>> interactionSet = [
			["a", "b"].toSet(),
			["a", "c"].toSet(),
			["b", "c"].toSet(),
			["b", "d"].toSet(),
			["d", "e"].toSet(),
			["d", "f"].toSet(),
			["e", "f"].toSet()
		].toSet()

		def populator = new GraphPopulator<String>(graph)


		populator.createRelationships(interactionSet)

		int nodes = 0
		graph.getAllNodes().each {nodes++}


		int edges = 0
		graph.getAllRelationships().each {edges++}

		louvain.init()

		boolean initcorrect = true

		graph.getAllNodes().each {it -> initcorrect &= (it.getProperty("community") == ("0#"+it.id.toString()))}


		tx.success()
		tx.close()

		expect:
		nodes == 6
		edges == 7
		initcorrect

		cleanup:
		service.graphDb().shutdown()
	}
		
	@Ignore
	def "first deltaQ run"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)


		def tx = graph.beginTx()
		populate(graph)

		louvain.init()

		for (Node node : graph.getAllNodes()){
			def neighbors = []
			//println node.id
			for(Relationship r : node.getRelationships()){

				neighbors.add(r.getOtherNode(node))
			}
			for (Node neighbor : neighbors){
				neighbor.id + ", " + louvain.deltaQ(node, neighbor)
			}
		}

		tx.success()
		tx.close()

		expect:

		false

		cleanup:
		service.graphDb().shutdown()
	}


	@Ignore
	def "complete 1st assignment run"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)

		def tx = graph.beginTx()
		populate(graph)
		
		louvain.init()


		for (Node node : graph.getAllNodes()){
			def neighbors = []
			// println node.id
			for(Relationship r : node.getRelationships()){

				neighbors.add(r.getOtherNode(node))
			}
			for (Node neighbor : neighbors){
				neighbor.id + ", " + louvain.deltaQ(node, neighbor)
			}
		}

		tx.success()
		tx.close()

		expect:

		false

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
		
		def timer = Timer.newTimer()
	
		timer.start()

		louvain.louvain(1)


		timer.stop()

		println "louvain took: " + timer.totalTime()

		def result =  louvain.gatherResult(1)
		
		
		tx.success()
		tx.close()

		expect:
		
		result.size() == 2

		cleanup:
		service.graphDb().shutdown()
	}





	def populate(GraphDatabaseService graph) {

		Set<Set<String>> interactionSet = [
			["a", "b"].toSet(),
			["a", "c"].toSet(),
			["b", "c"].toSet(),
			["b", "d"].toSet(),
			["d", "e"].toSet(),
			["d", "f"].toSet(),
			["e", "f"].toSet()
		].toSet()

		def populator = new GraphPopulator<String>(graph)


		populator.createRelationships(interactionSet)
	}
}
