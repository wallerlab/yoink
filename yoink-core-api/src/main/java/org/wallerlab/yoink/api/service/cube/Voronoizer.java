package org.wallerlab.yoink.api.service.cube;

import org.wallerlab.yoink.api.model.VoronoiPoint;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import static org.wallerlab.yoink.api.model.DensityPoint.DensityType;
import java.util.List;
import java.util.Set;

@FunctionalInterface
public interface Voronoizer {

    List<VoronoiPoint> voronoize(DensityType densityType,
                                 Set<MolecularSystem.Molecule> molecules,
                                 MolecularSystem molecularSystem);
}
