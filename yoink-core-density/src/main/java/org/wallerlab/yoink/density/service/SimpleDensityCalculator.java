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

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.density.DensityCalculator;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.density.data.RadialGridReader;
import org.wallerlab.yoink.density.domain.ExponentialFit;
import org.wallerlab.yoink.density.domain.RadialGrid;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.density.domain.SimpleDensityPoint;

import java.util.Set;
import java.util.Map;
import java.io.IOException;
import javax.annotation.PostConstruct;
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


	public static final double DENSITY_DEFAULT = 1.0E-30;
	public static final double DISTANCE_DEFAULT = 1.0E-10;
	public static final double RDG_COEFFICIENT = 6.187335452560543;

	@Resource
	private SimpleMatrixFactory matrixFactory;

	@Resource
	private SimpleVector3DFactory vectorFactory;

	@Resource
	RadialGridReader radialGridReader;

	private Map<Element,RadialGrid> radialGrids;

	public Double electronic(Coord coord, Set<MolecularSystem.Molecule> molecules) {
		Double density = molecules.stream()
								  .flatMap(molecule -> molecule.getAtoms().stream())
								  .mapToDouble(atom -> atomic(coord, atom))
								  .sum();
		return Math.max(density, DENSITY_DEFAULT);
	}

	public Double molecular(Coord currentCoord, MolecularSystem.Molecule molecule) {
		return molecule.getAtoms()
				       .stream()
			           .mapToDouble(atom -> atomic(currentCoord, atom))
					   .sum();
	}

	public Double atomic(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms){
		return atoms.stream()
				    .mapToDouble(atom -> atomic(coord, atom))
				    .sum();
	}

    //Select Density
	private double atomic(Coord coord, MolecularSystem.Molecule.Atom atom) {
		if (atom.getElement().atomNumber() <  18)
			return  exponentialFitDensity(coord, atom);
		return radialDensity(coord,atom);
	}

	private double exponentialFitDensity(Coord coord, MolecularSystem.Molecule.Atom atom) {
		double distance = coord.getCoords().distance(atom.getCoordinate());
		ExponentialFit element = ExponentialFit.valueOf(atom.getElement().toString());
		return element.C().dotProduct((element.invZ().scalarMultiply(-distance)).exp());
	}

	//D-GRID RADIAL DENSITY
	public double radialDensity(Coord coord, MolecularSystem.Molecule.Atom atom){

		double density          = 0.0;
		double firstDerivative  = 0.0;
		double secondDerivative = 0.0;

		double distance = coord.getCoords().distance(atom.getCoordinate());
		RadialGrid grid = radialGrids.get(atom.getElement());

		if (distance < grid.getMaxGridDistance()) {
			double[] gridPositions = grid.getGridPositions();
			double r_distance;
			int   ir;
			// careful with grid limits.
			if (distance <= gridPositions[0]) {
				r_distance = gridPositions[0];
				ir = 1;
			} else {
				r_distance = distance;
				ir = (int) (1 + Math.floor(Math.log(distance / grid.getExponent())/ grid.getZeta()));
			}

			double[]   gridPosition = new double[4];
			double[]   distanceToGridPosition = new double[4];
			double[][] x1dr12 = new double[4][4];

			for (int i = 0; i < 4; i++) {
				int index = (Math.min(Math.max(ir, 2), grid.getNumberOfGrids()) - 3 + i);
				gridPosition[i] = gridPositions[index];
				distanceToGridPosition[i] = r_distance - gridPosition[i];
				for (int j = 0; j <= i - 1; j++) {
					x1dr12[i][j] = 1.0 / (gridPosition[i] - gridPosition[j]);
					x1dr12[j][i] = -x1dr12[i][j];
				}
			}
			// interpolate, lagrange 3rd order, 4 nodes
			for (int i = 0; i < 4; i++) {
				int index = (Math.min(Math.max(ir, 2), grid.getNumberOfGrids()) - 3 + i);
				double prod = 1.0;
				for (int j = 0; j < 4; j++) {
					if (i == j)	continue;
					prod *= distanceToGridPosition[j] * x1dr12[i][j];
				}
				density          += grid.getGridValues()[index-1] * prod;
				firstDerivative  += grid.getFirstDerivativeOfGridValues()[index-1] * prod; //fac1 = -fp
				secondDerivative += grid.getSecondDerivativeOfGridValues()[index-1]* prod; //fac2 = fpp
			}
		}
		return density;
	}


	/**
	 * the density ratio of two atoms on a grid point.
	 *
	 * @param coordinate -{@link Coord}
	 * @param neighbours - an array of two atoms
	 *                   {@link MolecularSystem.Molecule.Atom}.
	 * @return the density ratio of two atoms in neighbours
	 */
	public Double ratio(Coord coordinate, MolecularSystem.Molecule.Atom[] neighbours) {
		return atomic(coordinate, neighbours[0])/ atomic(coordinate, neighbours[1]);
	}

	/**
	 * the density ratio of two molecules on a grid point.
	 *
	 * @param coordinate -{@link Coord}
	 * @param neighbours -an array of two molecules
	 *                   {@link MolecularSystem.Molecule}
	 * @return the density ratio of two molecules in neighbours
	 */
	public Double ratio(Coord coordinate, MolecularSystem.Molecule[] neighbours) {
		return molecular(coordinate, neighbours[0]) /molecular(coordinate, neighbours[1]);
	}

	/**
	 * This lambda is to get the reduced density gradient(RDG) value of a point. For
	 * RDG, see: Erin R.Johnson, Shahar Keinan, Paula Mori-Sanchez, Julia
	 * Contreras-Garcia, Aron J. Cohen, and Weitao Yang, J. Am. Chem. Soc. 2010,
	 * 132, pp 6498-6506.
	 * <p>
	 * molecular RDG of a density point
	 *
	 * @param coord  the point needed  for computation
	 * @return rdg value
	 */
	public Double rdg(Coord coord, Set<MolecularSystem.Molecule> molecules) {
		DensityPoint densityPoint = createDensityPoint(coord, getAtomsInMolecule(molecules));
		double rdg  = Math.sqrt(densityPoint.getGradient())/(Math.pow(densityPoint.getDensity(),4.0 / 3));
		rdg /= RDG_COEFFICIENT;
		return rdg;
	}

	/**
	 * This is to get the single exponential decay detector(SEDD) value of a
	 * grid point. For SEDD, see:De Silva, Piotr, Jacek Korchowiec, and Tomasz A.
	 * Wesolowski. "Revealing the Bonding Pattern from the Molecular Electron
	 * Density Using Single Exponential Decay Detector: An Orbital‐Free Alternative
	 * to the Electron Localization Function." ChemPhysChem 13.15 (2012): 3462-3465.
	 *
	 * molecular SEDD of a density point
	 *
	 * @return seddValue final-calculated
	 */
	public Double sedd(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms) {
		DensityPoint densityPoint = createDensityPoint(coord, atoms);
		double sedd = preFactor(densityPoint) * (4.0 / Math.pow(densityPoint.getDensity(), 8));
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
	 * @return doriValue, final-calculated
	 */
	public Double dori(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms) {
		DensityPoint densityPoint = createDensityPoint(coord, atoms);
		double dori = preFactor(densityPoint) * (4.0 / Math.pow(densityPoint.getGradient(), 3));
		dori /= (1.0 + dori);
		return dori;
	}

	/**
	 * molecular a density point's properties from atoms
	 *
	 * @param atoms -a Set of atoms
	 *              {@link MolecularSystem.Molecule.Atom}
	 * @param coord , the coordinate of the density point,
	 *              {@link Coord}
	 * @return densityPoint -
	 * {@link DensityPoint}
	 */
	public DensityPoint createDensityPoint(Coord coord, Set<MolecularSystem.Molecule.Atom> atoms) {

		Double density = 0d;
		Vector gradientVector = vectorFactory.create(0, 0, 0);
		Matrix hessian = matrixFactory.matrix3x3();

		for (MolecularSystem.Molecule.Atom atom : atoms) {
			Vector distanceVector = atom.getCoordinate().subtract(coord.getCoords());
			ExponentialFit expFit = ExponentialFit.valueOf(atom.getElement().toString());

			double distance = Math.max(distanceVector.getNorm(), DISTANCE_DEFAULT);
			Vector distanceUnitVector = distanceVector.scalarMultiply(1.0 / distance);

			double firstDerivative;
			double secondDerivative;

			Vector vector    = expFit.invZ().scalarMultiply(-distance).exp();
			density         += expFit.C().dotProduct(vector);
			firstDerivative  = expFit.Cz().dotProduct(vector);
			secondDerivative = expFit.Czz().dotProduct(vector);
			gradientVector.add(distanceUnitVector.scalarMultiply(firstDerivative * -1.0));
			hessian.add(getHessian(distanceVector, 1.0 / distance, distanceUnitVector, firstDerivative, secondDerivative));
		}

		density = Math.max(density, DENSITY_DEFAULT);
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
		for (int j = 0; j < 3; j++)
			for (int k = j + 1; k < 3; k++)
				hessian.setEntry(k, j, hessian.getEntry(j, k));
		return hessian;
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

	//Convienence method
	public Set<MolecularSystem.Molecule.Atom> getAtomsInMolecule(Set<MolecularSystem.Molecule> molecules){
		return molecules.stream()
						.flatMap(molecule -> molecule.getAtoms()
											         .stream())
				        .collect(toSet());
	}

	@PostConstruct
	private void readRadialGrids() throws IOException{
		this.radialGrids = radialGridReader.read();
	}

}


