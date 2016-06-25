package org.wallerlab.yoink.api.service.cube;

import org.wallerlab.yoink.api.model.batch.JobParameter;
import org.wallerlab.yoink.api.model.cube.GridPoint;
import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.region.Region;

import java.util.List;
import java.util.Map;

/**
 * Created by waller on 25/06/16.
 */
public interface Voronoizer {

    List<GridPoint> voronoize(List<Region> regions,
                              List<JobParameter> parameters,
                              DensityPoint.DensityType densityType);
}
