package org.wallerlab.yoink.region.service.partitioners

import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.DensityPoint
import org.wallerlab.yoink.api.model.VoronoiPoint
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.model.molecular.MolecularSystem.Molecule.Atom
import org.wallerlab.yoink.api.service.cube.Voronoizer
import org.wallerlab.yoink.api.service.density.DensityCalculator
import org.wallerlab.yoink.region.service.partitioners.DensityPartitioner

import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class DenistyPartitionerSpec extends Specification{

    def qmCore
    def searchMolecules
    def densityCalculator
    def voronoizer
    def molecularSystem
    def densityPartitioner

    def m1
    def m2
    def m3

    def setup(){

        m1 = createMolecule(1)
        m2 = createMolecule(2)
        m3 = createMolecule(3)

        qmCore = [m1] as Set
        searchMolecules = [m2,m3] as Set
        molecularSystem = Mock(MolecularSystem)
        def molecules = [m1,m2,m3] as Set
        molecularSystem.getMolecules() >> molecules

        voronoizer = Mock(Voronoizer)
        densityCalculator = Mock(DensityCalculator)

        densityPartitioner = new DensityPartitioner()
        densityPartitioner.densityCalculator = densityCalculator
        densityPartitioner.voronoizer = voronoizer


        def g1 = Mock(VoronoiPoint)
        def n1 = [m2,m3] as Set
        g1.getNearestMolecules() >> n1
        g1.getCoordinate() >> Mock(Coord)

        def g2 = Mock(VoronoiPoint)
        def n2 = [m2,m3] as Set
        g2.getNearestMolecules() >> n2
        g2.getCoordinate() >> Mock(Coord)

        def gridPoints = [g1,g2]

        voronoizer.voronoize(_, searchMolecules, molecularSystem) >> gridPoints
    }


    def "test strongly bound is working with all in QM core "() {

        when:"low density limit "
        def fullQmCore = [m1,m2,m3] as Set
        then:" no molecules are in set "
        densityPartitioner.stronglyBound( fullQmCore, searchMolecules,molecularSystem) == [] as Set
    }

    def "test strongly bound is working in low atomic density "() {

        when:"low density limit "
        densityCalculator.atomic(_, _)  >> 0.09
        then:" no molecules are in set "
        densityPartitioner.stronglyBound( qmCore, searchMolecules,molecularSystem) == [] as Set
    }

    def "test strongly bound is working in correct atomic density "() {

        when:"low density limit "
        densityCalculator.atomic(_, _)  >> 0.11
        densityCalculator.electronic(_,_) >> 0.01d
        then:" no molecules are in set "
        densityPartitioner.stronglyBound( qmCore, searchMolecules,molecularSystem) == [] as Set
    }

    def "test strongly bound is working in correct atomic density , correct density Ratio"() {

        when:"low density limit "
        densityCalculator.atomic(_, _)  >> 0.11
        densityCalculator.electronic(_,_) >> 0.07d
        densityCalculator.sedd(_,_) >> 2
        then:" no molecules are in set "
        densityPartitioner.stronglyBound( qmCore, searchMolecules,molecularSystem) == [m2,m3] as Set
    }

    def "test weakly bound is working in high density and correct dori"() {

        when:
        densityCalculator.electronic(_,_) >> 0.1d
        densityCalculator.dori(_,_) >> 0.95d

        then:
        densityPartitioner.weaklyBound( qmCore, searchMolecules,molecularSystem) == [m2,m3] as Set
    }

    def "test weakly bound is working in low density and correct dori"() {

        when:
        densityCalculator.electronic(_,_) >> 0.000001d
        densityCalculator.dori(_,_) >> 0.95d

        then:
        densityPartitioner.weaklyBound( qmCore, searchMolecules,molecularSystem) == [] as Set
    }

    def "test weakly bound is working in High density and low dori"() {

        when:
        densityCalculator.electronic(_,_) >> 0.1d
        densityCalculator.dori(_,_) >> 0.85d

        then:
        densityPartitioner.weaklyBound( qmCore, searchMolecules,molecularSystem) == [] as Set
    }


    def "test weakly bound is with both molecules already in qm region"() {

        when:
        densityCalculator.electronic(_,_) >> 0.1d
        densityCalculator.dori(_,_) >> 0.95d

        def fullQmCore = [m1,m2,m3] as Set
        then:
        densityPartitioner.weaklyBound( fullQmCore, searchMolecules,molecularSystem) == [] as Set
    }


    private MolecularSystem.Molecule createMolecule(int i) {
        def a1 = Mock(MolecularSystem.Molecule.Atom)
        def a2 = Mock(MolecularSystem.Molecule.Atom)
        def atoms = [a1, a2] as Set
        def molecule = Mock(MolecularSystem.Molecule)
        molecule.getAtoms() >>  atoms
        molecule
    }

    private MolecularSystem.Molecule.Atom createAtom(int i){
        return Mock(MolecularSystem.Molecule.Atom)
    }
}
