package org.wallerlab.yoink.adaptive.services;

import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import static org.wallerlab.yoink.api.model.batch.JobParameter.SMOOTHNER;

import javax.xml.bind.JAXBElement;

/**
 * Adaptive QM/MM processor.
 */
public class AdaptiveProcessor {

    private Map<NAME,Smoothner> smoothners;

    public AdaptiveProcessor(Map<NAME,Smoothner> smoothners){
        this.smoothners = smoothners;
    }

    public Job<JAXBElement> smooth(Job<JAXBElement>  job){

        job.getRegion(BUFFER)
           .getMolecules()
           .stream()
           .map(molecule -> )

       return smoothners.get(job.getParameter(SMOOTHNER)).smooth(job);
    }


}
