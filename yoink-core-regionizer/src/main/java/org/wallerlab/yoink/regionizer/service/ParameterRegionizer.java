package org.wallerlab.yoink.regionizer.service;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.api.service.regionizer.Regionizer;
import org.wallerlab.yoink.regionizer.domain.SimpleRegion;

/**
 * this class is for parameter based adaptive qm/mm partitioning (distance and
 * number). it is to get QM region, QM_ADAPTIVE region and buffer region
 * 
 * @author Min Zheng
 *
 */
public class ParameterRegionizer implements
		Regionizer<Map<Region.Name, Region>, Map<JobParameter, Object>> {

	@Resource
	protected Factory<Region, Region.Name> simpleRegionFactory;

	public Map<Region.Name, Region> regionize(Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters) {
		// initialize
		Region qmAdaptiveRegion = simpleRegionFactory
				.create(Region.Name.QM_ADAPTIVE);
		calculateQMAdaptiveAndBufferRegionRegion(qmAdaptiveRegion, regions,
				parameters);
		// qm region contains core and adaptive.
		calculateQMRegion(regions, qmAdaptiveRegion);
		return regions;
	}

	protected void calculateQMAdaptiveAndBufferRegionRegion(
			Region qmAdaptiveRegion, Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters) {
		Region bufferRegion = simpleRegionFactory.create(Region.Name.BUFFER);
		loopOverMoleculesInNonQmRegion(qmAdaptiveRegion, regions, parameters,
				bufferRegion);
		qmAdaptiveRegion.changeMolecularId();
		bufferRegion.changeMolecularId();
		regions.put(qmAdaptiveRegion.getName(), qmAdaptiveRegion);
		regions.put(bufferRegion.getName(), bufferRegion);
	}

	protected void loopOverMoleculesInNonQmRegion(Region qmAdaptiveRegion,
			Map<Region.Name, Region> regions,
			Map<JobParameter, Object> parameters, Region bufferRegion) {
		Coord centerCoord = regions.get(Region.Name.QM_CORE).getCenterOfMass();
		Set<Molecule> nonQMCoreMolecules = regions.get(Region.Name.NONQM_CORE)
				.getMolecules();
		checkEveryNonQMCoreMolecule(qmAdaptiveRegion, parameters, bufferRegion,
				centerCoord, nonQMCoreMolecules);
	}

	// rewrite in child class
	protected void checkEveryNonQMCoreMolecule(Region qmAdaptiveRegion,
			Map<JobParameter, Object> parameters, Region bufferRegion,
			Coord centerCoord, Set<Molecule> nonQMCoreMolecules) {

	}

	protected void calculateQMRegion(Map<Region.Name, Region> regions,
			Region qmAdaptiveRegion) {
		Region qmRegion = simpleRegionFactory.create(Region.Name.QM);
		Region qmCoreRegion = regions.get(Region.Name.QM_CORE);
		qmRegion.addAll(qmAdaptiveRegion.getMolecularMap());
		qmRegion.addAll(qmCoreRegion.getMolecularMap());
		regions.put(qmRegion.getName(), qmRegion);
	}

}
