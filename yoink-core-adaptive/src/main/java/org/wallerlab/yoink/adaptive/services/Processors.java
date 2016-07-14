package org.wallerlab.yoink.adaptive.services;

import com.google.common.primitives.Ints;
import org.wallerlab.yoink.adaptive.domain.SimpleAdaptiveMolecularSystem;
import org.wallerlab.yoink.api.model.adaptive.AdaptiveMolecularSystem;
import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.*;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecule.Calculator;
import org.wallerlab.yoink.api.service.plugin.QmMmWrapper;
import org.wallerlab.yoink.math.constants.Constants;
import org.wallerlab.yoink.math.linear.SimpleVector3DFactory;

import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static org.wallerlab.yoink.adaptive.services.Processors.Processor.NAME;
import static org.wallerlab.yoink.adaptive.services.Processors.Processor.NAME.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFactors.SmoothFactor.NAME.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME.*;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.wallerlab.yoink.math.set.Subsets;

@Service
public class Processors {

    @Resource
    @Qualifier("qmmm")
    private QmMmWrapper qmmmProcessor;

    @Resource
    @Qualifier("mm")
    private QmMmWrapper mmProcessor;

    @Resource
    private Calculator<Double, Coord, Atom> distanceCalculator;

    @Resource
    private SimpleVector3DFactory vectorFactory;

    @Autowired
    private WeightFactors weightFactors;

    @Autowired
    private SmoothFactors smoothFactors;

    @Autowired
    private SmoothFunctions smoothFunctions;

    @Autowired
    private Processor papProcessor;

    private Map<Processor.NAME,Processor> processors;

    public Processors(){
        this.processors = new EnumMap<>(NAME.class);
        this.processors.put(BF, bufferedForce);
        this.processors.put(FIRES, fires);
        this.processors.put(HOT_SPOT, hotspot);
        this.processors.put(CONFIG, config);
        this.processors.put(PAP, papProcessor);
    }

    public Processor use(Processor.NAME name ){
        return this.processors.get(name);
    }

    /**
     * Adaptive forces in buffered force method.
     */
    Processor bufferedForce = job -> {

        List<Vector> forces_QMMM = qmmmProcessor.getForces();
        List<Vector> forces_MM = mmProcessor.getForces();

        //Copy forces from QM/MM to the MM region to "buffer" the forece
        IntStream.range(0,job.getRegion(QM)
                 .getMolecules()
                 .size())
                 .forEach(index ->
                             forces_MM.set(index, forces_QMMM.get(index)));

        SimpleAdaptiveMolecularSystem ams =  new SimpleAdaptiveMolecularSystem(0.0,forces_MM);
        return job;
    };

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
                                                                  distanceCalculator.calculate(qmCenter, atom))
                                                   .max()
                                                   .getAsDouble())
                             .min()
                             .getAsDouble();

        double correction = 0;
        for (Atom atom : job.getRegion(MM).getAtoms()){
            double distance = distanceCalculator.calculate(qmCenter, atom);
            correction += Constants.FORCE_CONSTANT * Math.pow(distance - boundary, 2);
            int index = atom.getIndex() - 1;
            Vector atomCoord = atom.getCoordinate().getCoords();
            double xForce = getForce(boundary, distance, atomCoord.getX());
            double yForce = getForce(boundary, distance, atomCoord.getY());
            double zForce = getForce(boundary, distance, atomCoord.getZ());
            Vector forceVector = vectorFactory.create(xForce, yForce, zForce);
            Vector forceCorrected = forces.get(index).add(forceVector);
            forces.set(index, forceCorrected);
        }
        energy += correction * 0.5;

       SimpleAdaptiveMolecularSystem ams =  new SimpleAdaptiveMolecularSystem( energy, forces, job.getMolecularSystem());
        return job;
    };

    private double getForce(double lambdaMin, double distance, double currentCoord) {
        return Constants.FORCE_CONSTANT * currentCoord * (distance - lambdaMin) * (1 / distance);
    }


    /**
     *  Adaptive energy and forces in hot-spot method.
     */
    Processor hotspot = job -> {

        List<Double> lambdas = smoothFactors.use(DISTANCE_OR_DENSITY).compute(job,smoothFunctions.use(BROOKS));


        List<Vector> qmmmForces = qmmmProcessor.getForces();
        List<Vector> mmForces = mmProcessor.getForces();

        // use forces for buffer region
        for (int index = 0; index < buffer.size(); index++) {
            Vector qmForce = qmmmForces.get(index).scalarMultiply(lambdas.get(index));
            Vector mmForce = mmForces.get(index).scalarMultiply(1 - lambdas.get(index));
            qmmmForces.set(index, qmForce.add(mmForce));
        }

        // put qm force into mmForces
        for (int index = 0; index < qmMolecules.size(); index++)
            mmForces.set(index - 1, qmmmForces.get(index - 1));

        // Refactor.
        job.getRegion(BUFFER)
                .getMolecules()
                .stream(molecule -> {
                    molecule.getQmForce().scalarMultiply(molecule.getLambda());
                    molecule.getMmForce().scalarMultiply(1-molecule.getLamba());
                });
        job.getRegion(QM).getMolecules().stream().map(molecule ->
                                                        molecule.getQmmmForces());

        AdaptiveMolecularSystem ams = new SimpleAdaptiveMolecularSystem(0.0, qmmmForces );

    };


    /**
     * Adaptive energy and forces. It is used for ONIOM-XS/SCMP/DAS/SAP.
     */
    Processor config = job -> {

        //XS
        List<Double> XS = weightFactors.use(XS).smooth(job, smoothFactors
                                        .use(DISTANCE_OR_DENSITY), smoothFunctions.use(MOROKUMA));

        //DAS
        List<Double> DAS = weightFactors.use(DAS).smooth(job,smoothFactors
                                        .use(DISTANCE_OR_DENSITY), smoothFunctions.use(BULO));

        //SAP
        List<Double> SAP = weightFactors.use(SAP).smooth(job,smoothFactors
                                        .use(DISTANCE_OR_DENSITY), smoothFunctions.use(PERMUTED));

        //SCMP
        List<Double> SCMP = weightFactors.use(WeightFactors.WeightFactor.NAME.SCMP)
                                        .smooth(job,smoothFactors.use(DISTANCE_OR_DENSITY), smoothFunctions.use(PERMUTED));

        List<Vector> adaptiveForces = IntStream.range(0,job.getRegion(SYSTEM)
                                               .getSize())
                                               .mapToObj(i-> vectorFactory.create(0,0,0))
                                               .collect(toList());

        double adaptiveEnergy = 0;
        //Configuration
        for (List<Integer> tempQMRegion : molecularIndicesAndWeightFactor.keySet()) {
            double energy = qmmmProcessor.getEnergy();
            List<Vector> forces = qmmmProcessor.getForces();
            adaptiveEnergy += energy;
            for (int i = 0; i < forces.size(); i++)
                adaptiveForces.set(i, adaptiveForces.get(i).add(forces.get(i)
                                      .scalarMultiply(weightFactors.get(tempQMRegion))));
        }

        molecule.setEnergy(molecule.getEnergy + );
        molecule.setForce(molecule.getForce()* molecule.getLambda());


        Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
        forcesAndEnergy.put(adaptiveForces, adaptiveEnergy);
        job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
        return job;
    };

    /**
     * PAP adaptive energy and force
     *
     * @author Min Zheng
     *
     */
    Processor pap = job -> {

        List<Double> lambda = smoothFactors.use(DISTANCE_OR_DENSITY).compute(job, smoothFunctions.use(PERMUTED));

        Set<Molecule> moleculesInBuffer = job.getRegion(BUFFER).getMolecules();

        List<Integer> bufferIndices = Arrays.asList(0,1,2); //MADE UP

        int bufferSize = moleculesInBuffer.size();

        // run all qm/mm jobs
        Map<List<Integer>, Double>       energies = new HashMap<List<Integer>, Double>();
        Map<List<Integer>, List<Vector>> forces = new HashMap<List<Integer>, List<Vector>>();
        Map<List<Integer>, Double>       factors = new HashMap<List<Integer>, Double>();

        runAllQMMMCalculations(bufferIndices,lambda, energies, forces, factors);

        // use adaptive force and energy
        double adaptiveEnergy = 0;
        List<Vector> adaptiveForce = new ArrayList<Vector>();
        adaptiveEnergy = calculateAdaptiveForceAndEnergy(bufferIndices, bufferSize, energies, forces, factors, adaptiveEnergy, adaptiveForce);

        // put adaptive force and energy into job.properties
        Map<List<Vector>, Double> forcesAndEnergy = new HashMap<List<Vector>, Double>();
        forcesAndEnergy.put(adaptiveForce, adaptiveEnergy);
        job.getProperties().put("forcesAndEnergy", forcesAndEnergy);
        return;
    };



    private void runAllQMMMCalculations(List<Integer> bufferIndices,
                                        List<Double> lambda,
                                        Map<List<Integer>, Double> adaptiveEnery,
                                        Map<List<Integer>, List<Vector>> adaptiveForce,
                                        Map<List<Integer>, Double> factor_all) {

        for (List<Integer> qmSet : Subsets.split(Ints.toArray(bufferIndices))) {
            double energy = qmmmProcessor.getEnergy();
            List<Vector> forces = qmmmProcessor.getForces();

            double smoothFactor = 1.0;
            for (Integer qmIndex : qmSet)
                smoothFactor *= lambda.get(bufferIndices.indexOf(qmIndex));

            adaptiveEnery.put(qmSet, energy);
            factor_all.put(qmSet, smoothFactor);
            adaptiveForce.put(qmSet, forces);
        }
    }

    private double calculateAdaptiveForceAndEnergy(List<Integer> bufferIndices,
                                                   int bufferSize,
                                                   Map<List<Integer>,Double> v_all,
                                                   Map<List<Integer>, List<Vector>> f_all,
                                                   Map<List<Integer>, Double> factor_all, double v_adaptive,
                                                   List<Vector> adaptiveForce) {
        double v_qm = (double) v_all.get(new ArrayList<>());
        List<Vector> f_qm = (List<Vector>) f_all.get(new ArrayList<>());
        Map<Integer, Double> v_temp_all = new HashMap<Integer, Double>();

        Map<Integer, List<Vector>> f_temp_all = new HashMap<Integer, List<Vector>>();
        int forceSize = f_qm.size();

        initializeForce(adaptiveForce, forceSize);

        for (int i = 1; i <= bufferSize; i++) {
            for (int j = 1; j <= i; j++) {
                double v_temp = 0;
                List<Vector> tempForce = new ArrayList<Vector>();
                initializeForce(tempForce, forceSize);
                if (i == 1) v_adaptive = getAdaptiveEnergy(bufferIndices, v_all, f_all, factor_all, v_adaptive, adaptiveForce, v_qm, f_qm, v_temp_all, f_temp_all, v_temp, tempForce);
                else v_adaptive = getAdaptiveEnergy(bufferIndices, factor_all, v_adaptive, adaptiveForce, v_qm, f_qm, v_temp_all, f_temp_all, forceSize, j);
            }
        }
        return v_adaptive;
    }

    private double getAdaptiveEnergy(List<Integer> bufferIndices, Map<List<Integer>, Double> v_all, Map<List<Integer>, List<Vector>> f_all, Map<List<Integer>, Double> factor_all, double v_adaptive, List<Vector> f_adaptive, double v_qm, List<Vector> f_qm, Map<Integer, Double> v_temp_all, Map<Integer, List<Vector>> f_temp_all, double v_temp, List<Vector> f_temp) {
        List<Integer> singleBuffer = new ArrayList<Integer>();
        List<ArrayList<Integer>> combination_all = Subsets.split(Ints.toArray(bufferIndices), 1);
        for (int index = 0; index < combination_all.size(); index++) {

            singleBuffer = combination_all.get(index);
            double current_factor = factor_all.get(singleBuffer);
            v_temp += (v_all.get(singleBuffer) - v_qm);
            v_adaptive += v_temp * current_factor;
            v_temp_all.put(singleBuffer.get(0), v_temp);

            for (int n = 0; n < f_qm.size(); n++) {
                Vector tempForce = f_all.get(singleBuffer).get(n).subtract(f_qm.get(n));
                f_temp.add(n, f_temp.get(n).add(tempForce));
                Vector tempAF = f_temp.get(n).scalarMultiply(current_factor);
                f_adaptive.add(n, f_adaptive.get(n).add(tempAF));
            }
            f_temp_all.put(singleBuffer.get(0), f_temp);

        }
        for (Vector f : f_adaptive) {
            f = f.add(f_qm.get(f_adaptive.indexOf(f)));
        }
        v_adaptive += v_qm;
        return v_adaptive;
    }

    private double getAdaptiveEnergy(List<Integer> bufferIndices, Map<List<Integer>, Double> factor_all, double v_adaptive, List<Vector> f_adaptive, double v_qm, List<Vector> f_qm, Map<Integer, Double> v_temp_all, Map<Integer, List<Vector>> f_temp_all, int forceSize, int j) {

        List<ArrayList<Integer>> combinations = Subsets.split(Ints.toArray(bufferIndices), j);

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

    private double getmE(double v_qm,
                         List<Vector> qmForce,
                         Map<Integer, Double> v_temp_all,
                         Map<Integer, List<Vector>> allTempForces,
                         List<Integer> buffer,
                         double mE,
                         double singleE,
                         List<Vector> mF) {

        for (int sizeSub = buffer.size() - 1; sizeSub > 1; sizeSub--) {

            List<ArrayList<Integer>> combination_sub_all = Subsets.split(Ints.toArray(buffer), sizeSub);
            for (List<Integer> subBuffer : combination_sub_all) {
                double singleE_sub = v_qm;
                List<Vector> singleF_sub = qmForce;
                for (int item : subBuffer) {
                    singleE += v_temp_all.get(item);
                    for (Vector force : singleF_sub)
                        force = force.add(allTempForces.get(item).get(singleF_sub.indexOf(force)));
                }
                mE = v_temp_all.get(subBuffer) - singleE_sub;

                for (int index = 0; index < qmForce.size(); index++)
                    mF.add(index, allTempForces.get(subBuffer)
                            .get(index)
                            .subtract(singleF_sub.get(index)));
            }

        }
        return mE;
    }

    private void initializeForce(List<Vector> forces, int size) {
        for (int i = 0; i < size; i++) {
            Vector v = vectorFactory.create(0, 0, 0);
            forces.add(i, v);
        }
    }

    @FunctionalInterface
    public interface Processor {

        Job<JAXBElement> process(Job<JAXBElement> job);

        enum NAME{
            BF,
            FIRES,
            HOT_SPOT,
            CONFIG,
            PAP;
        }
    }

}
