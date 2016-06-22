package org.wallerlab.yoink.adaptive.services.smooth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
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
		List<Double> distances = new ArrayList<Double>();
		List<Atom> qmAtoms = job.getRegions().get(Region.Name.QM).getAtoms();
		Coord qmCenter = job.getRegions().get(Region.Name.QM_CORE).getCenterOfMass();

		double smoothFactor = qmAtoms.stream()
				.mapToDouble(atom -> {return distanceCalculator.calculate(qmCenter, atom);})
				.max()
				.getAsDouble();

		smoothFactors.add(smoothFactor);
		job.getProperties().put("smoothfactors", smoothFactors);

	}

}
