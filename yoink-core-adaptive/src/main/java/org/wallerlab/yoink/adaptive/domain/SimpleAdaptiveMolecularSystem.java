/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wallerlab.yoink.adaptive.domain;

import org.wallerlab.yoink.api.model.adaptive.AdaptiveMolecularSystem;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;

import java.util.List;

public class SimpleAdaptiveMolecularSystem implements AdaptiveMolecularSystem {

    final Double energy;

    final List<Vector> forces;

    final MolecularSystem molecularSystem;

    public SimpleAdaptiveMolecularSystem(Double energy, List<Vector> forces, MolecularSystem molecularSystem) {
        this.energy = energy;
        this.forces = forces;
        this.molecularSystem = molecularSystem;
    }

    @Override
    public Double getEnergy() {
        return energy;
    }

    @Override
    public List<Vector> getForces() {
        return forces;
    }

    @Override
    public MolecularSystem getMolecularSystem() {
        return molecularSystem;
    }
}
