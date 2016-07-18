package org.wallerlab.yoink.api.service.cluster;

import org.wallerlab.yoink.api.model.Job;

import javax.xml.bind.JAXBElement;

/**
 * This is a way to cluster a molecular system.
 */
@FunctionalInterface
public interface Clusterer {

	/**
	 *
	 * @param job to perform clustering on
     */
	Job<JAXBElement> cluster(Job<JAXBElement> job);
}
