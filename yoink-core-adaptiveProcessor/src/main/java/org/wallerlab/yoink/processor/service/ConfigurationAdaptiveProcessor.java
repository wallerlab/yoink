package org.wallerlab.yoink.processor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

/**
 * this class is to get adaptive energy and forces. It will be called by
 * ONIOM-XS/SCMP/DAS/SAP
 * 
 * @author Min Zheng
 *
 */
@Service("configuration")
public class ConfigurationAdaptiveProcessor implements Smoothner {

	@Resource
	@Qualifier("qmmm")
	AdaptiveProcessor qmmmProcessor;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Override
	public void smooth(Job<JAXBElement> job) {
		Map<List<Integer>, Double> molecularIndicesAndWeightFactor = (Map<List<Integer>, Double>) job
				.getProperties().get("weightfactors");

		double adaptiveEnergy = 0;
		List<Vector> adaptiveForces = new ArrayList<Vector>();
		int forceSize = job.getRegions().get(Region.Name.SYSTEM).getSize();

		initializeForce(adaptiveForces, forceSize);
		for (List<Integer> tempQMRegion : molecularIndicesAndWeightFactor
				.keySet()) {

			// get the qmmm energy and forces.
			double energy = qmmmProcessor.getEnergy();
			List<Vector> forces = qmmmProcessor.getForces();

			double weight = molecularIndicesAndWeightFactor.get(tempQMRegion);
			adaptiveEnergy += energy;

			for (int i = 0; i < forces.size(); i++) {

				Vector adaptiveForce = adaptiveForces.get(i).add(
						forces.get(i).scalarMultiply(weight));
				adaptiveForces.set(i, adaptiveForce);
			}

		}
		Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
		forcesAndEnergy.put(adaptiveForces, adaptiveEnergy);
		job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
	}

	private void initializeForce(List<Vector> forces, int size) {
		for (int i = 0; i < size; i++) {
			Vector v = myVector3D.create(0, 0, 0);
			forces.add(i, v);
		}
	}

}
