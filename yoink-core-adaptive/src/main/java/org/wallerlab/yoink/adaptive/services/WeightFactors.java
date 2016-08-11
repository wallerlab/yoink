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

import org.wallerlab.yoink.math.MapOps;
import org.wallerlab.yoink.math.SetOps;
import org.wallerlab.yoink.api.model.Coord;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.adaptive.domain.Configuration;
import org.wallerlab.yoink.adaptive.domain.BufferMolecule;

import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.QM_CORE;
import static org.wallerlab.yoink.adaptive.services.WeightFactors.WeightFactor.*;
import static org.wallerlab.yoink.adaptive.services.WeightFactors.WeightFactor.NAME.*;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME.BULO;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME.SCMP;

import java.util.*;
import java.util.stream.IntStream;
import com.google.common.primitives.Ints; //drop this dependency
import javax.xml.bind.JAXBElement;
import static java.util.stream.Collectors.toList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.wallerlab.yoink.molecule.service.IDistanceCalculator;

@Service
public class WeightFactors {

    @Autowired
    SmoothFunctions smoothFunctions;

    @Autowired
    IDistanceCalculator distanceCalculator;

    private Map<NAME,WeightFactor> weightFactors;

    public WeightFactors(){
        this.weightFactors = new EnumMap<>(NAME.class);
        this.weightFactors.put(DAS,dasWeights);
        this.weightFactors.put(XS,xsWeights);
        this.weightFactors.put(SAP,sapWeights);
        this.weightFactors.put(WeightFactor.NAME.SCMP,scmpWeights);
    }

    public WeightFactor use(NAME name){
        return weightFactors.get(name);
    }

    /**
     * Weight factor in XS. for details please see:
     * "ONIOM-XS: An extension of the ONIOM method for molecule simulation in condensed phase"
     * Chemical Physics Letters, Volume 355, Number 3, 2 April 2002, pp. 257-262(6).
     */
    public WeightFactor xsWeights = (job, lambdaFunction, smoothFunction) -> {
        Set<BufferMolecule> moleculesInBuffer = lambdaFunction.compute(job,smoothFunction);
        double averageLambda = moleculesInBuffer.stream()
                                                .mapToDouble(BufferMolecule::getLambda)
                                                .average()
                                                .getAsDouble();
        Set<Configuration> configurations = new HashSet<>();
        configurations.add(new Configuration(averageLambda, 1-averageLambda, moleculesInBuffer));
        return configurations;
    };

    /**
     * Weight factor in DAS. for details please see:
     * "Toward a practical method for adaptive QM/MM simulations." Journal of
     * Chemical Theory and Computation 5.9 (2009): 2212-2221.
     */
    public WeightFactor dasWeights = ( job, LambdaFunction, smoothFunction) -> {
        List<BufferMolecule> bufferMolecules = new ArrayList<>(LambdaFunction.compute(job,smoothFunction));
        List<Integer> bufferIndices = new ArrayList<Integer>();
        Set<Configuration> configurations = new HashSet<>();
        // loop over all QM/MM sets in buffer region,
        SetOps.split(Ints.toArray(bufferIndices))
              .forEach(qmSet -> {
                  Set<Integer> mmSet = new HashSet<Integer>(bufferIndices);
                  mmSet.removeAll(qmSet);
                  Double lambdaQMMax = qmSet.stream()
                                            .mapToDouble(index ->
                                                    bufferMolecules.get(bufferIndices.indexOf(index)).getLambda())
                                                                   .max()
                                                                   .getAsDouble();
                  Double lambdaMMMin = mmSet.stream()
                                            .mapToDouble(index ->
                                                    bufferMolecules.get(bufferIndices.indexOf(index)).getLambda())
                                                                   .min()
                                                                   .getAsDouble();
                    if (lambdaQMMax < lambdaMMMin)
                        configurations.add(new Configuration(lambdaMMMin - lambdaQMMax, 0.0,
                                               new HashSet<BufferMolecule>(bufferMolecules)));
                });
        return configurations;
    };

    /**
     * Weight factors in SAP. for details please see: see:
     * Heyden, Andreas, Hai Lin, and Donald G. Truhlar. "Adaptive partitioning in
     * combined quantum mechanical and molecule mechanical calculations of
     * potential energy functions for multiscale simulations." The Journal of
     * Physical Chemistry B 111.9 (2007): 2231-2241.
     *
     * @author Min Zheng
     *
     */
    public WeightFactor sapWeights = (job, smoothFactor, smoothFunction)-> {
        // sort buffer region based on radial coordinate
        List<BufferMolecule> bufferMolecules = sortBuffer(smoothFactor.compute(job,smoothFunction),
                                                                job.getRegion(QM_CORE).getCenterOfMass());
        List<List<Integer>> activeBuffers = new ArrayList<>();
        List<Double> weightFactors = new ArrayList<>();
        for (int i = 0; i < bufferMolecules.size(); i++) {
            double lambda = bufferMolecules.get(i).getLambda();
            double chi1 = 0;
            for (int j = 0; j < i; j++)
                chi1 += (1 - bufferMolecules.get(j).getLambda()) / (bufferMolecules.get(j).getLambda() - lambda);
            double chi2 = (1 - lambda) / lambda;
            double chi3 = 0;
            for (int j = i + 1; j < bufferMolecules.size(); j++)
                chi3 += (1 - lambda) / (lambda - bufferMolecules.get(j).getLambda()) *
                        bufferMolecules.get(j).getLambda();
            bufferMolecules.get(i).setQmWeight(Math.pow(1 + chi3 + chi2 + chi3, -3));
            //activeBuffers.add(bufferMolecules.subList(0, i + 1));
        }

        Set<Configuration> configurations = new HashSet<>();
        double bufferWeight=1.0;
        for(int i=0; i<bufferMolecules.size(); i++){
            double qmWeight =   bufferMolecules.get(i).getQmWeight().get();
            bufferWeight   *= 1-bufferMolecules.get(i).getQmWeight().get();
            for(int j=activeBuffers.get(i).size(); j<bufferMolecules.size(); j++)
                qmWeight*=(1-bufferMolecules.get(j).getQmWeight().get());
                //configurations.add(new Configuration(qmWeight, bufferWeight, bufferMolecules.get(activeBuffers.get(i)));
        }
        return configurations;
    };

    private Map<List<Integer>, Double> sigmas;

    /**
     * this class is to use weights factor in SCMP. for details please see:
     * "Size-Consistent Multipartitioning QM/MM: A Stable and Efficient Adaptive
     * QM/MM Method"
     *
     * @author Min Zheng
     *
     */
    public WeightFactor scmpWeights = (job, smoothFactor, smoothFunction) ->{
        List<BufferMolecule > bufferMolecules = new ArrayList<>(smoothFactor.compute(job,smoothFunction));
        Map<Job.JobParameter, Object> parameters = job.getParameters();

        List<Integer> bufferIndices = bufferMolecules.stream()
                                                     .map(molecule -> molecule.getMolecule().getIndex())
                                                     .collect(toList());

        double s_qm_out = (double) parameters.get(DISTANCE_S_QM_OUT);
        double t_qm_out = (double) parameters.get(DISTANCE_T_QM_OUT);
        double s_qm_in =  (double) parameters.get(DISTANCE_S_QM_IN);
        double t_qm_in =  (double) parameters.get(DISTANCE_T_QM_IN);
        double s_mm_out = (double) parameters.get(DISTANCE_S_MM_OUT);
        double t_mm_out = (double) parameters.get(DISTANCE_T_MM_OUT);
        double s_mm_in =  (double) parameters.get(DISTANCE_S_MM_IN);
        double t_mm_in =  (double) parameters.get(DISTANCE_T_MM_IN);

        sigmas = Collections.synchronizedMap(new HashMap<List<Integer>, Double>());

        int qmNumber = (int) parameters.get(NUMBER_QM);
        int numberQmInBuffer = qmNumber - qmNumber * 2 / 3;
        Coord centerCoord = job.getRegions().get(Region.Name.QM_CORE).getCenterOfMass();

        SetOps.split(Ints.toArray(bufferIndices), numberQmInBuffer)
              .parallelStream()
              .forEach(qmSet -> {
                  Set<Integer> mmSet = new HashSet<Integer>(bufferIndices);
                  mmSet.removeAll(qmSet);
                  double fadeOutQM =      fade(centerCoord, bufferMolecules, bufferIndices, s_qm_out,
                                               t_qm_out, qmSet,smoothFunctions.use(SCMP));
                  double fadeInQM  = 1 -  fade(centerCoord, bufferMolecules, bufferIndices, s_qm_in,
                                               t_qm_in, qmSet,smoothFunctions.use(SCMP));
                  double fadeOutMM =      fade(centerCoord, bufferMolecules, bufferIndices, s_mm_out,
                                               t_mm_out, (List<Integer>) mmSet,smoothFunctions.use(BULO));
                  double fadeInMM  = 1 -  fade(centerCoord, bufferMolecules, bufferIndices, s_mm_in,
                                               t_mm_in, (List<Integer>) mmSet,smoothFunctions.use(BULO));
                  double sigma = fadeOutQM * fadeOutMM * fadeInQM * fadeInMM;
                  sigmas.put(qmSet, sigma);
                  });
        return getLimitedWeights(job);
    };

    private Set<Configuration> getLimitedWeights( Job job) {
        int partitionNumber = (int) job.getParameters().get(NUMBER_PARTITION);
        Map<List<Integer>, Double> sortedSigmaIndexMap = MapOps.sortByValue(this.sigmas);
        List<Double> sortedSigmas = new ArrayList<Double>(sortedSigmaIndexMap.values());
        List<List<Integer>> sortedIndices = new ArrayList<List<Integer>>(sortedSigmaIndexMap.keySet());
        List<Double> subSigmas = sortedSigmas.subList(sortedSigmas.size() - partitionNumber, sortedSigmas.size());
        List<List<Integer>> subIndices = sortedIndices.subList(sortedSigmas.size() - partitionNumber, sortedSigmas.size());
        double sumOfSigmas = subSigmas.stream().reduce(0.0,Double::sum);

        double sigmas[] = new double[partitionNumber];
        List<List<Integer>> qmSets = new ArrayList<>();
        IntStream.rangeClosed(0,subSigmas.size()).forEach(index -> {qmSets.add(subIndices.get(index));
                                                                     sigmas[index] = subSigmas.get(index);
                                                                });

        //Looks like we need the average sigma as the weight factor. Then we need the qmSet is the
        Map<List<Integer>, Double> molecularIndicesAndWeightFactor = new HashMap<List<Integer>, Double>();

        Set<Configuration> configurations = new HashSet<>();
        for (int i = 0; i < sigmas.length; i++) {
            sigmas[i] /=  sumOfSigmas;
            molecularIndicesAndWeightFactor.put(qmSets.get(i), sigmas[i]);
            //configurations.add(new Configuration(sigmas[i], 0.0, qmSets.get(i)));
        }
        return configurations;
    }

    private double fade(Coord centerCoord,
                        List<BufferMolecule> molecules,
                        List<Integer> indices,
                        double s_qm_out,
                        double t_qm_out,
                        List<Integer> set,
                        SmoothFunctions.SmoothFunction smoothFunction) {
        double fade = 1.0;
        for (Integer index : set)
            fade *= smoothFunction.evaluate(
                    distanceCalculator.closest(centerCoord, molecules.get(indices.indexOf(index))
                                                                     .getMolecule()), s_qm_out, t_qm_out);
        return fade;
    }

    private List<BufferMolecule> sortBuffer(Set<BufferMolecule> moleculesInBuffer, Coord coord) {
        return moleculesInBuffer.stream()
                .map(bufferMolecule -> {
                    bufferMolecule.setDistanceToPoint(distanceCalculator.closest(coord, bufferMolecule.getMolecule()));
                    return bufferMolecule;
                })
                .sorted((m1, m2) -> m1.getDistanceToPoint().compareTo(m2.getDistanceToPoint()))
                .collect(toList());
    }

    @FunctionalInterface
    public interface WeightFactor {

        Set<Configuration> compute(Job<JAXBElement> job ,
                                   SmoothFactors.SmoothFactor smoothFactor,
                                   SmoothFunctions.SmoothFunction smoothFunction);
        enum NAME{ XS, DAS, SAP, SCMP }
    }

}
