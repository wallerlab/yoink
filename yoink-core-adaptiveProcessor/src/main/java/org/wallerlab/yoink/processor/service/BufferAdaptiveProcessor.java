package org.wallerlab.yoink.processor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;

/**
 * This class is to get adaptive forces in buffered force method.
 * 
 * @author Min Zheng
 *
 *
 */
@Service("buffer")
public class BufferAdaptiveProcessor implements Smoothner {

	@Resource
	@Qualifier("qmmm")
	AdaptiveProcessor qmmmProcessor;

	@Resource
	@Qualifier("mm")
	AdaptiveProcessor mmProcessor;

	@Override
	public void smooth(Job<JAXBElement> job) {
		// initialize and run QM/MM and MM jobs
		Map<Molecule, Integer> qmMoleculeMap = job.getRegions()
				.get(Region.Name.QM).getMolecularMap();
		List<Integer> qmIndices = new ArrayList<Integer>(qmMoleculeMap.values());

		List<Vector> forces_QMMM = qmmmProcessor.getForces();

		List<Vector> forces_MM = mmProcessor.getForces();

		// put qm force into forces_MM
		for (int i = 0; i < qmIndices.size(); i++) {
			int qmIndex = qmIndices.get(i);
			forces_MM.set(qmIndex - 1, forces_QMMM.get(qmIndex - 1));
		}
		//put force into job.properties
		Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
		forcesAndEnergy.put(forces_MM, 0.0);
		job.getProperties().put("forcesAndEnergy", forcesAndEnergy);

	}

}
