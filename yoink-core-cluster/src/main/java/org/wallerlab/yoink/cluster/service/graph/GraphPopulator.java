package org.wallerlab.yoink.cluster.service.graph;

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
import org.wallerlab.yoink.api.model.Interaction;
import org.wallerlab.yoink.cluster.domain.interaction.SimpleInteraction;
import org.wallerlab.yoink.cluster.domain.graph.NodeLabel;
import org.wallerlab.yoink.cluster.domain.graph.Relations;

/**
 * Constructs the graph with objects<T>
 *  
 * Maintains BiMap {T:Node} to convert between mapped objects and network nodes.
 * 
 * @author marwin
 *
 * @param <T>
 */
public class GraphPopulator<T> {

	private BiMap<T, Node> bimap;
	private GraphDatabaseService graphDb;

	public GraphPopulator(GraphDatabaseService graphDb) {
		this.graphDb = graphDb;
	}

	private void createNodes(List<SimpleInteraction<T>> interactionSet) throws NotInTransactionException {
		bimap = HashBiMap.create();
		Set<T> molecules = new HashSet<>();
		for (SimpleInteraction<T> triple : interactionSet) {
				molecules.add(triple.first);
				molecules.add(triple.second);
		}
		for (T molecule : molecules) {
			Node node = graphDb.createNode(NodeLabel.MOLECULE);
			bimap.put(molecule, node);
		}
	}
	
	private void createNodes(Set<Set<T>> interactionSet) throws NotInTransactionException {
		bimap = HashBiMap.create();
		for (T mol : getObjectSet(interactionSet)) {
			Node node = graphDb.createNode(NodeLabel.MOLECULE);
			bimap.put(mol, node);
		}
	}
/*
	public void createRelationships(Set<Interaction<T>> interactionSet) throws NotInTransactionException {
		createNodes(interactionSet);
		for (SimpleInteraction<T> triple : interactionSet) {
			Node node = bimap.get(triple.first);
			Node otherNode = bimap.get(triple.second);
			Relationship edge = node.createRelationshipTo(otherNode, Relations.INTERACT);
			edge.setProperty("weights", triple.weight);
		}
	}*/
	
	
	public void createRelationships(Set<Set<T>> interactionSet) throws NotInTransactionException {
		createNodes(interactionSet);
		for (Set<T> pair : interactionSet) {
			List<T> list = pair.stream().collect(Collectors.toList());
			Node node = bimap.get(list.get(0));
			Node otherNode = bimap.get(list.get(1));
			Relationship edge = node.createRelationshipTo(otherNode, Relations.INTERACT);
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
			for (T mol : pair)
				molecules.add(mol);
		}
		return molecules;
	}
	
	
	public List<Set<T>> convertCommunities(Map<String, Set<Node>> communities){
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
