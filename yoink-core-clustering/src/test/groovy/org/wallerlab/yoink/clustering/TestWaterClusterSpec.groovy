package org.wallerlab.yoink.clustering


import java.util.Optional;

import spock.lang.*
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.wallerlab.yoink.clustering.louvain.LouvainAlgoImpl
import org.wallerlab.yoink.clustering.utils.Timer;



class TestWaterClusterSpec extends Specification {
	
	
	
	

	def "water cluster"(){

		setup:


		def service = new DatabaseService("testDb")

		service.clearDb()

		service.startDb()

		def graph = service.graphDb()

		Set<Set<String>> set = []
		
		
		
		new File("src/test/resources/org/wallerlab/yoink/clustering/interactions.csv").newReader().eachLine {
			
			line ->
			def entry = line.trim().split(", ")
			
			def tuple = new HashSet<String>()
			tuple.add(entry[0].trim())
			tuple.add(entry[1].trim())
			set.add(tuple)
		}
		
		println set
		
		def tx = graph.beginTx()


		println "creating nodes"


		def populator = new GraphPopulator<String>(graph)
		
		populator.createRelationships(set)
		
		int i = 0;
		graph.getAllNodes().each {i++}
		int j = 0
		graph.getAllRelationships().each {j++}
		
				
		println "$i nodes and $j relationships created. "
		
		def louvain = new LouvainAlgoImpl(graph, Relations.INTERACT)
		
		
		louvain.init()
		
		def timer = Timer.newTimer()
	
		timer.start()

		louvain.louvain(2)
		
		//println louvain.communities()
		//println louvain.nodesAtLevel

		def c =  louvain.gatherResult(1)
		println populator.convertCommunities(c)		
		
		timer.stop()

		println "louvain took: " + timer.totalTime()

		
		
		tx.success()
		tx.close()
		
		
		
		expect:
		true

		cleanup:
		service.graphDb().shutdown()




	}
	
	


}
