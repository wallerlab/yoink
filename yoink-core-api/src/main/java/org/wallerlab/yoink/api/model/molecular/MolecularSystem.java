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
package org.wallerlab.yoink.api.model.molecular;

import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.math.Vector;

import java.util.Set;

/**
 * this interface is for domain model MolecularSystem
 * 
 * @author Min Zheng
 *
 */
public interface MolecularSystem {

	/**
	 * get all the atoms in the molecule system
	 * 
	 * @return a list of atoms, -
	 *         {@link Molecule.Atom}
	 */
	Set<Molecule.Atom> getAtoms();

	/**
	 * get all molecules in the molecule system
	 * 
	 * @return a list of molecules, -
	 *         {@link Molecule}
	 */
	 Set<Molecule> getMolecules();

	/**
	 * get all molecules in the molecule system
	 * with a string query
	 *
	 * @param string the id of the molecule that you
	 *               are interested in.
	 * @return a list of molecules, -
	 *         {@link Molecule}
	 */
	Set<Molecule> getMolecules(String string);

	/**
     * this interface is for domain model Molecule
     *
     * @author Min Zheng
     *
     */
	interface Molecule {

        /**
         * get the name of molecule. it represents which region the molecule is in
         *
         * @return name, -
         *         {@link Region.Name}
         */
        Region.Name getName();

        /**
         * set the name of molecule.
         *
         * @param name
         *            -indicates which the molecule is in,
         *            {@link Region.Name}
         */
        void setName(Region.Name name);

        /**
         * get all atoms in the molecule
         *
         * @return a List of atoms,
         *         {@link Atom}
         */
        Set<Atom> getAtoms();

        /**
         * get the index of molecule in the molecule system
         *
         * @return molecule index, {@link Integer}
         */
        int getIndex();

        /**
         * get the center of mass of this molecule.
         *
         * @return center of mass, -
         *         {@link Coord}
         */
         Coord getCenterOfMass();

        /**
         * set the center of mass of this molecule.
         *
         * @param centerOfMass
         *            - {@link Coord}
         */
         void setCenterOfMass(Coord centerOfMass);


		/**
         * this interface is for domain model atom
         *
         * @author Min Zheng
         *
         */
		interface Atom {

            /**
             * get the element of this atom
             *
             * @return element of this atom, -
             *         {@link Element}
             */
            Element getElement();

            /**
             * get the index of this atom in molecule system
             *
             * @return atomic index in molecule system -{@link Integer}
             */
            int getIndex();

            /**
             * get the coordinate of this atom
             * @return {@link Coord}
             */
            Vector getCoordinate();


        }
	}
}
