package org.wallerlab.yoink.cluster

import org.wallerlab.yoink.cluster.service.graph.DatabaseService
import org.wallerlab.yoink.cluster.service.graph.GraphPopulator
import spock.lang.*;

class GraphPopulatorSpec extends Specification{



	def "test node creation"(){

		setup:

		def service = new DatabaseService("testDb")
		service.clearDb()
		service.startDb()

		def graph = service.graphDb()

		Object mol1 = new Object()
		Object mol2 = new Object()
		Object mol3 = new Object()
		Object mol4 = new Object()


		Set<Set<Object>> interactionSet = [
			[mol1, mol2].toSet(),
			[mol1, mol3].toSet(),
			[mol3, mol4].toSet()
		].toSet()

		def populator = new GraphPopulator<Object>(graph)

		def tx = graph.beginTx()

		populator.createNodes(interactionSet)

		//println populator.bimap
		int i = 0;
		graph.getAllNodes().each {i++}

		tx.close()

		expect:

		i== 4
		cleanup:
		service.shutdown()

	}


	def "test relationship creation"(){

		setup:

		def service = new DatabaseService("testDb")

		service.clearDb()
		service.startDb()
		
		def graph = service.graphDb()

		Object mol1 = new Object()
		Object mol2 = new Object()
		Object mol3 = new Object()
		Object mol4 = new Object()


		Set<Set<Object>> interactionSet = [
			[mol1, mol2].toSet(),
			[mol1, mol3].toSet(),
			[mol3, mol4].toSet()
		].toSet()

		def populator = new GraphPopulator<Object>(graph)

		def tx = graph.beginTx()

		populator.createRelationships(interactionSet)

		int i = 0;
		
		graph.getAllRelationships().each {i++}

		tx.close()

		expect:

		i == 3
		
		cleanup:
		service.shutdown()

	}

	def "test interactionset to Objectset"(){

		setup:


		Object mol1 = new Object()
		Object mol2 = new Object()
		Object mol3 = new Object()
		Object mol4 = new Object()

		Set<Set<Object>> interactionSet = new HashSet<Set<Object>>()
		Set<Object> set1 = new HashSet<Object>()
		set1.add(mol1)
		set1.add(mol2)
		interactionSet.add(set1)
		Set<Object> set2 = new HashSet<Object>()
		set2.add(mol2)
		set2.add(mol3)
		interactionSet.add(set2)

		def gp = new GraphPopulator<Object>(null)
		//Set<Object>
		def set = gp.getObjectSet(interactionSet)

		expect:

		set == [mol1, mol2, mol3].toSet()



	}

}
