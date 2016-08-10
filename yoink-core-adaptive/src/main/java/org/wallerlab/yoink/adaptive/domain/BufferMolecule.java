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

import org.wallerlab.yoink.api.model.molecular.MolecularSystem;

import java.util.Optional;

public class BufferMolecule  {

    private final double lambda;

    private final MolecularSystem.Molecule molecule;

    private Double distanceToPoint;

    private Optional<Double> qmWeight;

    private Optional<Double> bufferWeight;

    public BufferMolecule(final MolecularSystem.Molecule molecule, final  double lambda) {
        this.molecule = molecule;
        this.lambda = lambda;
    }

    public void setQmWeight(double qmWeight) {
        this.qmWeight = Optional.of(qmWeight);
    }

    public void setDistanceToPoint(double distanceToPoint) {this.distanceToPoint = distanceToPoint;}

    public void setBufferWeight(double bufferWeight) {
        this.bufferWeight = Optional.of(bufferWeight);
    }

    public Double getLambda() {
        return lambda;
    }

    public Double getDistanceToPoint() {
        return distanceToPoint;
    }

    public MolecularSystem.Molecule getMolecule() {return molecule;}

    public Optional<Double> getQmWeight() {
        return qmWeight;
    }

    public Optional<Double> getBufferWeight() {
        return bufferWeight;
    }


}
