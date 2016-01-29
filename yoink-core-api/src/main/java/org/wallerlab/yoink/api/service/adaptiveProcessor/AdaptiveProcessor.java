package org.wallerlab.yoink.api.service.adaptiveProcessor;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * this interface is to process energy and force during adaptive qm/mm.
 * @author Min Zheng
 *
 */
public interface AdaptiveProcessor {

	/**
	 * this method is to process energy and force during adaptive qm/mm.
	 * @param job  - {@link org.wallerlab.yoink.api.model.bootstrap.Job}
	 */
	void run(Job<JAXBElement> job);

	/**
	 * This method calls an external program to get an energy
	 * @return energy
	 */
	double getEnergy();

	/**
	 * This method calls an external program to get the forces
	 * @return forces as a List of Vectors 
	 */
	List<Vector> getForces();

}
