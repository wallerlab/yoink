package org.wallerlab.yoink.processor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Calculator;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.adaptiveProcessor.AdaptiveProcessor;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

/**
 * this class is to get FIRES adaptive energy and forces.
 * @author Min Zheng
 *
 */
@Service("fires")
public class FIRESAdaptiveProcessor implements Smoothner {


	@Qualifier("qmmm")
	private AdaptiveProcessor qmmmProcessor;
	

	@Resource
	private Calculator<Double, Coord, Atom> distanceCalculator;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Override
	public void smooth(Job<JAXBElement> job) {
		//initialize
		List<Double> lambda = (List<Double>) job.getProperties().get(
				"smoothfactors");
		double innerR = lambda.get(0);
		// get the qmmm energy and forces.
		double energy = qmmmProcessor.getEnergy();
		List<Vector> forces = qmmmProcessor.getForces();
		//adjust MM energy and forcd
		List<Atom> mmAtoms = job.getRegions().get(Region.Name.MM).getAtoms();
		Coord qmCenter = job.getRegions().get(Region.Name.QM_CORE)
				.getCenterOfMass();
		double eCorrection = 0;
		for (Atom atom : mmAtoms) {
			double r = distanceCalculator.calculate(qmCenter, atom);
			double e_r = Constants.FORCE_CONSTANT * Math.pow(r - innerR, 2);
			eCorrection += e_r;
			int index = atom.getIndex() - 1;
			Vector atomCoord = atom.getCoordinate().getCoords();
			double fx = getForce(innerR, r, atomCoord.getX());
			double fy = getForce(innerR, r, atomCoord.getY());
			double fz = getForce(innerR, r, atomCoord.getZ());
			Vector f_r = myVector3D.create(fx, fy, fz);
			Vector f_correction = forces.get(index).add(f_r);
			forces.set(index, f_correction);
		}
		energy += eCorrection * 0.5;
		//put correted force and energy into job.properties
		Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
		forcesAndEnergy.put(forces, energy);
		job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
	}

	private double getForce(double innerR, double r, double x) {
		return Constants.FORCE_CONSTANT * x * (r - innerR) * (1 / r);
	}
}
