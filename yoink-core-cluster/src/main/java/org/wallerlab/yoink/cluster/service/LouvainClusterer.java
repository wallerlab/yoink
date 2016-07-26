package org.wallerlab.yoink.cluster.service;

import org.wallerlab.yoink.api.model.Interaction;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.cluster.domain.cluster.Community;
import org.wallerlab.yoink.cluster.domain.cluster.CommunityFactory;
import org.wallerlab.yoink.cluster.domain.graph.LouvainRelation;
import org.wallerlab.yoink.cluster.service.graph.NodeAggregator;

import org.neo4j.ogm.model.Node;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.logging.Logger;

/**
 *  notation follows "Fast unfolding of communities in large networks" by Blondel et al.
 *  arXiv:0803.0476v2
 * 
 * @author marwin
 *
 */
public class LouvainClusterer {

	private Logger log = Logger.getLogger(LouvainClusterer.class.getName());

	// sum over all edge weights in graph
	private double m;

	private GraphDatabaseService graph;

	protected final String attributeName = "community";

	private RelationshipType edgeType;

	private long hierarchylevel;

	private Map<Long, Integer> communities;

	private List<List<Node>> nodesAtLevel;

	public LouvainClusterer(GraphDatabaseService graph, RelationshipType relationshipType) {
		this.graph = graph;
		this.edgeType = relationshipType;
		this.communities = new HashMap<>();
		this.hierarchylevel = 0;
		nodesAtLevel = new ArrayList<>();
	}



	public Job<JAXBElement> cluster(Job<JAXBElement> job, Set<Interaction> interactions) {
		int maximumNumberOfCommunities = (int) job.getParameter(Job.JobParameter.MAX_COMMS);
		Map<Long, Integer> result = cluster(maximumNumberOfCommunities);
		List<Set<Integer>> clusters = getResult(result.size() - 1);
		louvain.shutdown();
		job.setClusters(clusters);
		return job;
	}


	/**
	 *
	 *
	 * @param maxCommunities the max number of communities
	 * @return map of hierarchy level and corresponding number of communities {hierarchylevel : number of communites}
	 */
	public Map<Long, Integer> cluster(int maxCommunities) {
		System.out.println("in louvain region");
		try (Transaction tx = graphDb.beginTx()) {
			init();
			louvain(maxCommunities);
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

	public void shutdown(){service.shutdown();}



	// molecular modularity gain for community assignment
	private double deltaQ(Community community, Node node) {

		double sumIn = community.getSumOfInternalEdgeWeights();
		double sumTot = community.getTotalSumOfEdgeWeights();
		double nodeWeightedDegree = node.getDegree();
		double divisor = 2.0 * m;

		return (sumIn + community.getEdgeWeightSumTo(node))
				/ divisor
				- ((sumTot + nodeWeightedDegree) / divisor)
				* ((sumTot + nodeWeightedDegree) / divisor)
				- (sumIn / divisor - (sumTot / divisor) * (sumTot / divisor) - (nodeWeightedDegree / divisor)
						* (nodeWeightedDegree / divisor));
	}

	private double deltaQ(Node node, Node neighbor) {
		CommunityFactory factory = new CommunityFactory(graph, edgeType);
		Community community = factory.of(neighbor);
		return deltaQ(community, node);
	}

	/**
	 * initialize the graph by assigning a community label = nodeId to all nodes.
	 * 
	 */
	public void init() {
		for (Node node : graph.getAllNodes())
			init(node);
		m = 0.;
		for (Relationship edge : graph.getAllRelationships())
			if (edge.isType(edgeType))  m += (double) edge.getProperty("weights", 1.0);
	}

	/**
	 *  assign the initial community for each node, which is its id.
	 * @param node
	 */
	private void init(Node node) {
		long id = node.getId();
		node.setProperty(attributeName, "0#" + String.valueOf(id));
		node.addLabel(Label.label(String.valueOf("0#" + id)));
	}

	/**
	 * 
	 * 
	 * @param node
	 * @return modularity gain for the assignment
	 */
	private double communityAssignment(Node node) {
		CommunityFactory factory = new CommunityFactory(graph, edgeType);
		String currentCommunity = (String) node.getProperty(attributeName);
		Label bestCommunity = Label.label(String.valueOf(currentCommunity));	
		String bestCommunityId = currentCommunity;
		node.removeLabel(Label.label(String.valueOf(currentCommunity)));
		Community community = factory.of(currentCommunity);
		double deltaQmax = deltaQ(community, node);
		double deltaQold = deltaQmax;
		
		for (Relationship edge : node.getRelationships()) {
			Node neighbor = edge.getOtherNode(node);
			String communityId = (String) neighbor.getProperty(attributeName);
				community = factory.of(neighbor);
				double deltaQ = deltaQ(community, node);
				if (deltaQ > deltaQmax) {
					bestCommunity = community.getLabel();
					bestCommunityId = communityId;
					deltaQmax = deltaQ;

				}
		}
		node.addLabel(bestCommunity);
		node.setProperty(attributeName, bestCommunityId);
		return deltaQmax - deltaQold;

	}

	/**
	 * One iteration over all nodes to check whether by community assignment
	 * modularity can be improved
	 * 
	 * @param nodes
	 * @return modularity gain
	 */
	private double assignmentIterationStep(List<Node> nodes) {
		double Q_tot = 0;
		Collections.shuffle(nodes);
		for (Node node : nodes) {
			double ql = communityAssignment(node);
			Q_tot += ql;
		}
		return Q_tot;
	}

	/**
	 * Iterates all nodes until conversion
	 * 
	 * @param nodes
	 * @return
	 */
	private double assignmentIterator(List<Node> nodes) {
		int i = 0;
		double Q_old = 1;
		double precision = 0.00001;
		boolean converged = false;
		while (!converged & i < 1000) {
			double Qnew = assignmentIterationStep(nodes);
			double dQ = Math.abs(Qnew - Q_old);
			if (dQ < precision) converged = true;
			i++;
			Q_old = Qnew;
		}
		return 0;
	}

	public void louvain(int maxCommunities) {

		hierarchylevel = 0;
		List<Node> nodes = new ArrayList<>();

		for (Node a : graph.getAllNodes())
		    	nodes.add(a);

		Integer currentCommunities = nodes.size();

		communities.put(hierarchylevel, currentCommunities);
		nodesAtLevel.add(nodes);
		NodeAggregator aggregator = new NodeAggregator(graph, edgeType);

		boolean converged = false;

		while (currentCommunities > maxCommunities & !converged) {
			assignmentIterator(nodes);
			hierarchylevel++;
			nodes = aggregator.aggregateCommunities(nodes, hierarchylevel);
			converged = currentCommunities == nodes.size();
			currentCommunities = nodes.size();
			nodesAtLevel.add(nodes);
			communities.put(hierarchylevel, currentCommunities);
			log.info("Iterations: " + hierarchylevel + ", Communities: "
					+ nodes.size() + (converged ? ", converged!" : ""));
		}

	}

	public Map<String, Set<Node>> gatherResult(int level) {

		Map<String, Set<Node>> communities = new HashMap<>();
		List<Node> nodes = nodesAtLevel.get(level);

		TraversalDescription traversal = graph
				.traversalDescription()
				.breadthFirst()
				.relationships(LouvainRelation.LOUVAIN_ACCUMULATED_BY, Direction.INCOMING)
				.evaluator(Evaluators.atDepth(level));

		for (Path path : traversal.traverse(nodes)) {
			String key = (String) path.startNode().getProperty("community");
			Node member = path.endNode();
			if (communities.containsKey(key)) {
				communities.get(key).add(member);
			} else {
				Set<Node> set = new HashSet<Node>();
				set.add(member);
				communities.put(key, set);
			}
		}
		return communities;
	}

	public Map<Long, Integer> communities() {
		return communities;
	}

}
