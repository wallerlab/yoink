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
package org.wallerlab.yoink.density.service;

import org.wallerlab.yoink.api.model.density.DensityPoint;
import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.service.density.DensityCalculator;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.density.domain.SimpleDensityPoint;

import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toSet;

/**
 * This class is to do Silva density analysis calculation for a point.
 *
 * Silva developed two density analysis methods (DORI and SEDD).
 *
 *
 * For DORI,see:De Silva, Piotr, and Clémence Corminboeuf.
 * "Simultaneous Visualization of Covalent and Noncovalent Interactions Using Regions of Density Overlap."
 * Journal of chemical theory and computation 10.9 (2014): 3745-3756.
 *
 * For SEDD, see:De Silva, Piotr, Jacek Korchowiec, and Tomasz A. Wesolowski.
 * "Revealing the Bonding Pattern from the Molecular Electron Density Using
 * Single Exponential Decay Detector: An Orbital‐Free Alternative to the
 * Electron Localization Function." ChemPhysChem 13.15 (2012): 3462-3465.
 *
 * @author Min Zheng
 *
 */
@Service
public class SimpleDensityCalculator implements DensityCalculator {

	protected static final Log log = LogFactory.getLog(SimpleDensityCalculator.class);

	@Resource
	private SimpleMatrixFactory matrixFactory;

	@Resource
	private SimpleVector3DFactory vectorFactory;

	@Resource
	private Map<ELEMENT,RadialGrid> radialGrids;

	public Double electronic(Coord coord, Set<Molecule> molecules) {
		Double density = molecules.stream()
								  .flatMap(molecule ->
													molecule.getAtoms()
															.stream())
								  .mapToDouble(atom ->
												  atomic(coord, atom))
								  .sum();
		return Math.max(density, Constants.DENSITY_DEFAULT);
	}

	public Double molecular(Coord currentCoord, Set<Molecule> molecules) {
		return null;
	}

	/*
	 * the density on a point from a molecule
	 */
	public Double molecular(Coord currentCoord, Molecule molecule) {
		return molecule.getAtoms()
				       .stream()
			           .mapToDouble(atom -> atomic(currentCoord, atom))
					   .sum();
	}

	public Double atomPair(Coord coord, Set<Atom> atoms){
		return atoms.stream()
				    .mapToDouble(atom -> atomic(coord, atom))
				    .sum();
	}

	private double atomic(Coord coord, Atom atom) {
		double density = 0;
		Element atomType = atom.getElementType();
		double distance = coord.getCoords().distance(atom.getCoordinate().getCoords());
		double exp1 = Math.exp(-distance / atomType.z1());
		double exp2 = Math.exp(-distance / atomType.z2());
		double exp3 = Math.exp(-distance / atomType.z3());
		density = atomType.c1() * exp1 + atomType.c2() * exp2 + atomType.c3() * exp3;
		//density = C.dotProduct(Z.scalarMultiply(-distance).exp());
		return density;
	}


	/**
	 * This lambda is to get the reduced density gradient(RDG) value of a point. For
	 * RDG, see: Erin R.Johnson, Shahar Keinan, Paula Mori-Sanchez, Julia
	 * Contreras-Garcia, Aron J. Cohen, and Weitao Yang, J. Am. Chem. Soc. 2010,
	 * 132, pp 6498-6506.
	 * <p>
	 * molecular RDG of a density point
	 *
	 * @param densityPoint -{@link DensityPoint}
	 * @return rdg value
	 */
	public Double rdg(Coord coord, Set<Molecule> molecules) {
		double rdg = 0;
		DensityPoint densityPoint = createDensityPoint(coord, getAtomsInMolecule(molecules));
		rdg = Math.sqrt(densityPoint.getGradient())/(Math.pow(densityPoint.getDensity(),4.0 / 3));
		rdg /= Constants.RDG_COEFFICIENT;
		return rdg;
	}

	/**
	 * This is to get the single exponential decay detector(SEDD) value of a
	 * grid point. For SEDD, see:De Silva, Piotr, Jacek Korchowiec, and Tomasz A.
	 * Wesolowski. "Revealing the Bonding Pattern from the Molecular Electron
	 * Density Using Single Exponential Decay Detector: An Orbital‐Free Alternative
	 * to the Electron Localization Function." ChemPhysChem 13.15 (2012): 3462-3465.
	 * <p>
	 * <p>
	 * molecular SEDD of a density point
	 *
	 * @param densityPoint -{@link DensityPoint}
	 * @param seddValue    pre-calculated
	 * @return seddValue final-calculated
	 */
	public Double sedd(Coord coord, Set<Atom> atoms) {
		double sedd = 0;
		DensityPoint densityPoint = createDensityPoint(coord, atoms);
		sedd = preFactor(densityPoint) * (4.0 / Math.pow(densityPoint.getDensity(), 8));
		sedd = Math.log((1.0 + sedd));
		return sedd;
	}

	/**
	 * This is to get the density overlap regions indicator(DORI) value of a
	 * grid point. For DORI,see:De Silva, Piotr, and Clémence Corminboeuf.
	 * "Simultaneous Visualization of Covalent and Noncovalent Interactions Using Regions of Density Overlap."
	 * Journal of chemical theory and computation 10.9 (2014): 3745-3756.
	 * <p>
	 * molecular dori value of a grid point
	 *
	 * @param densityPoint -{@link DensityPoint}
	 * @param doriValue    , pre-calculated
	 * @return doriValue, final-calculated
	 */
	public Double dori(Coord coord, Set<Atom> atoms) {
		DensityPoint densityPoint = createDensityPoint(coord, atoms);
		double dori =0;
		dori = preFactor(densityPoint) * (4.0 / Math.pow(densityPoint.getGradient(), 3));
		dori /= (1.0 + dori);
		return dori;
	}

	/**
	 * molecular a density point's properties from atoms
	 *
	 * @param atoms -a Set of atoms
	 *              {@link Atom}
	 * @param coord , the coordinate of the density point,
	 *              {@link Coord}
	 * @return densityPoint -
	 * {@link DensityPoint}
	 */
	public DensityPoint createDensityPoint(Coord coord, Set<Atom> atoms) {

		Double density = 0d;
		Vector gradientVector = vectorFactory.create(0, 0, 0);
		Matrix hessian = matrixFactory.matrix3x3();

		for (Atom atom : atoms) {
			Vector distanceVector = atom.getCoordinate().getCoords().subtract(coord.getCoords());
			Element atomName = atom.getElementType();
			double distance = Math.max(distanceVector.getNorm(), Constants.DISTANCE_DEFAULT);
			double distanceReciprocal = 1.0 / distance;
			Vector distanceUnitVector = distanceVector.scalarMultiply(distanceReciprocal);
			double firstDerivative = 0;
			double secondDerivative = 0;
			if (atom.getRadialGrid() == null) {
				double exp1 = Math.exp(-distance / atomName.z1());
				double exp2 = Math.exp(-distance / atomName.z2());
				double exp3 = Math.exp(-distance / atomName.z3());
				density += atomName.c1() * exp1 + atomName.c2() * exp2 + atomName.c3() * exp3;
				firstDerivative = atomName.cz1() * exp1 + atomName.cz2() * exp2 + atomName.cz3() * exp3;
				secondDerivative = atomName.czz1() * exp1 + atomName.czz2() * exp2 + atomName.czz3() * exp3;
			}
			gradientVector.add(distanceUnitVector.scalarMultiply(firstDerivative * -1.0));
			hessian.add(getHessian(distanceVector, distanceReciprocal, distanceUnitVector, firstDerivative, secondDerivative));
		}

		density = Math.max(density, Constants.DENSITY_DEFAULT);
		DensityPoint densityPoint = new SimpleDensityPoint(coord, density, gradientVector.dotProduct(), gradientVector, hessian);

		return densityPoint;
	}

	private Matrix getHessian(Vector distanceVector,
							  double distanceReciprocal,
							  Vector distanceUnitVector,
							  double firstDerivative,
							  double secondDerivative) {
		Matrix hessian = matrixFactory.matrix3x3();
		Vector distanceUnitVectorSquared = distanceUnitVector.ebeMultiply(distanceUnitVector);
		double distanceReciprocalSquared = distanceReciprocal * distanceReciprocal;
		// hessian is a 3x3 symmetric matrix. Compute only the upper right part.
		for (int j = 0; j < 3; j++) {
			hessian.setEntry(j, j, (secondDerivative + (firstDerivative * distanceReciprocal)) *
					distanceUnitVectorSquared.getEntry(j) -
					(firstDerivative * distanceReciprocal));
			for (int k = j + 1; k < 3; k++) {
				hessian.setEntry(j, k, distanceReciprocalSquared *
						distanceVector.getEntry(j) *
						distanceVector.getEntry(k) *
						(secondDerivative + (firstDerivative * distanceReciprocal)));
			}
		}
		// Copy to the lower left part of hessian
		for (int j = 0; j < 3; j++) {
			for (int k = j + 1; k < 3; k++) {
				hessian.setEntry(k, j, hessian.getEntry(j, k));
			}
		}
		return hessian;
	}

	/**
	 * the density ratio of two atoms on a grid point.
	 *
	 * @param coordinate -{@link Coord}
	 * @param neighbours - an array of two atoms
	 *                   {@link Atom}.
	 * @return the density ratio of two atoms in neighbours
	 */
	public Double calculate(Coord coordinate, Atom[] neighbours) {
		double densityA = atomic(coordinate, neighbours[0]);
		double densityB = atomic(coordinate, neighbours[1]);
		return densityA / densityB;
	}

	/**
	 * the density ratio of two molecules on a grid point.
	 *
	 * @param coordinate -{@link Coord}
	 * @param neighbours -an array of two molecules
	 *                   {@link Molecule}
	 * @return the density ratio of two molecules in neighbours
	 */
	public Double calculate(Coord coordinate, Molecule[] neighbours) {
		double densityA = molecular(coordinate, neighbours[0]);
		double densityB = molecular(coordinate, neighbours[1]);
		return densityA / densityB;
	}

	/**
	 * molecular Silva density analysis value for a density point
	 *
	 * @return prefactor for a density point
	 */
	private Double preFactor(DensityPoint densityPoint) {
		double preFactor = 0.0;
		for (int i = 0; i < 3; i++) {
			double temp = 0.0;
			for (int j = 0; j < 3; j++)
				temp += densityPoint.getGradientVector().getEntry(j) * densityPoint.getHessian().getEntry(i, j);
			    preFactor += Math.pow((densityPoint.getDensity() * temp -
								densityPoint.getGradientVector().getEntry(i) * densityPoint.getGradient()), 2);
		}
		return preFactor;
	}

	public Set<Atom> getAtomsInMolecule(Set<Molecule> molecules){
		return molecules.stream()
						.flatMap(molecule ->
									molecule.getAtoms()
											.stream())
											.collect(toSet());
	}


	public radialDesnity(){

		double density = 0.0;
		RadialGrid grid = radialGrids.getR(element);

		if (distance < grid.getPosition_max()) {

			int   ir = 0;
			double r = 0;
			double[] grid_positions = grid.getGrid_positions();

			// careful with grid limits.
			if (distance <= grid_positions[0]) {
				ir = 1;
				r = grid_positions[0];
			} else {
				ir = (int) (1 + Math.floor(Math.log(distance / grid.getA())/ grid.getB()));
				r = distance;
			}

			double[] rr = new double[4];
			double[] dr1 = new double[4];
			double[][] x1dr12 = new double[4][4];

			for (int i = 0; i < 4; i++) {
				int ii = (Math.min(Math.max(ir, 2), grid.getNgrid()) - 3 + i);
				rr[i] = grid_positions[ii];
				dr1[i] = r - rr[i];
				for (int j = 0; j <= i - 1; j++) {
					x1dr12[i][j] = 1.0 / (rr[i] - rr[j]);
					x1dr12[j][i] = -x1dr12[i][j];
				}
			}
			// interpolate, lagrange 3rd order, 4 nodes
			for (int i = 0; i < 4; i++) {
				int ii = (Math.min(Math.max(ir, 2), grid.getNgrid()) - 3 + i);
				double prod = 1.0;
				for (int j = 0; j < 4; j++) {
					if (i == j)	continue;
					prod = prod * dr1[j] * x1dr12[i][j];
				}
				density += grid.getGrid_values()[ii] * prod;
			}
		}
		return density;
	}

}


