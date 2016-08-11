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
package org.wallerlab.yoink.adaptive.services;

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.service.adaptive.Adaptive;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.plugin.QmMmWrapper;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.math.SetOps;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;
import org.wallerlab.yoink.adaptive.domain.Configuration;
import org.wallerlab.yoink.adaptive.domain.BufferMolecule;

import static org.wallerlab.yoink.api.model.Job.JobParameter.SMOOTHNER;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;
import static org.wallerlab.yoink.adaptive.services.Processors.Processor.NAME;
import static org.wallerlab.yoink.adaptive.services.Processors.Processor.NAME.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME.*;
import static org.wallerlab.yoink.adaptive.services.WeightFactors.WeightFactor.NAME.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFactors.SmoothFactor.NAME.DISTANCE_OR_DENSITY;

import java.util.*;
import java.util.stream.IntStream;
import javax.xml.bind.JAXBElement;
import com.google.common.primitives.Ints; //lets drop this dependency
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.wallerlab.yoink.molecule.service.IDistanceCalculator;

import static java.util.stream.Collectors.toList;

/**
 * TODO
 */
@Service
public class Processors implements Adaptive{

    @Resource
    @Qualifier("qmmm")
    private QmMmWrapper qmmmProcessor;

    @Resource
    @Qualifier("mm")
    private QmMmWrapper mmProcessor;

    @Resource
    private IDistanceCalculator distanceCalculator;

    @Resource
    private SimpleVector3DFactory vectorFactory;

    private WeightFactors weightFactors;

    private SmoothFactors smoothFactors;

    private SmoothFunctions smoothFunctions;

    private Map<Processor.NAME,Processor> processors;

    public Processor use(Processor.NAME name ){
        return this.processors.get(name);
    }

    public Processors(){
        this.processors = new EnumMap<>(NAME.class);
        this.processors.put(BF, bufferedForce);
        this.processors.put(FIRES, fires);
        this.processors.put(HOT_SPOT, hotspot);
        this.processors.put(CONFIG, config);
        this.processors.put(PAP, pap);
    }

    /**
     * @param job as input
     * @return Job completed job
     */
    public Job compute(Job job){
        return this.processors.get(job.getParameter(SMOOTHNER)).process(job);
    }

    /**
     * Adaptive forces in buffered force method.
     */
    Processor bufferedForce = job -> {
        List<Vector> forces_QMMM = qmmmProcessor.getForces();
        List<Vector> forces_MM = mmProcessor.getForces();
        //Copy forces from QM/MM to the MM region to "buffer" the force
        IntStream.range(0,job.getRegion(QM)
                 .getMolecules()
                 .size())
                 .forEach(index -> forces_MM.set(index, forces_QMMM.get(index)));
        //AdaptiveMolecularSystem ams =  new AdaptiveMolecularSystem(0.0,forces_MM);
        return job;
    };

    final double FORCE_CONSTANT=500;//unit in kcal/(‎Å* ‎Å)

    /**
     *  FIRES adaptive energy and forces.
     */
    Processor fires = job -> {
        double energy = qmmmProcessor.getEnergy();
        List<Vector> forces = qmmmProcessor.getForces();
        Coord qmCenter = job.getRegion(QM_CORE).getCenterOfMass();
        double boundary = job.getRegion(BUFFER)
                             .getMolecules()
                             .stream()
                             .mapToDouble(molecule ->
                                                job.getRegion(QM)
                                                   .getAtoms()
                                                   .stream()
                                                   .mapToDouble(atom ->
                                                                  distanceCalculator.distance(qmCenter, atom))
                                                   .max()
                                                   .getAsDouble())
                             .min()
                             .getAsDouble();

        double correction = 0;
        for (MolecularSystem.Molecule.Atom atom : job.getRegion(MM).getAtoms()){
            double distance = distanceCalculator.distance(qmCenter, atom);
            correction += FORCE_CONSTANT * Math.pow(distance - boundary, 2);
            int index = atom.getIndex() - 1;
            Vector atomCoord = atom.getCoordinate();
            //Clean this.
            double xForce = getForce(boundary, distance, atomCoord.getX());
            double yForce = getForce(boundary, distance, atomCoord.getY());
            double zForce = getForce(boundary, distance, atomCoord.getZ());
            Vector forceVector = vectorFactory.create(xForce, yForce, zForce);
            Vector forceCorrected = forces.get(index).add(forceVector);
            forces.set(index, forceCorrected);
        }
        energy += correction * 0.5;
      //return new AdaptiveMolecularSystem( energy, forces);
       return job;
    };

    private double getForce(double lambdaMin, double distance, double currentCoord) {
        return FORCE_CONSTANT * currentCoord * (distance - lambdaMin) * (1 / distance);
    }

    /**
     *  Adaptive energy and forces in hot-spot method.
     */
    Processor hotspot = job -> {

        List<BufferMolecule> moleculesInBuffer = new ArrayList<>(smoothFactors.use(DISTANCE_OR_DENSITY).compute(job,smoothFunctions.use(BROOKS)));

        List<Vector> qmmmForces = qmmmProcessor.getForces();
        List<Vector> mmForces = mmProcessor.getForces();

        // use forces for buffer region
        for (int index = 0; index < job.getRegion(BUFFER).getSize(); index++) {
            Vector qmForce = qmmmForces.get(index).scalarMultiply(    moleculesInBuffer.get(index).getLambda());
            Vector mmForce =   mmForces.get(index).scalarMultiply(1 - moleculesInBuffer.get(index).getLambda());
            qmmmForces.set(index, qmForce.add(mmForce));
        }

        // put qm force into mmForces
        for (int index = 0; index < job.getRegion(QM).getSize(); index++)
            mmForces.set(index - 1, qmmmForces.get(index - 1));

        //Refactor
        //  List<Molecule> molecules = qmmmProcessor.getForces(job.getMolecularSystem());

       /* lambdas.stream()
                         .forEach( lambda -> {
                             lambda.getMolecule().getQmmmForce().scalarMultiply(molecule.getLambda());
                             molecule.getMmForce().scalarMultiply(1 - molecule.getLambda());
                          });
        */

        // Refactor.
        /*job.getRegion(BUFFER)
                .getMolecules()
                .stream(molecule -> {
                    molecule.getQmForce().scalarMultiply(molecule.getLambda());
                    molecule.getMmForce().scalarMultiply(1-molecule.getLamba());
                });
        job.getRegion(QM).getMolecules().stream().map(molecule ->
                                                        molecule.getQmmmForces());*/

        //return new AdaptiveMolecularSystem(0.0, qmmmForces);
        return job;
    };


    /**
     * Adaptive energy and forces. It is used for ONIOM-XS/SCMP/DAS/SAP.
     */
    Processor config = job -> {

        Set<Configuration> xs = weightFactors.use(XS)
                                             .compute(job, smoothFactors.use(DISTANCE_OR_DENSITY),
                                                           smoothFunctions.use(MOROKUMA));

        Set<Configuration> das = weightFactors.use(DAS)
                                              .compute(job,smoothFactors.use(DISTANCE_OR_DENSITY),
                                                           smoothFunctions.use(BULO));

        Set<Configuration> sap = weightFactors.use(SAP)
                                              .compute(job,smoothFactors.use(DISTANCE_OR_DENSITY),
                                                           smoothFunctions.use(PERMUTED));

        Set<Configuration> scmp = weightFactors.use(WeightFactors.WeightFactor.NAME.SCMP)
                                               .compute(job,smoothFactors.use(DISTANCE_OR_DENSITY),
                                                            smoothFunctions.use(PERMUTED));

        double adaptiveEnergy = 0;
        List<Vector> adaptiveForces = IntStream.range(0,job.getRegion(SYSTEM)
                .getSize())
                .parallel()
                .mapToObj(i-> vectorFactory.create(0,0,0))
                .collect(toList());

        for (Configuration configuration : xs) {
            double energy = qmmmProcessor.getEnergy();
            List<Vector> forces = qmmmProcessor.getForces();
            adaptiveEnergy += energy;
            for (int i = 0; i < forces.size(); i++)
                adaptiveForces.set(i, adaptiveForces.get(i).add(
                                      forces.get(i).scalarMultiply(configuration.getQmWeight())));
        }

        Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
        forcesAndEnergy.put(adaptiveForces, adaptiveEnergy);
        job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
        //return new AdaptiveSystem
        return job;
    };

    /**
     * PAP adaptive energy and force
     *
     * @author Min Zheng
     *
     */
    public Processor pap = job -> {

        List<BufferMolecule> moleculesInBuffer =
                new ArrayList<>(smoothFactors.use(DISTANCE_OR_DENSITY).compute(job, smoothFunctions.use(PERMUTED))); //??? what did i do here?

        List<Integer> bufferIndices = Arrays.asList(0,1,2); //MADE UP to allow compilation.

        Map<List<Integer>, Double>       energies = new HashMap<List<Integer>, Double>();
        Map<List<Integer>, List<Vector>> forces = new HashMap<List<Integer>, List<Vector>>();

        Map<List<Integer>, Double>       weightFactors = new HashMap<List<Integer>, Double>();
        //This looks like a weight factor calculator ??
        for (List<Integer> qmSet : SetOps.split(Ints.toArray(bufferIndices))) {
            double smoothFactor = 1.0;
            for (Integer qmIndex : qmSet)
                smoothFactor *= moleculesInBuffer.get(bufferIndices.indexOf(qmIndex)).getLambda();
            // run all qm/mm jobs
            energies.put(qmSet, qmmmProcessor.getEnergy());
            forces.put(qmSet, qmmmProcessor.getForces());
            weightFactors.put(qmSet, smoothFactor);
        }

        // use adaptive force and energy
        double adaptiveEnergy = 0;
        double qmEnergy = energies.get(new ArrayList<>());

        List<Vector> qmForces = forces.get(new ArrayList<>());
        List<Vector> adaptiveForce = new ArrayList<Vector>();

        Map<Integer, Double> allTempEnergies = new HashMap<Integer, Double>();
        Map<Integer, List<Vector>> allTempForces = new HashMap<Integer, List<Vector>>();

        int forceSize = qmForces.size();
        initializeForce(adaptiveForce, forceSize);
        for (int i = 1; i <= moleculesInBuffer.size(); i++) {
            for (int j = 1; j <= i; j++) {
                double v_temp = 0;
                List<Vector> tempForce = new ArrayList<Vector>();
                initializeForce(tempForce, forceSize);
                //Clean this.
                if (i == 1) adaptiveEnergy = getAdaptiveEnergy(bufferIndices, energies, forces, weightFactors,
                                                               adaptiveEnergy, adaptiveForce, qmEnergy, qmForces,
                                                               allTempEnergies, allTempForces, v_temp, tempForce);
                else        adaptiveEnergy = getAdaptiveEnergy(bufferIndices, weightFactors, adaptiveEnergy,
                                                               adaptiveForce, qmEnergy, qmForces, allTempEnergies,
                                                               allTempForces, forceSize, j);
            }
        }
        // put adaptive force and energy into job.properties
        Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
        forcesAndEnergy.put(adaptiveForce, adaptiveEnergy);
        job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
        return job;
    };

    private double getAdaptiveEnergy(List<Integer> bufferIndices,
                                     Map<List<Integer>, Double> v_all,
                                     Map<List<Integer>, List<Vector>> f_all,
                                     Map<List<Integer>, Double> factor_all,
                                     double v_adaptive,
                                     List<Vector> adaptiveForce,
                                     double v_qm,
                                     List<Vector> qmForce,
                                     Map<Integer, Double> v_temp_all,
                                     Map<Integer, List<Vector>> f_temp_all,
                                     double v_temp,
                                     List<Vector> f_temp) {
        List<Integer> singleBuffer = new ArrayList<Integer>();
        List<ArrayList<Integer>> combinations = SetOps.split(Ints.toArray(bufferIndices), 1);
        for (int index = 0; index < combinations.size(); index++) {

            singleBuffer = combinations.get(index);
            double current_factor = factor_all.get(singleBuffer);
            v_temp += (v_all.get(singleBuffer) - v_qm);
            v_adaptive += v_temp * current_factor;
            v_temp_all.put(singleBuffer.get(0), v_temp);

            for (int n = 0; n < qmForce.size(); n++) {
                Vector tempForce = f_all.get(singleBuffer).get(n).subtract(qmForce.get(n));
                f_temp.add(n, f_temp.get(n).add(tempForce));
                Vector tempAF = f_temp.get(n).scalarMultiply(current_factor);
                adaptiveForce.add(n, adaptiveForce.get(n).add(tempAF));
            }
            f_temp_all.put(singleBuffer.get(0), f_temp);

        }
        for (Vector force : adaptiveForce) {
            force = force.add(qmForce.get(adaptiveForce.indexOf(force)));
        }
        v_adaptive += v_qm;
        return v_adaptive;
    }

    private double getAdaptiveEnergy(List<Integer> bufferIndices,
                                     Map<List<Integer>, Double> factor_all,
                                     double v_adaptive,
                                     List<Vector> f_adaptive,
                                     double v_qm,
                                     List<Vector> f_qm,
                                     Map<Integer, Double> v_temp_all,
                                     Map<Integer, List<Vector>> f_temp_all,
                                     int forceSize, int j) {
        List<ArrayList<Integer>> combinations = SetOps.split(Ints.toArray(bufferIndices), j);
        for (List<Integer> buffer : combinations) {
            double mE = 0;
            double singleE = v_qm;
            List<Vector> mF = new ArrayList<Vector>();
            initializeForce(mF, forceSize);
            List<Vector> singleF = f_qm;
            for (int item : buffer) {
                singleE += v_temp_all.get(item);
                for (Vector f : singleF)
                    f = f.add(f_temp_all.get(item).get(singleF.indexOf(f)));
            }
            mE = v_temp_all.get(buffer) - singleE;
            for (int n = 0; n < f_qm.size(); n++)
                mF.add(n, f_temp_all.get(buffer).get(n).subtract(singleF.get(n)));

            mE = getmE(v_qm, f_qm, v_temp_all, f_temp_all, buffer, mE, singleE, mF);
            double current_factor = factor_all.get(buffer);
            v_adaptive = mE * current_factor;
            for (int n = 0; n < f_qm.size(); n++)
                f_adaptive.add(n,mF.get(n).scalarMultiply(current_factor));
        }
        return v_adaptive;
    }

    private double getmE(double qmEnergy,
                         List<Vector> qmForce,
                         Map<Integer, Double> allTempEnergies,
                         Map<Integer, List<Vector>> allTempForces,
                         List<Integer> buffer,
                         double mE,
                         double singleE,
                         List<Vector> mF) {
        for (int sizeSub = buffer.size() - 1; sizeSub > 1; sizeSub--) {
            List<ArrayList<Integer>> allSubCombinations = SetOps.split(Ints.toArray(buffer), sizeSub);
            for (List<Integer> subBuffer : allSubCombinations) {
                double singleEnergy_sub = qmEnergy;
                List<Vector> singleForce_sub = qmForce;
                for (int item : subBuffer) {
                    singleE += allTempEnergies.get(item);
                    for (Vector force : singleForce_sub)
                        force = force.add(allTempForces.get(item).get(singleForce_sub.indexOf(force)));
                }
                mE = allTempEnergies.get(subBuffer) - singleEnergy_sub;
                for (int index = 0; index < qmForce.size(); index++)
                    mF.add(index, allTempForces.get(subBuffer).get(index)
                      .subtract(singleForce_sub.get(index)));
            }
        }
        return mE;
    }

    //Move back to matrix.
    private void initializeForce(List<Vector> forces, int size) {
        for (int i = 0; i < size; i++) {
            Vector v = vectorFactory.create(0, 0, 0);
            forces.add(i, v);
        }
    }

    @FunctionalInterface
    public interface Processor {

        Job<JAXBElement> process(Job<JAXBElement> job);

        enum NAME{ BF, FIRES, HOT_SPOT, CONFIG, PAP }
    }

}
