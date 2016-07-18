package org.wallerlab.yoink.adaptive.services;

import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.service.adaptive.Adaptive;

@Service
public class AdaptiveService implements Adaptive {
    public Job compute(Job job){
        return job;
    }
}
