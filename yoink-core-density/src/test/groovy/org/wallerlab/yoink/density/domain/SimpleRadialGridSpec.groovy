package org.wallerlab.yoink.density.domain

import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class SimpleRadialGridSpec extends Specification{


    def simpleRadialGrid

    def setup(){
        double exponent                       = 0.01
        double zeta                           = 0.05
        int numberOfGrids                     = 1000
        double maxGridDistance                = 0.001
        double[] gridPositions                = [0.05,0.05,0.05]
        double[] gridValues                   = [0.01,0.01,0.01]
        double[] firstDerivativeOfGridValues  = [0.02,0.02,0.02]
        double[] secondDerivativeOfGridValues = [0.03,0.03,0.03]

       simpleRadialGrid = new SimpleRadialGrid( exponent,
                                                zeta,
                                                numberOfGrids,
                                                gridPositions,
                                                gridValues,
                                                firstDerivativeOfGridValues,
                                                secondDerivativeOfGridValues,
                                                maxGridDistance)
    }

    def "assert radial grid is working "() {
        expect:
           simpleRadialGrid.exponent        == 0.01
           simpleRadialGrid.zeta            == 0.05
           simpleRadialGrid.numberOfGrids   == 1000
           simpleRadialGrid.maxGridDistance == 0.001
           simpleRadialGrid.gridPositions   == [0.05,0.05,0.05]
           simpleRadialGrid.gridValues      == [0.01,0.01,0.01]
           simpleRadialGrid.firstDerivativeOfGridValues  == [0.02,0.02,0.02]
           simpleRadialGrid.secondDerivativeOfGridValues == [0.03,0.03,0.03]

    }
}
