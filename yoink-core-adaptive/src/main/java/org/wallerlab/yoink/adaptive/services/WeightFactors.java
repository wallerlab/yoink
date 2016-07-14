package org.wallerlab.yoink.adaptive.services;

import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.math.set.Subsets;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.adaptive.domain.AdaptiveMolecule;
import org.wallerlab.yoink.adaptive.domain.SimpleAdaptiveMolecularSystem;

import static org.wallerlab.yoink.api.model.region.Region.Name.BUFFER;
import static org.wallerlab.yoink.adaptive.services.WeightFactors.WeightFactor.*;
import static org.wallerlab.yoink.adaptive.services.WeightFactors.WeightFactor.NAME.*;

import java.util.*;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBElement;

@Service
public class WeightFactors {

    @Autowired private Smoothner sapWeights;

    @Autowired private Smoothner scmpWeights;

    private Map<NAME,WeightFactor> weightFactors;

    public WeightFactors(){
        this.weightFactors = new EnumMap<>(NAME.class);
        this.weightFactors.put(DAS,dasWeights);
        this.weightFactors.put(XS,xsWeights);
        this.weightFactors.put(SAP,sapWeights);
        this.weightFactors.put(SCMP,scmpWeights);
    }

    public Smoothner use(NAME name){
        return weightFactors.get(name);
    }

    /**
     * Weight factor in XS. for details please see:
     * "ONIOM-XS: An extension of the ONIOM method for molecule simulation in condensed phase"
     * Chemical Physics Letters, Volume 355, Number 3, 2 April 2002, pp. 257-262(6).
     */
    public WeightFactor xsWeights = (job, lambdaFunction, smoothFunction) -> {

        List<AdaptiveMolecule> moleculesInBuffer = lambdaFunction.compute(job,smoothFunction);

        double sumLambda = moleculesInBuffer.stream()
                                            .mapToDouble(molecule -> molecule.getLambda())
                                            .reduce(0.0,Double::sum);

        SimpleAdaptiveMolecularSystem sams = new SimpleAdaptiveMolecularSystem();
        sams.setQmWeight(sumLambda / moleculesInBuffer.size());
        sams.setBufferWeight(1 - sams.getQmWeight());
        return sams;
    };

    /**
     * Weight factor in DAS. for details please see:
     * "Toward a practical method for adaptive QM/MM simulations." Journal of
     * Chemical Theory and Computation 5.9 (2009): 2212-2221.
     */
    public WeightFactor dasWeights = ( job, smoothFactor, smoothFunction) -> {

        List<Double> lambda = smoothFactor.compute(job,smoothFunction);

        Set<Molecule> bufferMolecules = job.getRegion(BUFFER).getMolecules();
        List<Integer> bufferIndices = new ArrayList<Integer>();
        for (Molecule molecule : bufferMolecules)
                    bufferIndices.add(molecule.getIndex());

        Map<List<Integer>, Double> molecularIndicesAndWeightFactor = new HashMap<List<Integer>, Double>();

        // loop over all QM/MM sets in buffer region
        Subsets.split(Ints.toArray(bufferIndices))
               .forEach(qmSet -> {
                    Set<Integer> mmSet = new HashSet<Integer>(bufferIndices);
                    mmSet.removeAll(qmSet);
                    Double lambdaQMMax = qmSet.stream()
                                              .mapToDouble(index ->
                                                             lambda.get(bufferIndices.indexOf(index)))
                                                                   .max()
                                                                   .getAsDouble();
                    Double lambdaMMMin = mmSet.stream()
                                              .mapToDouble(index ->
                                                             lambda.get(bufferIndices.indexOf(index)))
                                                                   .min()
                                                                   .getAsDouble();
                    if (lambdaQMMax < lambdaMMMin)
                        molecularIndicesAndWeightFactor.put(qmSet, lambdaMMMin - lambdaQMMax);
                });

        SimpleAdaptiveMolecularSystem sams = new SimpleAdaptiveMolecularSystem();
        sams.setQmWeight(qmWeight);
        sams.setBufferWeight(bufferWeight);
        return weights;
    };


    @FunctionalInterface
    public interface WeightFactor {

        List<Double> compute(Job<JAXBElement> job , SmoothFactors.SmoothFactor smoothFactor, SmoothFunctions.SmoothFunction smoothFunction);

        public enum NAME{
            XS,
            DAS,
            SAP,
            SCMP;
        }
    }

}
