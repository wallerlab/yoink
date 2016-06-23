package org.wallerlab.yoink.api.service.cluster;

import org.wallerlab.yoink.api.model.batch.Job;

/**
 * This is a way to cluster a molecular system.
 */
public interface Clusterer {

	/**
	 *
	 * @param job to perform clustering on
     */
	void cluster(Job job);
}
