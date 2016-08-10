package org.wallerlab.yoink.cluster.service;

import org.wallerlab.yoink.cluster.data.InteractionRepo;
import org.wallerlab.yoink.cluster.domain.Community;
import org.wallerlab.yoink.cluster.domain.Interaction;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class CommunityService {

    @Autowired
    private InteractionRepo interactionRepo;

    private Community calculate(Set<Interaction> interactions) {
        double totalSum = interactions.stream().mapToDouble(Interaction::getWeight).sum();
        double internalSum = interactions.stream().mapToDouble(Interaction::getWeight).sum();
        return new Community(interactions, totalSum, internalSum);
    }


    /*
    * TraversalDescription traversal = graph.traversalDescription()
    * .relationships(edgeType) .evaluator(Evaluators.atDepth(1))
    * .evaluator(Evaluators.includeWhereEndNodeIs(incidentNode));
    */
    public double getEdgeWeightSumTo(Community community, Interaction interaction) {
        return 0.0;
    }
}
