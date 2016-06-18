package org.wallerlab.yoink.batch.clustering

import org.wallerlab.yoink.batch.clustering.louvain.LouvainAlgoImpl
import org.wallerlab.yoink.batch.clustering.utils.Timer;

import spock.lang.*;


class ReturnInteractionSetSpec extends Specification{


	def "water cluster"(){

		setup:


		def service = new DatabaseService("testDb")

		service.clearDb()

		service.startDb()

		def graph = service.graphDb()

		Object mol1 = new Object()
		Object mol2 = new Object()
		Object mol3 = new Object()
		Object mol4 = new Object()

		/*
		 * 1--2
		 * |
		 * 3--4
		 */

		Set<Set<Object>> interactionSet = [
			[mol1, mol2].toSet(),
			[mol1, mol3].toSet(),
			[mol3, mol4].toSet()
		].toSet()



		def tx = graph.beginTx()

		def populator = new GraphPopulator<Object>(graph)

		populator.createRelationships(interactionSet)

		int i = 0;
		graph.getAllNodes().each {i++}
		int j = 0
		graph.getAllRelationships().each {j++}

		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)


		louvain.init()

		def timer = Timer.newTimer()

		timer.start()

		louvain.louvain(1)

		//println louvain.communities()
		//println louvain.nodesAtLevel

		def c =  louvain.gatherResult(0)
		def result = populator.convertCommunities(c)

		timer.stop()

		println c

		tx.success()
		tx.close()



		expect:
		i == 4
		j == 3
			
		result.size() == 2

		cleanup:
		service.graphDb().shutdown()




	}
}
