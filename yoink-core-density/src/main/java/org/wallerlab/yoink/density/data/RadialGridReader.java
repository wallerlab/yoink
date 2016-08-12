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

package org.wallerlab.yoink.density.data;

import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.math.Constants;
import org.wallerlab.yoink.density.domain.RadialGrid;
import org.wallerlab.yoink.api.service.math.Matrix;
import org.wallerlab.yoink.density.domain.SimpleRadialGrid;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import static java.nio.file.Files.readAllLines;


/**
 * This class is needed to read in wfc files and then convert them to Radial Grid domain models.
 * Because this is only needed once, we can serialize the domain models once they are created.
 * Then when we want to use yoink in the future just de-serialize them.
 *
 * This code is left here, just in case the grids are updated one day.
 *
 *
 */
@Service
public class RadialGridReader {

    @Resource
    private SimpleMatrixFactory myMatrix;

    private static final double prefactorFirstDerivative = 1.0 / 120.0;   // fac1

    private static final double prefactorSecondDerivative = 2.0 / 120.0;  // fac2

    private static final double coreCutoff = 1E-12; // Cutoff contribution for core radial grids

    private static final int[][] nodeOffsets = new int[][]{
            {0, -2, -5},
            {1, -1, -4},
            {2, 0, -3},
            {3, 1, -2},
            {4, 2, -1},
            {5, 3, 0}};

    private static final double[][] coefficientsFirstDerivative = new double[][]{
            {-274, 6, -24},
            {600, -60, 150},
            {-600, -40, -400},
            {400, 120, 600},
            {-150, -30, -600},
            {24, 4, 274}};

    private static final double[][] coefficientsSecondDerivative = new double[][]{
            {225, -5, -50},
            {-770, 80, 305},
            {1070, -150, -780},
            {-780, 80, 1070},
            {305, -5, -770},
            {-50, 0, 225}};


    private final String FILEPATH = "/Users/waller/merge/yoink/yoink-core-density/src/main/java/org/wallerlab/yoink/density/data/radialGrids.serialized";

    public Map<Element,RadialGrid> read() throws IOException {

        //Save a lot of effort by reading in serialized version of the radial grids.
        String serializedFilePath = "/Users/waller/merge/yoink/yoink-core-density/src/main/java/org/wallerlab/yoink/density/data/radialGrids.serialized";
        File file = new File(serializedFilePath);
        String current = new java.io.File( "." ).getCanonicalPath();

        try{
            if(!file.isDirectory()) {
                InputStream inputFile = new FileInputStream(serializedFilePath);
                InputStream buffer = new BufferedInputStream(inputFile);
                ObjectInput input = new ObjectInputStream(buffer);
                return (Map<Element,RadialGrid>) input.readObject();
            }
        } catch (ClassNotFoundException e){
            System.out.println("Error deserializing radial grid" + e);
        }

        Map<Element,RadialGrid> radialGrids =  new EnumMap<>(Element.class);

        Files.walk(Paths.get(FILEPATH))
             .filter(Files::isRegularFile)
             .forEach(filePath -> {
                  String[] paths = filePath.toString().split("/");

                 //SOooOOoOOoO Ugly. Sorry
                 String fileNamePrefix = Arrays.asList(Arrays.asList(paths).get(paths.length-1).split("_")).get(0);
                 String elementName = fileNamePrefix.substring(0, 1).toUpperCase() + fileNamePrefix.substring(1);
                 Element element = Element.valueOf(elementName);

                 List<String> lines = null;
                 try {lines = readAllLines(filePath);
                     } catch (IOException e) {e.printStackTrace();}

                 int numberOfOrbitals = Integer.parseInt(lines.get(0).trim().split("\\s+")[0]);

                 double[][] orbitalOccupation = new double[1][numberOfOrbitals];
                 String[] line_three = lines.get(2).trim().split("\\s+");
                 for (int index = 0; index < line_three.length; index++)
                     orbitalOccupation[0][index] = Integer.parseInt(line_three[index]);

                 Matrix occupationNumbers = myMatrix.matrix();
                 occupationNumbers.array2DRowRealMatrix(orbitalOccupation);

                 Matrix chis = myMatrix.matrix(); //vector of Chi's

                 String[] line_four = lines.get(3).trim().split("\\s+");
                 double xMin = Double.parseDouble(line_four[0]); //always -6 ?
                 double zz = Double.parseDouble(line_four[1]);
                 double exponent = Double.parseDouble(line_four[2]); //always 0.002 ?
                 int numberOfGridPoints = Integer.parseInt(line_four[3]);

                 // Read the grid
                 double[] gridPosition = new double[numberOfGridPoints];
                 double[] density = new double[numberOfGridPoints];
                 double[][] coefficients = new double[1][numberOfOrbitals];

                 for (int j = 0; j < numberOfGridPoints; j++) {
                        String[] line = lines.get(4 + j).trim().split("\\s+");
                        gridPosition[j] = Double.parseDouble(line[0]);
                        for (int m = 1; m < line.length; m++)
                            coefficients[0][m - 1] = Double.parseDouble(line[m]);
                        chis.array2DRowRealMatrix(coefficients);
                        Matrix wfcSquared = chis.ebeMultiply(chis);
                        density[j] = wfcSquared.dotProduct(occupationNumbers);
                        if (density[j] / (4.0 * Constants.PI * Math.pow(gridPosition[j], 2)) < coreCutoff) {
                            numberOfGridPoints = j + 1;
                            break;
                        }
                 }
                 radialGrids.put(element,buildDensityAndDerivatives(xMin, zz, exponent, numberOfGridPoints, gridPosition));
             });
        return radialGrids;
     }

    private RadialGrid buildDensityAndDerivatives(double xMin, double zz, double exponent, int numberOfGridPoints, double[] gridPosition) {
        double[] gridValues = new double[numberOfGridPoints];
        double[] firstDerivativeOfGridValues = new double[numberOfGridPoints];
        double[] secondDerivativeOfGridValues = new double[numberOfGridPoints];

        for (int i = 0; i < numberOfGridPoints; i++) {
            int ic = 1;
            if (i <= 1) ic = 0;
            else if (i >= numberOfGridPoints - 3) ic = 2;
            for (int j = 0; j < 6; j++) {
                firstDerivativeOfGridValues[i] += coefficientsFirstDerivative[j][ic] * gridValues[i + nodeOffsets[j][ic]];
                secondDerivativeOfGridValues[i] += coefficientsSecondDerivative[j][ic] * gridValues[i + nodeOffsets[j][ic]];
            }
            firstDerivativeOfGridValues[i] *= prefactorFirstDerivative;
            secondDerivativeOfGridValues[i] *= prefactorSecondDerivative;

            double position = gridPosition[i];
            double r1 = 1.0 / position;
            double r2 = r1 * r1;
            double r3 = r2 * r1;
            double r4 = r3 * r1;
            double delta = 1.0 / exponent;

            gridValues[i] = r2 / (4.0 * Constants.PI);

            firstDerivativeOfGridValues[i] =
                    (firstDerivativeOfGridValues[i] * delta - 2.0 * gridValues[i]) *
                            r3 / (4.0 * Constants.PI);
            secondDerivativeOfGridValues[i] = (secondDerivativeOfGridValues[i] * (delta * delta)
                    - 5.0 * firstDerivativeOfGridValues[i] * delta + 6.0 * gridValues[i])
                    * r4 / (4.0 * Constants.PI);
        }

        double zeta = Math.exp(xMin) / zz;
        double maxGridDistance = gridPosition[numberOfGridPoints - 1];

        return new SimpleRadialGrid(exponent,
                                    zeta,
                                    numberOfGridPoints,
                                    gridPosition,
                                    gridValues,
                                    firstDerivativeOfGridValues,
                                    secondDerivativeOfGridValues,
                                    maxGridDistance);
    }

    public void setMyMatrix(SimpleMatrixFactory myMatrix) {this.myMatrix = myMatrix;}

}
