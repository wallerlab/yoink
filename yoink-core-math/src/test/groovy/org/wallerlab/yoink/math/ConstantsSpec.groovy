package org.wallerlab.yoink.math

import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class ConstantsSpec extends  Specification{

    def "make sure values are there"() {
        expect:
        Constants.ANGSTROM_TO_BOHR          == 1.8897259885789;
        Constants.BOHR_TO_ANGTROM           == 0.529177249;
        Constants.PI                        == 3.14159265359;
        Constants.COLUMN_NUMBER_IN_EACH_ROW == 6;
    }
}
