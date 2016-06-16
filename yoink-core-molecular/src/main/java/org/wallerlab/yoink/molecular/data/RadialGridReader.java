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

package org.wallerlab.yoink.molecular.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.molecular.RadialGrid;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.api.service.molecular.FilesReader;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory;
import org.wallerlab.yoink.molecular.domain.SimpleRadialGrid;

@Service
public class RadialGridReader implements FilesReader<RadialGrid, String> {

	@Resource
	private SimpleMatrixFactory myMatrix;

	@Override
	public RadialGrid read(String wfc_file, RadialGrid radial_grid) {
		List<String> lines = new ArrayList<String>();

		Matrix occ_electrons = myMatrix.matrix();
		Matrix wfcin = myMatrix.matrix();

		lines = read_wfc_lines(wfc_file);

		int num_orbitals = Integer
				.parseInt(lines.get(0).trim().split("\\s+")[0]);

		double[][] occ_electrons_array = new double[1][num_orbitals];
		String[] line_three = lines.get(2).trim().split("\\s+");
		for (int i = 0; i < line_three.length; i++) {
			occ_electrons_array[0][i] = Integer.parseInt(line_three[i]);
		}

		occ_electrons.array2DRowRealMatrix(occ_electrons_array);
		String[] line_four = lines.get(3).trim().split("\\s+");
		double xmin = Double.parseDouble(line_four[0]);
		double zz = Double.parseDouble(line_four[1]);
		double dx = Double.parseDouble(line_four[2]);
		int ngrid = Integer.parseInt(line_four[3]);

		// Read the grid and build the density
		double[][] temp_rr_array = new double[ngrid][3];
		double[] temp_r_array = new double[ngrid];
		double[][] wfcin_array = new double[1][num_orbitals];

		for (int j = 0; j < ngrid; j++) {
			String[] line = lines.get(4 + j).trim().split("\\s+");
			temp_r_array[j] = Double.parseDouble(line[0]);
			for (int m = 1; m < line.length; m++) {
				wfcin_array[0][m - 1] = Double.parseDouble(line[m]);

			}
			wfcin.array2DRowRealMatrix(wfcin_array);
			Matrix wfcin_square = wfcin.ebeMultiply(wfcin);
			temp_rr_array[j][0] = wfcin_square.dotProduct(occ_electrons);

			if (temp_rr_array[j][0]
					/ (4.0 * Constants.PI * Math.pow(temp_r_array[j], 2)) < radial_grid
						.getCore_cutdens()) {
				ngrid = j + 1;
				break;
			}

		}

		double[][] rr_array = new double[ngrid][3];

		double[] r_array = new double[ngrid];
		System.arraycopy(temp_rr_array, 0, rr_array, 0, ngrid);
		System.arraycopy(temp_r_array, 0, r_array, 0, ngrid);

		// calculate derivatives
		double[] first_derivative_of_grid_values = new double[ngrid];
		double[] second_derivative_of_grid_values = new double[ngrid];
		double[] grid_values = new double[ngrid];
		int[][] noef = radial_grid.getNode_offsets();
		double[][] coef1 = radial_grid.getCoefficients_of_first_derivative();
		double[][] coef2 = radial_grid.getCoefficients_of_second_derivative();
		double fac1 = radial_grid.getPrefactor_of_first_derivative();
		double fac2 = radial_grid.getPrefactor_of_second_derivative();
		for (int i = 0; i < ngrid; i++) {
			int ic = 1;
			if (i <= 1) {
				ic = 0;
			} else if (i >= ngrid - 3) {
				ic = 2;
			}
			for (int j = 0; j < 6; j++) {
				rr_array[i][1] = rr_array[i][1] + coef1[j][ic]
						* rr_array[i + noef[j][ic]][0];
				rr_array[i][2] = rr_array[i][2] + coef2[j][ic]
						* rr_array[i + noef[j][ic]][0];
			}
			rr_array[i][1] = rr_array[i][1] * fac1;
			rr_array[i][2] = rr_array[i][2] * fac2;
			double r = r_array[i];
			double r1 = 1.0 / r;
			double r2 = r1 * r1;
			double r3 = r2 * r1;
			double r4 = r3 * r1;
			double delta = 1.0 / dx;
			double delta2 = delta * delta;
			grid_values[i] = rr_array[i][0] * r2 / (4.0 * Constants.PI);
			first_derivative_of_grid_values[i] = (rr_array[i][1] * delta - 2.0 * rr_array[i][0])
					* r3 / (4.0 * Constants.PI);
			second_derivative_of_grid_values[i] = (rr_array[i][2] * delta2
					- 5.0 * rr_array[i][1] * delta + 6.0 * rr_array[i][0])
					* r4 / (4.0 * Constants.PI);
		}

		// fill grid info
		radial_grid.setGrid_positions(r_array);
		radial_grid.setA(Math.exp(xmin) / zz);
		radial_grid.setB(dx);
		radial_grid.setNgrid(ngrid);
		radial_grid.setPosition_max(r_array[ngrid - 1]);
		radial_grid.setSquare_position_max(Math.pow(r_array[ngrid - 1], 2));
		radial_grid
				.setFirst_derivative_of_grid_values(first_derivative_of_grid_values);
		radial_grid
				.setSecond_derivative_of_grid_values(second_derivative_of_grid_values);
		radial_grid.setGrid_values(grid_values);

		return radial_grid;
	}

	private List<String> read_wfc_lines(String wfc_file) {
		List<String> lines = new ArrayList<String>();
		File file = new File(wfc_file);
		try {
			lines = FileUtils.readLines(file);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return lines;
	}

}
