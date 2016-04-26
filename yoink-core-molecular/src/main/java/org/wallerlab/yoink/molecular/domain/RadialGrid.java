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

package org.wallerlab.yoink.molecular.domain;

import java.util.List;

import org.wallerlab.yoink.api.model.molecular.Coord;

public class RadialGrid {

	int[][] node_offsets = new int[][] { 
			{ 0, -2, -5 }, 
			{ 1, -1, -4},
			{ 2, 0, -3 }, 
			{ 3, 1, -2 }, 
			{ 4, 2, -1}, 
			{ 5, 3, 0 }

	};// noef
	double[][] coefficients_of_first_derivative = new double[][] {
			{ -274, 6, -24 }, 
			{ 600, -60, 150 }, 
			{ -600, -40, -400 },
			{ 400, 120, 600 },
			{ -150, -30, -600 }, 
			{ 24, 4, 274 } };// coef1
	double[][] coefficients_of_second_derivative = new double[][] {
			{ 225, -5, -50 },
			{ -770, 80, 305 },
			{ 1070, -150, -780 },
			{ -780, 80, 1070 }, 
			{ 305, -5, -770 }, 
			{ -50, 0, 225 } };// coef2
	double prefactor_of_first_derivative = 1.0 / 120.0;// fac1
	double prefactor_of_second_derivative = 1.0 / 120.0;// fac2
	double a;// Logarithmic grid parameter ri = a * exp(b * (i-1))//a
	double b;// !< Logarithmic grid parameter ri = a * exp(b * (i-1))//b
	double position_max;// Max. grid distance// rmax
	double square_position_max;// Squared max. grid distance//rmax2
	int ngrid; // Number of grids// ngrid
	double[] grid_positions;// grids positions//r(:)
	double[] grid_values;// Grid values, f = 4*pi*r^2*rho //f(:)

	double[] first_derivative_of_grid_values;// fp(:)
	double[] second_derivative_of_grid_values;// fpp(:)
	double core_cutdens = 1E-12; // Cutoff contribution for core radial grids

	public double getCore_cutdens() {
		return core_cutdens;
	}

	public int[][] getNode_offsets() {
		return node_offsets;
	}

	public double[][] getCoefficients_of_first_derivative() {
		return coefficients_of_first_derivative;
	}

	public double[][] getCoefficients_of_second_derivative() {
		return coefficients_of_second_derivative;
	}

	public double getPrefactor_of_first_derivative() {
		return prefactor_of_first_derivative;
	}

	public double getPrefactor_of_second_derivative() {
		return prefactor_of_second_derivative;
	}

	public double getPosition_max() {
		return position_max;
	}

	public void setPosition_max(double position_max) {
		this.position_max = position_max;
	}

	public double getSquare_position_max() {
		return square_position_max;
	}

	public void setSquare_position_max(double square_position_max) {
		this.square_position_max = square_position_max;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getDistance_max() {
		return position_max;
	}

	public void setDistance_max(double distance_max) {
		this.position_max = distance_max;
	}

	public double getSquare_distance_max() {
		return square_position_max;
	}

	public void setSquare_distance_max(double square_distance_max) {
		this.square_position_max = square_distance_max;
	}

	public int getNgrid() {
		return ngrid;
	}

	public void setNgrid(int ngrid) {
		this.ngrid = ngrid;
	}

	public double[] getGrid_positions() {
		return grid_positions;
	}

	public void setGrid_positions(double[] grid_positions) {
		this.grid_positions = grid_positions;
	}

	public double[] getGrid_values() {
		return grid_values;
	}

	public void setGrid_values(double[] grid_values) {
		this.grid_values = grid_values;
	}

	public double[] getFirst_derivative_of_grid_values() {
		return first_derivative_of_grid_values;
	}

	public void setFirst_derivative_of_grid_values(
			double[] first_derivative_of_grid_values) {
		this.first_derivative_of_grid_values = first_derivative_of_grid_values;
	}

	public double[] getSecond_derivative_of_grid_values() {
		return second_derivative_of_grid_values;
	}

	public void setSecond_derivative_of_grid_values(
			double[] second_derivative_of_grid_values) {
		this.second_derivative_of_grid_values = second_derivative_of_grid_values;
	}

}
