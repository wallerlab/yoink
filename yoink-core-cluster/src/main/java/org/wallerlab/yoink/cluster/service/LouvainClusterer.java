/*
package org.wallerlab.yoink.cluster.service;

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.cluster.data.CommunityRepo;
import org.wallerlab.yoink.cluster.data.InteractionRepo;
import org.wallerlab.yoink.cluster.domain.Community;
import org.wallerlab.yoink.cluster.domain.Interaction;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


*/
/**
 *  notation follows
 *  "Fast unfolding of communities in large networks" by Blondel et al. arXiv:0803.0476v2
 * 
 * @author marwin
 *
 *//*

@Service
public class LouvainClusterer {

	private Logger log = Logger.getLogger(LouvainClusterer.class.getName());

	@Autowired
	private CommunityService communityService;

	@Autowired
	private CommunityRepo communityRepo;

	@Autowired
	private InteractionRepo interactionRepo;

	public LouvainClusterer() {}

	public Job<JAXBElement> cluster(Job<JAXBElement> job, Set<Interaction> interactions) {
		int maximumNumberOfCommunities = (int) job.getParameter(Job.JobParameter.MAX_COMMS);
		Set<Community> communities = louvain(maximumNumberOfCommunities);
		communityRepo.save(communities);
		job.setClusters(communities);
		return job;
	}


	public Set<Community> louvain(int maxCommunities) {

		int hierarchyLevel = 0;
		List<Interaction> interactionsAtHierarchyLevel = new ArrayList<>();
		double sumOfAllEdgeWeights =  Stream.of(interactionRepo.findAll()).mapToDouble(Interaction::getWeight).sum();  //sum over all edge weights in graph

		Set<Interaction> interactions = new ArrayList<>(interactionRepo.findAll());
		Integer currentNumberOfCommunities = interactions.size();
		Set<Community> communities;
		boolean converged = false;

		while (currentNumberOfCommunities > maxCommunities & !converged) {
			assignmentIterator(interactions);
			hierarchyLevel++;
			communities = aggregator.aggregateCommunities(interactions, hierarchyLevel);
			converged = currentNumberOfCommunities == communities.size();
			currentNumberOfCommunities = interactions.size();
			log.info("Iterations: " + hierarchyLevel + ", Communities: " + interactions.size() + (converged ? ", converged!" : ""));
		}
		return communities;

	}
	*/
/**
	 * Iterates over all nodes until conversion
	 *
	 * @return
	 *//*

	private double assignmentIterator(List<Interaction> interactions) {
		int cycle = 0;
		double qOld = 1;
		double precision = 0.00001;
		boolean converged = false;
		while (!converged & cycle < 1000) {
			double qNew = 0;
			Collections.shuffle(interactions);
			*/
/*
			 * One iteration over all nodes to check whether community assignment
			 * modularity can be improved
			 *//*

			for (Interaction interaction : interactions)
				qNew += communityAssignment(interaction);
			double qDelta= Math.abs(qNew - qOld);
			if (qDelta < precision) converged = true;
			cycle++;
			qOld = qNew;
		}
		return 0;
	}

	*/
/**
	 * @return modularity gain for the assignment
	 *//*

	private double communityAssignment(Interaction interaction) {
		Community community = new Community();
		Community bestCommunity = new Community();

		double deltaQmax = deltaQ(community, interaction);
		double deltaQold = deltaQmax;

		for (Interaction interaction: interactions) {
			double deltaQ = deltaQ(community, interaction, sumOfAllEdgeWeights);
			if (deltaQ > deltaQmax) {
				bestCommunity = community;
				deltaQmax = deltaQ;
			}
		}
		community = bestCommunity ;
		return deltaQmax - deltaQold;
	}


	// molecular modularity gain for community assignment
	private double deltaQ(Community community, Interaction interaction, double sumOfAllEdgeWeights) {

		double sumOfInternalEdgeWeights = community.getSumOfInternalEdgeWeights();
		double totalSumOfEdgeWeights = community.getTotalSumOfEdgeWeights();
		double nodeWeightedDegree = interaction.getDegree();
		double divisor = 2.0 * sumOfAllEdgeWeights;

		return (sumOfInternalEdgeWeights + communityService.getEdgeWeightSumTo(community,interaction)) / divisor
				- ((totalSumOfEdgeWeights + nodeWeightedDegree) / divisor)
				* ((totalSumOfEdgeWeights + nodeWeightedDegree) / divisor)
				- (sumOfInternalEdgeWeights / divisor - (totalSumOfEdgeWeights / divisor)
				* (totalSumOfEdgeWeights    / divisor) - (nodeWeightedDegree / divisor)
				* (nodeWeightedDegree       / divisor));
	}

}
*/
