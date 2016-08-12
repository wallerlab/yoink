package org.wallerlab.yoink.molecule.service.translator;
/**
 * this interface contains enum UnitConverterType
 *
 * @author Min Zheng
 *
 */
public  interface Converter {

    /**
     * unit convert coefficient for angstrom to bohr and bohr to angstrom
     *
     * @author Min Zheng
     *
     */
    enum UnitConverterType {

        AngstromToBohr(1.8897259885789), BohrToAngstrom(0.529177249);

        private final double constant;

        UnitConverterType(double constant) {
            this.constant = constant;
        }

        public double value() {
            return constant;
        }
    }

}