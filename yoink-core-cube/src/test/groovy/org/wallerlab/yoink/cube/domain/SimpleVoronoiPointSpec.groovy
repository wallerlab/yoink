package org.wallerlab.yoink.cube.domain

import org.spockframework.compiler.model.Spec
import org.wallerlab.yoink.api.model.Coord
import org.wallerlab.yoink.api.model.molecular.MolecularSystem
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory
import org.wallerlab.yoink.molecule.domain.SimpleCoord
import spock.lang.Specification

/**
 * Created by waller on 12/08/16.
 */
class SimpleVoronoiPointSpec extends Specification{


    def m1, m2
    def molecules
    def a1, a2
    def atoms
    def coord

    def setup() {

        m1 = Mock(MolecularSystem.Molecule)
        m2 = Mock(MolecularSystem.Molecule)

        a1 = Mock(MolecularSystem.Molecule.Atom)
        a2 = Mock(MolecularSystem.Molecule.Atom)

        atoms = [a1,a2] as Set
        molecules = [m1, m2] as Set

        coord = new SimpleCoord(SimpleVector3DFactory.staticCreate(0.0,0.0,0.0))
    }


    def "test simple domain model"() {

    when:
     def simpleVoronoiPoint = new SimpleVoronoiPoint(coord,molecules,atoms)

    then:
      simpleVoronoiPoint.coordinate == coord
      simpleVoronoiPoint.nearestMolecules == molecules
      simpleVoronoiPoint.nearestAtoms ==  atoms
    }
}
