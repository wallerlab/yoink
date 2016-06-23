package org.wallerlab.yoink.adaptive.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.plugin.QmMmWrapper;
import org.wallerlab.yoink.api.service.math.Vector;

import static org.wallerlab.yoink.api.model.region.Region.Name.*;
/**
 * This class is to get adaptive forces in buffered force method.
 * 
 * @author Min Zheng
 *
 *
 */
@Service("buffer")
public class BufferSmoothner implements Smoothner {

	@Resource
	@Qualifier("qmmm")
	QmMmWrapper qmmmProcessor;

	@Resource
	@Qualifier("mm")
	QmMmWrapper mmProcessor;

	@Override
	public void smooth(Job<JAXBElement> job) {
		Map<Molecule, Integer> qmMoleculeMap = job.getRegions().get(QM).getMolecularMap();
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
