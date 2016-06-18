package org.wallerlab.yoink.batch.clustering;

import java.util.List;
import java.util.Map;
import java.util.Set;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.wallerlab.yoink.batch.clustering.louvain.LouvainAlgoImpl;


/**
 * 
 * Convenience class to start, populate, cluster and return the results.
 * @author marwin
 *
 * @param <T> Type of graph populator
 */
public class LouvainClusteringFacade<T> {

	private final String databaseLocation;
	private GraphPopulator<T> populator;
	private DatabaseService service;
	private GraphDatabaseService graphDb;
	private LouvainAlgoImpl louvain;

	public LouvainClusteringFacade(String databaseFile) {

		this.databaseLocation = databaseFile;
		this.service = DatabaseService.createAt(databaseLocation);

		service.clearDb().startDb();

		this.graphDb = service.graphDb();
		this.populator = new GraphPopulator<T>(graphDb);
		this.louvain = new LouvainAlgoImpl(graphDb, Relations.INTERACT);

	}

	
	/**
	 * Creates the graph from the provided interacting pairs.
	 * The set has to be in the format
	 * [[a,b], [b,d],[a,e],...]
	 * 
	 * @param interactions as defined by DORI
	 */
	public void populate(Set<Set<T>> interactions) {

		try (Transaction tx = graphDb.beginTx()) {

			populator.createRelationships(interactions);
			tx.success();
		}
	}

	
	public void populate(List<InteractionTriple<T>> interactions) {

		try (Transaction tx = graphDb.beginTx()) {

			populator.createRelationships(interactions);
			tx.success();
		}
	}
	
	
	/**
	 * 
	 * 
	 * @param maxCommunities the max number of communities
	 * @return map of hierarchy level and corresponding number of communities {hierarchylevel : number of communites}
	 */
	public Map<Long, Integer> cluster(int maxCommunities) {
		System.out.println("in louvain clustering");
		try (Transaction tx = graphDb.beginTx()) {
			louvain.init();
			louvain.louvain(maxCommunities);
			tx.success();
		}
		return louvain.communities();

	}

	
	/**
	 * Return the set of communities at a certain hierarchy/aggregation level.
	 * 
	 * @param level for getting communities
	 * @return List of Communities
	 */
	public List<Set<T>> getResult(int level) {
			
		List<Set<T>> output = null;
		try (Transaction tx = graphDb.beginTx()) {

			output = populator.convertCommunities(louvain.gatherResult(level));
			tx.success();
		}
		return output;
	}
	
	public void shutdown(){
		
		service.shutdown();
		
	}

}
