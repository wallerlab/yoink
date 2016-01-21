package org.wallerlab.yoink.adaptive.smooth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;

@Service("firesSmoothner")
public class FIRESmoothner implements Smoothner {

	@Resource
	Calculator<Double, Coord, Atom> distanceCalculator;

	@Override
	public void smooth(Job<JAXBElement> job) {
		List<Double> smoothFactors = new ArrayList<Double>();
		Region qmRegion=job.getRegions().get(Region.Name.QM);
		
		List<Atom> qmAtoms = qmRegion.getAtoms();
		Coord qmCenter = job.getRegions().get(Region.Name.QM_CORE)
				.getCenterOfMass();
		List<Double> distances = new ArrayList<Double>();
		for (Atom atom : qmAtoms) {
			double distance = distanceCalculator.calculate(qmCenter, atom);
			distances.add(distance);
		}
		double smoothFactor = Collections.max(distances);
		smoothFactors.add(smoothFactor);
		job.getProperties().put("smoothfactors", smoothFactors);

	}

}
