package org.wallerlab.yoink.region.service.partitioners

import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.Job
import org.wallerlab.yoink.api.model.VoronoiPoint
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.api.service.cube.Voronoizer
import org.wallerlab.yoink.api.service.density.DensityCalculator

import static org.wallerlab.yoink.api.model.adaptive.Region.Name.BUFFER
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.QM_ADAPTIVE

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class DenistyPartitionerSpec extends Specification{

    def qmCore
    def searchMolecules
    def densityCalculator
    def voronoizer
    def molecularSystem
    def densityPartitioner

    @Shared def m1 = createMolecule(1)
    @Shared def m2 = createMolecule(2)
    @Shared def m3 = createMolecule(3)

    def setup(){

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

    def "test the overall partitioner"() {

        given:
        def dp = new DummyDensityPartitioner()

        def job = Mock(Job)
        def ms = Mock(MolecularSystem)
        ms.getMolecules("QM_CORE") >> qmCore
        job.getMolecularSystem() >> ms

        expect:
        dp.partition(job).get(QM_ADAPTIVE).size() == 3
        dp.partition(job).get(BUFFER).size() == 2
    }

    @Unroll
    def "density partitioner with '#electronic' density "(){
        given:
        densityCalculator.electronic(_,_) >> electronic;

        molecularSystem.getMolecules("QM_CORE_FIXED") >> qmCore

        expect:
        densityPartitioner.densityPartitioner(molecularSystem) == result

        where:
            electronic        | result
            0.0000001d        | [[]      as Set,[]      as Set,[]      as Set]
            0.000002d         | [[]      as Set,[]      as Set,[m2,m3] as Set]
            0.0002d           | [[]      as Set,[m2,m3] as Set,[m2,m3] as Set]
            0.2d              | [[m2,m3] as Set,[m2,m3] as Set,[m2,m3] as Set]
    }

    def "test strongly bound is working  with full Qm "() {
       when:"low density limit "
        def fullQmCore = [m1,m2,m3] as Set

      then:" no molecules are in set "
        densityPartitioner.stronglyBound( fullQmCore, searchMolecules,molecularSystem) == [] as Set

    }

    @Unroll
    def "Strongly bound with atomic = '#atomic' electronic = '#electronic' and sedd = '#sedd' "() {

        given:
          densityCalculator.atomic(_, _)    >> atomic
          densityCalculator.electronic(_,_) >> electronic
          densityCalculator.sedd(_,_)       >> sedd

        expect:" no molecules are in set "
           densityPartitioner.stronglyBound( qmCore, searchMolecules,molecularSystem) == result

        where:
             atomic  | electronic  |  sedd       |       result
             0.09d   | null        |   null      |    []      as Set
             0.011d  | 0.01d       |   null      |    []      as Set
             0.2d    | 0.1d        |   1.5d      |    [m2,m3] as Set
             0.2d    | 0.1d        |   3.0d      |    []      as Set
    }

    def "test weakly bound with already in qm region"() {

        when:
          def fullQmCore = [m1,m2,m3] as Set
        then:
          densityPartitioner.weaklyBound( fullQmCore, searchMolecules,molecularSystem) == [] as Set
    }


    @Unroll
    def " weakly bound  with electronic =  '#electronic' and DORI = '#dori'  "() {

        given:
          densityCalculator.electronic(_, _)  >> electronic
          densityCalculator.dori(_,_) >> dori

        expect:" no molecules are in set "
          densityPartitioner.weaklyBound( qmCore, searchMolecules, molecularSystem) == result

        where:
          electronic  |   dori            |     result
          0.1d        |   0.95d           |     [m2,m3]    as Set
          0.1d        |   0.85d           |     []         as Set
          0.000001d   |   0.95d           |     []         as Set
          0.1d        |   0.95d           |     [m2,m3]    as Set
    }

    //helper
    private MolecularSystem.Molecule createMolecule(int i) {
        def a1 = Mock(MolecularSystem.Molecule.Atom)
        def a2 = Mock(MolecularSystem.Molecule.Atom)
        def atoms = [a1, a2] as Set
        def molecule = Mock(MolecularSystem.Molecule)
        molecule.getAtoms() >>  atoms
        molecule
    }
    //helper
    private MolecularSystem.Molecule.Atom createAtom(int i){
        return Mock(MolecularSystem.Molecule.Atom)
    }

    //helper - this is to avoid us setting up a big test.
   class DummyDensityPartitioner extends DensityPartitioner{

           @Override
           protected List<Set<MolecularSystem.Molecule>> densityPartitioner(MolecularSystem molecularSystem){
               return [[m2,m3] as Set,[m2,m3] as Set,[m2,m3] as Set]
           }

           @Override
           protected  Set<MolecularSystem.Molecule> stronglyBound(Set<MolecularSystem.Molecule> qmFixedMolecules,
                                                               Set<MolecularSystem.Molecule> searchMolecules,
                                                               MolecularSystem molecularSystem) {
               return [m2,m3] as Set
           }

           @Override
           protected Set<MolecularSystem.Molecule> weaklyBound(Set<MolecularSystem.Molecule> qmCore,
                                                               Set<MolecularSystem.Molecule> searchMolecules,
                                                               MolecularSystem molecularSystem){
               return [m2,m3] as Set
           }
    }

}
