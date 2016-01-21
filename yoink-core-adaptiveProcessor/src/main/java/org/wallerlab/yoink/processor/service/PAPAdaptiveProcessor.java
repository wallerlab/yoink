package org.wallerlab.yoink.processor.service;

import java.util.ArrayList;
import java.util.Collection;
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
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.math.set.Subsets;

import com.google.common.primitives.Ints;

/**
 * This class is to get PAP adaptive energy and force
 * 
 * @author Min Zheng
 *
 */
@Service("pap")
public class PAPAdaptiveProcessor implements Smoothner {

	@Resource
	@Qualifier("qmmm")
	private AdaptiveProcessor qmmmProcessor;

	@Resource
	private SimpleVector3DFactory myVector3D;

	@Override
	public void smooth(Job<JAXBElement> job) {
		// initialize
		List<Double> lambda = (List<Double>) job.getProperties().get(
				"smoothfactors");
		Map<Molecule, Integer> bufferMoleculeMap = job.getRegions()
				.get(Region.Name.BUFFER).getMolecularMap();

		List<Integer> bufferIndices = new ArrayList<Integer>(
				bufferMoleculeMap.values());
		int bufferSize = bufferIndices.size();
		// run all qm/mm jobs
		Map<List<Integer>, Double> v_all = new HashMap<List<Integer>, Double>();
		Map<List<Integer>, List<Vector>> f_all = new HashMap<List<Integer>, List<Vector>>();
		Map<List<Integer>, Double> factor_all = new HashMap<List<Integer>, Double>();
		runAllQMMMCalculations(lambda, bufferIndices, v_all, f_all, factor_all);
		// get adaptive force and energy
		double v_adaptive = 0;
		List<Vector> f_adaptive = new ArrayList<Vector>();
		v_adaptive = calculateAdaptiveForceAndEnergy(bufferIndices, bufferSize,
				v_all, f_all, factor_all, v_adaptive, f_adaptive);
		// put adaptive force and energy into job.properties
		Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
		forcesAndEnergy.put(f_adaptive, v_adaptive);
		job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
	}

	private void runAllQMMMCalculations(List<Double> lambda,
			List<Integer> bufferIndices, Map<List<Integer>, Double> v_all,
			Map<List<Integer>, List<Vector>> f_all,
			Map<List<Integer>, Double> factor_all) {
		for (List<Integer> qmSet : Subsets.split(Ints.toArray(bufferIndices))) {
			double energy = qmmmProcessor.getEnergy();
			List<Vector> forces = qmmmProcessor.getForces();

			double smoothFactor = 1.0;
			for (Integer i : qmSet) {
				int index = bufferIndices.indexOf(i);
				smoothFactor *= lambda.get(index);

			}
			v_all.put(qmSet, energy);
			factor_all.put(qmSet, smoothFactor);

			f_all.put(qmSet, forces);

		}
	}

	private double calculateAdaptiveForceAndEnergy(List<Integer> bufferIndices,
			int bufferSize, Map<List<Integer>, Double> v_all,
			Map<List<Integer>, List<Vector>> f_all,
			Map<List<Integer>, Double> factor_all, double v_adaptive,
			List<Vector> f_adaptive) {
		double v_qm = (double) v_all.get(new ArrayList<>());
		List<Vector> f_qm = (List<Vector>) f_all.get(new ArrayList<>());
		Map<Integer, Double> v_temp_all = new HashMap<Integer, Double>();

		Map<Integer, List<Vector>> f_temp_all = new HashMap<Integer, List<Vector>>();

		int forceSize = f_qm.size();
		initializeForce(f_adaptive, forceSize);

		for (int i = 1; i <= bufferSize; i++) {

			for (int j = 1; j <= i; j++) {
				double v_temp = 0;
				List<Vector> f_temp = new ArrayList<Vector>();
				initializeForce(f_temp, forceSize);
				if (i == 1) {
					List<Integer> singleBuffer = new ArrayList<Integer>();
					List<ArrayList<Integer>> combination_all = Subsets.split(
							Ints.toArray(bufferIndices), 1);
					for (int index = 0; index < combination_all.size(); index++) {

						singleBuffer = combination_all.get(index);
						double current_factor = factor_all.get(singleBuffer);
						v_temp += (v_all.get(singleBuffer) - v_qm);
						v_adaptive += v_temp * current_factor;
						v_temp_all.put(singleBuffer.get(0), v_temp);

						for (int n = 0; n < f_qm.size(); n++) {

							Vector tempForce = f_all.get(singleBuffer).get(n)
									.subtract(f_qm.get(n));
							f_temp.add(n, f_temp.get(n).add(tempForce));

							Vector tempAF = f_temp.get(n).scalarMultiply(
									current_factor);
							f_adaptive.add(n, f_adaptive.get(n).add(tempAF));
						}
						f_temp_all.put(singleBuffer.get(0), f_temp);

					}
					for (Vector f : f_adaptive) {
						f = f.add(f_qm.get(f_adaptive.indexOf(f)));
					}
					v_adaptive += v_qm;

				}

				else {
					List<ArrayList<Integer>> combination_all = Subsets.split(
							Ints.toArray(bufferIndices), j);

					for (List<Integer> buffer : combination_all) {
						double mE = 0;
						double singleE = v_qm;

						List<Vector> mF = new ArrayList<Vector>();
						initializeForce(mF, forceSize);
						List<Vector> singleF = f_qm;
						for (int item : buffer) {
							singleE += v_temp_all.get(item);

							for (Vector f : singleF) {
								f = f.add(f_temp_all.get(item).get(
										singleF.indexOf(f)));
							}

						}
						mE = v_temp_all.get(buffer) - singleE;

						for (int n = 0; n < f_qm.size(); n++) {

							mF.add(n,
									f_temp_all.get(buffer).get(n)
											.subtract(singleF.get(n)));
						}
						for (int sizeSub = buffer.size() - 1; sizeSub > 1; sizeSub--) {
							List<ArrayList<Integer>> combination_sub_all = Subsets
									.split(Ints.toArray(buffer), sizeSub);

							for (List<Integer> subBuffer : combination_sub_all) {
								double singleE_sub = v_qm;

								List<Vector> singleF_sub = f_qm;
								for (int item : subBuffer) {
									singleE += v_temp_all.get(item);
									for (Vector f : singleF_sub) {
										f = f.add(f_temp_all.get(item).get(
												singleF_sub.indexOf(f)));
									}
								}
								mE = v_temp_all.get(subBuffer) - singleE_sub;
								for (int n = 0; n < f_qm.size(); n++) {

									mF.add(n, f_temp_all.get(subBuffer).get(n)
											.subtract(singleF_sub.get(n)));

								}
							}

						}

						double current_factor = factor_all.get(buffer);
						v_adaptive = mE * current_factor;
						for (int n = 0; n < f_qm.size(); n++) {

							f_adaptive.add(n,
									mF.get(n).scalarMultiply(current_factor));
						}
					}

				}
			}
		}
		return v_adaptive;
	}

	private void initializeForce(List<Vector> forces, int size) {
		for (int i = 0; i < size; i++) {
			Vector v = myVector3D.create(0, 0, 0);
			forces.add(i, v);
		}
	}
}
