package org.wallerlab.yoink.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotInTransactionException;
import org.neo4j.graphdb.Relationship;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 *  Constructs the graph with objects<T>
 *  
 *  Maintains BiMap {T:Node} to convert between mapped objects and network nodes.
 * 
 * @author marwin
 *
 * @param <T>
 */
class GraphPopulator<T> {

	private BiMap<T, Node> bimap;
	private GraphDatabaseService graphDb;

	public GraphPopulator(GraphDatabaseService graphDb) {
		this.graphDb = graphDb;
	}

	private void createNodes(List<InteractionTriple<T>> interactionSet)
												throws NotInTransactionException {
		bimap = HashBiMap.create();
		Set<T> molecules = new HashSet<>();
		for (InteractionTriple<T> triple : interactionSet) {
				molecules.add(triple.first);
				molecules.add(triple.second);
		}
			
		for (T mol : molecules) {
			Node node = graphDb.createNode(NodeLabel.MOLECULE);
			bimap.put(mol, node);
		}

	}
	
	
	private void createNodes(Set<Set<T>> interactionSet)
								throws NotInTransactionException {
		bimap = HashBiMap.create();
		for (T mol : getObjectSet(interactionSet)) {
			Node node = graphDb.createNode(NodeLabel.MOLECULE);
			bimap.put(mol, node);
		}
	}

	void createRelationships(List<InteractionTriple<T>> interactionSet)
			throws NotInTransactionException {

		createNodes(interactionSet);
		
		for (InteractionTriple<T> triple : interactionSet) {

			Node node = bimap.get(triple.first);
			Node otherNode = bimap.get(triple.second);
			Relationship edge = node.createRelationshipTo(otherNode, Relations.INTERACT);
			
			edge.setProperty("weights", triple.weight);
			

		}
	}
	
	
	void createRelationships(Set<Set<T>> interactionSet)
			throws NotInTransactionException {

		createNodes(interactionSet);

		for (Set<T> pair : interactionSet) {

			List<T> list = pair.stream().collect(Collectors.toList());

			Node node = bimap.get(list.get(0));
			Node otherNode = bimap.get(list.get(1));
			Relationship edge = node.createRelationshipTo(otherNode, Relations.INTERACT);
			
		//	edge.setProperty("weights", 1.0);
			;

		}
	}

	/**
	 * Reads set of interacting pairs
	 * 
	 * returns set of molecules
	 * 
	 * @param interaction
	 *            set
	 * @return molecule set
	 */

	private Set<T> getObjectSet(Set<Set<T>> interactionSet) {
		Set<T> molecules = new HashSet<>();
		for (Set<T> pair : interactionSet) {
			for (T mol : pair) {
				molecules.add(mol);
			}
		}
		return molecules;
	}
	
	
	List<Set<T>> convertCommunities(Map<String, Set<Node>> communities){
		Map<Node,T> inversebimap = bimap.inverse();
		List<Set<T>> output = new ArrayList<>();
		for(Set<Node> community : communities.values()){
			Set<T> convertedCommunity = new HashSet<T>(); 
			community.forEach( n-> convertedCommunity.add(inversebimap.get(n)));
			output.add(convertedCommunity);
		}
		return output;
	}

}
