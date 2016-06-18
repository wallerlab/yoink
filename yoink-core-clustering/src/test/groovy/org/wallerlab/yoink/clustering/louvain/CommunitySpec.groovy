package org.wallerlab.yoink.clustering.louvain


import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.Node
import org.wallerlab.yoink.clustering.GraphPopulator
import org.wallerlab.yoink.clustering.DatabaseService
import org.wallerlab.yoink.clustering.Relations;


import spock.lang.*

class CommunitySpec extends Specification {


	def "complete 1st assignment run"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)
		louvain.m = 3.0

		def tx = graph.beginTx()
		populate(graph)

		louvain.init()

		def fact = new CommunityFactory(graph, Relations.INTERACT)

		def comm =  fact.of(graph.getNodeById(1))

		def label = comm.getLabel()
		def csize = comm.size()
		def tonode = comm.getEdgeWeightSumTo(graph.getNodeById(0))
		def totalsum = comm.getTotalEdgeWeightSum()
		def internalsum = comm.getInternalEdgeWeightSum()

		tx.success()
		tx.close()

		expect:

		label.name() == "0#1"
		csize == 1
		tonode == 1.0
		totalsum == 2.0
		internalsum == 0.0

		cleanup:
		service.graphDb().shutdown()
	}




	def "complete 1st assignment run bigger community"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)
		louvain.m = 3.0

		def tx = graph.beginTx()
		populate(graph)

		louvain.init()

		def fact = new CommunityFactory(graph, Relations.INTERACT)

		graph.getNodeById(2).removeLabel(DynamicLabel.label("2"))
		graph.getNodeById(2).addLabel(DynamicLabel.label("1"))

		def comm =  fact.of(graph.getNodeById(1))

		def label = comm.getLabel()
		def csize = comm.size()
		def tonode = comm.getEdgeWeightSumTo(graph.getNodeById(0))
		def totalsum = comm.getTotalEdgeWeightSum()
		def internalsum = comm.getInternalEdgeWeightSum()

		tx.success()
		tx.close()

		expect:

		label.name() == "0#1"
		csize == 1
		tonode == 1.0
		totalsum == 2.0
		internalsum == 0.0

		cleanup:
		service.graphDb().shutdown()
	}



	def "self loops, weights"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb().startDb()

		def graph = service.graphDb()
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)

		def tx = graph.beginTx()


		List<Node> nodes = []

		for (j in 0..2) {	
			nodes.add( graph.createNode())
		}

		// a -3- a -1- b -2- c
		
		def w = "weight"
		nodes.get(0).createRelationshipTo(nodes.get(0), Relations.INTERACT).setProperty(w, 3.0d)
		nodes.get(0).createRelationshipTo(nodes.get(1), Relations.INTERACT).setProperty(w, 1.0d)
		nodes.get(1).createRelationshipTo(nodes.get(2), Relations.INTERACT).setProperty(w, 2.0d)

		louvain.init()

		def factory = new CommunityFactory(graph, Relations.INTERACT)

		def comm = factory.of(graph.getNodeById(0))
		
//		println comm
		
//		println factory.of(graph.getNodeById(1))
		
	//	println factory.of(graph.getNodeById(2))
		
		louvain.assignmentIterator(nodes)
		
	//	println louvain.communities()
		
		def label = comm.getLabel()
		def csize = comm.size()
		def tonode = comm.getEdgeWeightSumTo(graph.getNodeById(0))
		def totalsum = comm.getTotalEdgeWeightSum()
		def internalsum = comm.getInternalEdgeWeightSum()

		tx.success()
		tx.close()

		expect:

		label.name() == "0#0"
		csize == 1
		tonode == 3.0
		totalsum == 4.0
		internalsum == 3.0

		cleanup:
		service.graphDb().shutdown()
	}


	def populate(GraphDatabaseService graph) {

		// a - b
		//   \ |
		//     c
		
		Set<Set<String>> interactionSet = [
			["a", "b"].toSet(),
			["a", "c"].toSet(),
			["b", "c"].toSet()
		].toSet()

		def populator = new GraphPopulator<String>(graph)
		populator.createRelationships(interactionSet)
	}
}
