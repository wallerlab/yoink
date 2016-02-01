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
package org.wallerlab.yoink.adaptive.smooth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;

/**
 * this class is to choose different smoothner to smooth the buffer region
 * 
 * @author Min Zheng
 *
 */
@Service
public class AdaptiveQMMMSmoothnerRouter implements Smoothner {

	@Resource
	private Smoothner distanceSmoothnerBF;

	@Resource
	private Smoothner distanceSmoothnerXS;

	@Resource
	private Smoothner distanceSmoothnerSPOT;

	@Resource
	private Smoothner distanceSmoothnerDAS;

	@Resource
	private Smoothner distanceSmoothnerPAP;

	@Resource
	@Qualifier("dasWeightFactors")
	private Smoothner dasWeightFactors;

	@Resource
	@Qualifier("sapWeightFactors")
	private Smoothner sapWeightFactors;

	@Resource
	@Qualifier("scmpWeightFactors")
	private Smoothner scmpWeightFactors;

	@Resource
	@Qualifier("xsWeightFactors")
	private Smoothner xsWeightFactors;

	@Resource
	@Qualifier("firesSmoothner")
	private Smoothner firesSmoothner;
	
	public void smooth(Job job) {
		List<Smoothner> smoothners = getSmoothers(job);
		for (Smoothner smoothner : smoothners) {
			smoothner.smooth(job);
		}
	}

	private List<Smoothner> getSmoothers(Job job) {
		List<Smoothner> smoothners = new ArrayList<Smoothner>();

		switch ((Smoothner.Type) job.getParameters()
				.get(JobParameter.SMOOTHNER)) {

		case DISTANCE_DAS:
			smoothners.add(distanceSmoothnerDAS);
			smoothners.add(dasWeightFactors);
			break;
		case DISTANCE_XS:
			smoothners.add(distanceSmoothnerXS);
			smoothners.add(xsWeightFactors);
			break;
		case DISTANCE_HOTSPOT:
			smoothners.add(distanceSmoothnerSPOT);
			break;
		case DISTANCE_PAP:
			smoothners.add(distanceSmoothnerPAP);
			break;
		case DISTANCE_SAP:
			smoothners.add(distanceSmoothnerPAP);
			smoothners.add(sapWeightFactors);
			break;
		case DISTANCE_SCMP:
			smoothners.add(scmpWeightFactors);
			break;
		case FIRES:
			smoothners.add(firesSmoothner);
			break;	
		case ABRUPT:
		case BUFFERED_FORCE:
			break;
		}
		return smoothners;
	}

}
