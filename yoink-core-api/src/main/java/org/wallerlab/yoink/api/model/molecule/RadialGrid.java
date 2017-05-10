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

package org.wallerlab.yoink.api.model.molecule;

public interface RadialGrid {

	public abstract double getCore_cutdens();

	public abstract int[][] getNode_offsets();

	public abstract double[][] getCoefficients_of_first_derivative();

	public abstract double[][] getCoefficients_of_second_derivative();

	public abstract double getPrefactor_of_first_derivative();

	public abstract double getPrefactor_of_second_derivative();

	public abstract double getPosition_max();

	public abstract void setPosition_max(double position_max);

	public abstract double getSquare_position_max();

	public abstract void setSquare_position_max(double square_position_max);

	public abstract double getA();

	public abstract void setA(double a);

	public abstract double getB();

	public abstract void setB(double b);

	public abstract int getNgrid();

	public abstract void setNgrid(int ngrid);

	public abstract double[] getGrid_positions();

	public abstract void setGrid_positions(double[] grid_positions);

	public abstract double[] getGrid_values();

	public abstract void setGrid_values(double[] grid_values);

	public abstract double[] getFirst_derivative_of_grid_values();

	public abstract void setFirst_derivative_of_grid_values(
			double[] first_derivative_of_grid_values);

	public abstract double[] getSecond_derivative_of_grid_values();

	public abstract void setSecond_derivative_of_grid_values(
			double[] second_derivative_of_grid_values);

}
