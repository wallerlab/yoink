package org.wallerlab.yoink.cube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.wallerlab.yoink.api.model.cube.VoronoiPoint;
import org.wallerlab.yoink.api.model.molecule.Coord;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.Factory;
import org.wallerlab.yoink.molecule.service.calculator.DistanceCalculator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by waller on 06/07/16.
 */
public class StreamingCube {

    @Resource
    @Qualifier("simpleCoordFactory")
    private Factory<Coord, double[]> coordFactory;

    @Autowired
    DistanceCalculator distanceCalculator;

    public List<VoronoiPoint> cube(){

        int[]numberOfXYZSteps = {10,10,10};

        int xMax =10;
        int yMax =10;
        int zMax =10;
        int xMin =0;
        int yMin =0;
        int zMin =0;

        double xSpacing = (xMax - xMin) / numberOfXYZSteps[0];
        double ySpacing = (yMax - yMin) / numberOfXYZSteps[1];
        double zSpacing = (zMax - zMin) / numberOfXYZSteps[2];

        List<Molecule> molecules = new ArrayList<>();

        List<VoronoiPoint> gridPoints = new ArrayList<>();

       for(int  i = 0; i< numberOfXYZSteps[0]; i++){
           for(int  j = 0; j< numberOfXYZSteps[1]; j++){
               for(int  k = 0; k< numberOfXYZSteps[2]; k++){
                   double[] xyz = {xSpacing * i, ySpacing * j, zSpacing * k};
                  /* Coord coord = coordFactory.create(xyz);

                   MixedPair<Molecule> nearest = closestMolecules(coord,molecules.get(0));

                   boolean bothNeighboursAreInNonQmCore = nonQmCoreRegion.containsAll((Set) moleculePair);
                   boolean bothNeighboursAreInQmCore = qmCoreRegion.containsAll((Set) moleculePair);
                   boolean invalid =  notNeighbourPair || bothNeighboursAreInNonQmCore || bothNeighboursAreInQmCore;

                   if(invalid){
                        gridPoints.add(new SimpleVoronoiPoint(new Coord(xPoint,yPoint,zPoint),nearest);
                   }*/
               }
           }
       }
       return gridPoints;
    }



    private Double closestAtom(Coord coord, Molecule molecule) {
       return molecule.getAtoms()
                      .stream()
                      .mapToDouble(atom -> {return distanceCalculator.calculate(coord,atom);})
                      .min()
                      .getAsDouble();
      }

}
