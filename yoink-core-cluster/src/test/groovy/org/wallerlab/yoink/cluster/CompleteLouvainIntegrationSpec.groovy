package org.wallerlab.yoink.cluster



import spock.lang.*;

class CompleteLouvainIntegrationSpec extends Specification {

	
	def 'cluster'(){
		setup:
		
		LouvainClusteringFacade louvain = new LouvainClusteringFacade<Object>("testDb/databases/graph.db")
		
		Object mol1 = new Object()
		Object mol2 = new Object()
		Object mol3 = new Object()
		Object mol4 = new Object()
		Object mol5 = new Object()
		Object mol6 = new Object()

		/*
		 *  1--2--4--5 
		 *  | /    \ |
		 *  3        6 
		 *  
		 * 
		 */
		
		Set<Set<Object>> interactionSet = [
			[mol1, mol2].toSet(),
			[mol1, mol3].toSet(),
			[mol2, mol3].toSet(),
			[mol2, mol4].toSet(),
			[mol4, mol5].toSet(),
			[mol4, mol6].toSet(),
			[mol6, mol5].toSet()
		].toSet()
		
		
		//println "populate" + interactionSet
		louvain.populate(interactionSet)
		
		println "cluster"
		def clusterres = louvain.cluster(2);
		println clusterres
		
		def res =  louvain.getResult(0)
		
		louvain.shutdown()
		
		expect:
		
		res.size() == 2
		
		
	}
	
	
	def 'cluster weights'(){
		setup:
		
		LouvainClusteringFacade louvain = new LouvainClusteringFacade<Object>("testDb/databases/graph.db")
		
		Object mol1 = new Object()
		Object mol2 = new Object()
		Object mol3 = new Object()
		Object mol4 = new Object()

		/*
		 *  1-(1)-2-(1)-3-(20)-4
		 *        
		 *
		 *
		 */
		
		InteractionTriple<Object> t1 = new InteractionTriple(mol1,mol2,1.0);
		InteractionTriple<Object> t2 = new InteractionTriple(mol2,mol3, 1.0);
		InteractionTriple<Object> t3 = new InteractionTriple(mol3,mol4,1.0);
		
		
		List<InteractionTriple<Object>> interactionSet = [t1,t2,t3]
		
		
		//println "populate" + interactionSet
		louvain.populate(interactionSet)
		
		println "cluster"
		def clusterres = louvain.cluster(1);
		println clusterres
		
		def res =  louvain.getResult(0)
		
		println res
		
		louvain.shutdown()
		
		expect:
		
		res.size() == 2
		
		
	}
	

	def 'cluster2'(){
		setup:
		
		LouvainClusteringFacade louvain = new LouvainClusteringFacade<Integer>("testDb/databases/graph.db")
		
		Integer mol0 = new Integer(0)
		Integer mol1 = new Integer(1)
		Integer mol2 = new Integer(2)
		Integer mol3 = new Integer(3)
		Integer mol4 = new Integer(4)
		Integer mol5 = new Integer(5)
		Integer mol6 = new Integer(6)
		Integer mol7 = new Integer(7)
		Integer mol8 = new Integer(8)
		Integer mol9 = new Integer(9)
		Integer mol10 = new Integer(10)
		Integer mol11 = new Integer(11)
		Integer mol12 = new Integer(12)
		Integer mol13 = new Integer(13)
		Integer mol14 = new Integer(14)
		Integer mol15 = new Integer(15)

			
		Set<Set<Integer>> interactionSet = [
			[mol0, mol3].toSet(),
			[mol0, mol2].toSet(),
			[mol0, mol4].toSet(),
			[mol0, mol5].toSet(),
			[mol1, mol2].toSet(),
			[mol1, mol4].toSet(),
			[mol1, mol7].toSet(),
			[mol2, mol4].toSet(),
			[mol2, mol5].toSet(),
			[mol2, mol6].toSet(),
			[mol3, mol7].toSet(),
			[mol4, mol10].toSet(),
			[mol5, mol7].toSet(),
			[mol5, mol11].toSet(),
			[mol6, mol7].toSet(),
			[mol6, mol11].toSet(),
			[mol8, mol9].toSet(),
			[mol8, mol10].toSet(),
			[mol8, mol11].toSet(),
			[mol8, mol14].toSet(),
			[mol8, mol15].toSet(),
			[mol9, mol12].toSet(),
			[mol9, mol14].toSet(),
			[mol10, mol11].toSet(),
			[mol10, mol12].toSet(),
			[mol10, mol13].toSet(),
			[mol10, mol14].toSet()
			
		].toSet()
		
		
		//println "populate" + interactionSet
		louvain.populate(interactionSet)
		
		println "cluster"
		def result = louvain.cluster(2);
		println result
		
		println louvain.getResult(0)
		println louvain.getResult(1)
		
		louvain.shutdown()
		
		
		
	}
	
	
}
