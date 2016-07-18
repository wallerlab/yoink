package org.wallerlab.yoink.adaptive.domain;

import java.util.Set;

public class Configuration {

    private final Double qmWeight;

    private final Double bufferWeight;

    private final Set<BufferMolecule> bufferMolecules;

    public Configuration(final Double qmWeight, final  Double bufferWeight, final Set<BufferMolecule> bufferMolecules) {
        this.qmWeight = qmWeight;
        this.bufferWeight = bufferWeight;
        this.bufferMolecules = bufferMolecules;
    }

    public Double getQmWeight() {
        return qmWeight;
    }

    public Double getBufferWeight() {
        return bufferWeight;
    }

    public Set<BufferMolecule> getBufferMolecules() {
        return bufferMolecules;
    }
}
