package org.wallerlab.yoink.adaptive.services;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.service.adaptive.Adaptive;

@Service
public class AdaptiveService implements Adaptive {
    /**
     *
     * @param job as input
     * @return Job completed job
     */
    public Job compute(Job job){
        return job;
    }
}
