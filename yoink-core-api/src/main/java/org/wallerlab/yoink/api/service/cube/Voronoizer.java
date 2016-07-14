package org.wallerlab.yoink.api.service.cube;

import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.VoronoiPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.MolecularSystem;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FunctionalInterface
public interface Voronoizer {

    List<VoronoiPoint> voronoize(DensityPoint.DensityType densityType,
                                 Set<Molecule> moleculesInAdaptiveSearchRegion,
                                 MolecularSystem molecularSystem);
}
