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
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;
import org.springframework.beans.factory.annotation.Qualifier;
/**
 * this class is go get the adaptive energy and forces in hot-spot method.
 * 
 * @author Min Zheng
 *
 */
@Service("hotspot")
public class HOTSPOTAdaptiveProcessor implements Smoothner {

	@Resource
	@Qualifier("qmmm")
	AdaptiveProcessor qmmmProcessor;

	@Resource
	@Qualifier("mm")
	AdaptiveProcessor mmProcessor;

	@Override
	public void smooth(Job<JAXBElement> job) {
		// initialize and run qm/mm and mm calculation
		Map<Molecule, Integer> bufferMoleculeMap = job.getRegions()
				.get(Region.Name.BUFFER).getMolecularMap();
		List<Integer> bufferIndices = new ArrayList<Integer>(
				bufferMoleculeMap.values());
		Map<Molecule, Integer> qmMoleculeMap = job.getRegions()
				.get(Region.Name.QM).getMolecularMap();
		List<Integer> qmIndices = new ArrayList<Integer>(qmMoleculeMap.values());

		List<Vector> forces_QMMM = qmmmProcessor.getForces();

		List<Vector> forces_MM = mmProcessor.getForces();

		// get forces for buffer region
		List<Double> lambda = (List<Double>) job.getProperties().get(
				"smoothfactors");
		for (int i = 0; i < bufferIndices.size(); i++) {
			int bufferIndex = bufferIndices.get(i);
			double s = lambda.get(i);
			Vector qmForce = forces_QMMM.get(bufferIndex - 1).scalarMultiply(s);
			Vector mmForce = forces_MM.get(bufferIndex - 1).scalarMultiply(
					1 - s);
			Vector bufferForce = qmForce.add(mmForce);
			forces_MM.set(bufferIndex - 1, bufferForce);
		}

		// put qm force into forces_MM
		for (int i = 0; i < qmIndices.size(); i++) {
			int qmIndex = qmIndices.get(i);
			forces_MM.set(qmIndex - 1, forces_QMMM.get(qmIndex - 1));
		}

		// put adaptive force into job.properties
		Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
		forcesAndEnergy.put(forces_MM, 0.0);
		job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
	}

}
