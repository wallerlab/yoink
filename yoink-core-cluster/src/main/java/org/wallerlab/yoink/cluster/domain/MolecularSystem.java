package org.wallerlab.yoink.cluster.domain;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * This domain model is adjusted the molecular system of a pdbml file. Is set as
 * a neo4j entity.
 *
 * @author Christian Ouali Turki
 *
 */
@NodeEntity
public class MolecularSystem {

    private String nameOfSystem;

    public Integer numberOfMolecules;

    public Integer numberOfAtoms;

    //I think we need the interactions here.

    public MolecularSystem(String nameOfSystem, Integer numberOfMolecules, Integer numberOfAtoms) {
        this.nameOfSystem = nameOfSystem;
        this.numberOfMolecules = numberOfMolecules;
        this.numberOfAtoms = numberOfAtoms;
    }

    public String getNameOfSystem() {
        return nameOfSystem;
    }

    public Integer getNumberOfMolecules() {
        return numberOfMolecules;
    }

    public Integer getNumberOfAtoms() {
        return numberOfAtoms;
    }

    @Override
    public String toString() {
        return "MolecularSystem [nameOfSystem=" + nameOfSystem + ", numberOfMolecules=" + numberOfMolecules
                + ", numberOfAtoms=" + numberOfAtoms + " ]";
    }

}
