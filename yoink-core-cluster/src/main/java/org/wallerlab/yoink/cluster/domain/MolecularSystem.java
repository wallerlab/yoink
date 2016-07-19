package org.wallerlab.yoink.cluster.domain;

import org.neo4j.ogm.annotation.GraphId;
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

    @GraphId
    private Long id;

    private String nameOfSystem;
    public Integer numberOfMolecules;
    public Integer numberOfAtoms;


    public String getNameOfSystem() {
        return nameOfSystem;
    }

    public void setNameOfSystem(String nameOfSystem) {
        this.nameOfSystem = nameOfSystem;
    }

    public Integer getNumberOfMolecules() {
        return numberOfMolecules;
    }

    public void setNumberOfMolecules(Integer numberOfMolecules) {
        this.numberOfMolecules = numberOfMolecules;
    }

    public Integer getNumberOfAtoms() {
        return numberOfAtoms;
    }

    public void setNumberOfAtoms(Integer numberOfAtoms) {
        this.numberOfAtoms = numberOfAtoms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MolecularSystem [nameOfSystem=" + nameOfSystem + ", numberOfMolecules=" + numberOfMolecules
                + ", numberOfAtoms=" + numberOfAtoms + " ]";
    }


}
