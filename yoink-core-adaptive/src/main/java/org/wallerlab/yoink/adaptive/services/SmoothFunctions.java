package org.wallerlab.yoink.adaptive.services;


import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME;
import static org.wallerlab.yoink.adaptive.services.SmoothFunctions.SmoothFunction.NAME.*;

import java.util.Map;
import java.util.EnumMap;
import org.springframework.stereotype.Service;

@Service
public class SmoothFunctions {

    private Map<NAME,SmoothFunction> smoothFunctions;

    public SmoothFunctions(){
        this.smoothFunctions = new EnumMap<>(NAME.class);
        this.smoothFunctions.put(BROOKS, brooks);
        this.smoothFunctions.put(BULO, bulo);
        this.smoothFunctions.put(MOROKUMA, morokuma);
        this.smoothFunctions.put(PERMUTED, permuted);
        this.smoothFunctions.put(SCMP, scmp);
    }

    public SmoothFunction use(SmoothFunction.NAME name){
        return this.smoothFunctions.get(name);
    }

    /**
     * this compute function is used in hot-spot method. for details please see:
     * "A QM/MM simulation method applied to the solution of Li+ in liquid ammonia."
     * Chemical physics 211.1 (1996): 313-323.
     *
     * @param currentValue, currentValue(variable) in compute function
     * @param min, minimum value in compute function
     * @param max, maximum value in compute function
     * @return compute factor
     */
    SmoothFunction brooks = (currentValue, min, max) -> {
        double smoothFactor;
        if 		(currentValue >  max) smoothFactor = 0;
        else if (currentValue <= min) smoothFactor = 1;
        else {
            smoothFactor = Math.pow((Math.pow(max, 2) - Math.pow(currentValue, 2)), 2);
            smoothFactor *= (Math.pow(max, 2) + 2 * Math.pow(currentValue, 2) - 3 * Math.pow(min, 2));
            smoothFactor /=  Math.pow((max * max - min * min), 3);
        }
        return smoothFactor;
    };

    /**
     * this compute function is used in difference-based adaptive solvation(DAS)
     * method. for details please see:
     * "Toward a practical method for adaptive QM/MM simulations." Journal of
     * Chemical Theory and Computation 5.9 (2009): 2212-2221.
     *
     * @param currentValue
     *            , currentValue(variable) in compute function
     * @param min
     *            , minimum value in compute function
     * @param max
     *            , maximum value in compute function
     * @return compute factor
     */
    SmoothFunction bulo =(currentValue, min, max) ->{
        double smoothFactor;
        if 		(currentValue > max) smoothFactor = 1;
        else if (currentValue < min) smoothFactor = 0;
        else {
            smoothFactor  = Math.pow((currentValue - min), 2);
            smoothFactor *= (3 * max - min - 2 * currentValue);
            smoothFactor /= Math.pow((max - min), 3);
        }
        return smoothFactor;
    };

    /**
     * this compute function is used in ONIOM-XS method. for details please see:
     * "ONIOM-XS: An extension of the ONIOM method for molecule simulation in condensed phase"
     * Chemical Physics Letters, Volume 355, Number 3, 2 April 2002, pp.
     * 257-262(6).
     *
     * @param currentValue
     *            , currentValue(variable) in compute function
     * @param min
     *            , minimum value in compute function
     * @param max
     *            , maximum value in compute function
     * @return compute factor
     */
    SmoothFunction morokuma = (currentValue, min, max) -> {
        double smoothFactor;
        if 		(currentValue > max)  smoothFactor = 1;
        else if (currentValue < min)  smoothFactor = 0;
        else {
            double x = (currentValue - min) / (max - min);
            smoothFactor = 6 * (Math.pow((x - 0.5), 5)) - 5
                    * (Math.pow((x - 0.5), 3)) + 15.0 / 8 * (x - 0.5) + 0.5;
        }
        return smoothFactor;
    };

    /**
     * this compute function is used in PAP and SAP methods. for details please
     * see: Heyden, Andreas, Hai Lin, and Donald G. Truhlar. "Adaptive
     * partitioning in combined quantum mechanical and molecule mechanical
     * calculations of potential energy functions for multiscale simulations."
     * The Journal of Physical Chemistry B 111.9 (2007): 2231-2241.
     *
     * @param currentValue
     *            , currentValue(variable) in compute function
     * @param min
     *            , minimum value in compute function
     * @param max
     *            , maximum value in compute function
     * @return compute factor
     */
    SmoothFunction permuted = (currentValue, min, max) -> {
        double smoothFactor;
        if 		(currentValue > max)  smoothFactor = 0;
        else if (currentValue < min)  smoothFactor = 1;
        else {
            double alpha = (currentValue - min) / (max - min);
            smoothFactor = -6 * (Math.pow((alpha), 5)) + 15
                    * (Math.pow((alpha), 4)) - 10 * (Math.pow((alpha), 3)) + 1;
        }
        return smoothFactor;
    };

    /**
     * this compute function is used in SCMP method. for details please see:
     * "Size-Consistent Multipartitioning QM/MM: A Stable and Efficient Adaptive
     * QM/MM Method"
     *
     * @param currentValue, currentValue(variable) in compute function
     * @param min , minimum value in compute function
     * @param max , maximum value in compute function
     * @return compute factor
     */
    SmoothFunction scmp = (currentValue, min, max) -> {
        double  smoothFactor;
        if 		(currentValue > max) smoothFactor = 0;
        else if (currentValue < min) smoothFactor = 1;
        else {
            smoothFactor  = Math.pow((currentValue - max), 2);
            smoothFactor *= (-3 * min + max + 2 * currentValue);
            smoothFactor /= Math.pow((max - min), 3);
        }
        return smoothFactor;
    };

    /**
     * this interface is for the compute function
     *
     * @author Min Zheng
     *
     */
    @FunctionalInterface
    public interface SmoothFunction {

        /**
         *
         * @param current
         *            , the current value of variable
         * @param min
         *            , the minimum value of the variable
         * @param max
         *            , the maximum value of the variable
         * @return compute value, {@link java.lang.Double}
         */
        double evaluate(double current, double min, double max);

        enum NAME {
            BROOKS,
            BULO,
            MOROKUMA,
            PERMUTED,
            SCMP;
        }

    }

}