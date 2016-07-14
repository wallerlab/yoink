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
package org.wallerlab.yoink.region.service;

import org.wallerlab.yoink.api.model.batch.Job;
import org.wallerlab.yoink.api.model.molecule.Molecule;
import org.wallerlab.yoink.api.model.region.Region;
import org.wallerlab.yoink.region.service.partitioners.Partitioner;

import org.wallerlab.yoink.api.service.region.Regionizer;
import org.wallerlab.yoink.region.domain.SimpleRegion;

import javax.xml.bind.JAXBElement;
import java.util.*;

import static org.wallerlab.yoink.api.model.region.Region.Name.*;
import static org.wallerlab.yoink.api.model.batch.JobParameter.*;
import static org.wallerlab.yoink.math.set.SetOps.*;

public class RegionizerService implements Regionizer{

	private Map<Regionizer.Type,Partitioner> partitioners;

	public RegionizerService(Map<Regionizer.Type, Partitioner> partitioners){
		this.partitioners = partitioners;
	}

	public Job<JAXBElement> regionize(Job<JAXBElement> job) {

        Set<Molecule> moleculesInSystem = job.getMolecularSystem().getMolecules();
        Set<Molecule> moleculesInQmCore = job.getMolecularSystem().getMolecules("QM_CORE");

		Map<Region.Name,Set<Molecule>> partitionedSets = this.partitioners.get(job.getParameter(PARTITIONER)).partition(job);
		Set<Molecule> moleculesInQmAdaptive = partitionedSets.get(QM_ADAPTIVE);
        Set<Molecule> moleculesInBuffer = partitionedSets.get(BUFFER);

		job.addRegion(new SimpleRegion(QM,         union(moleculesInQmCore,moleculesInQmAdaptive)));
		job.addRegion(new SimpleRegion(BUFFER,  equality(moleculesInBuffer)));
		job.addRegion(new SimpleRegion(MM, 	  difference(moleculesInSystem,union(moleculesInQmCore,moleculesInQmAdaptive))));

		return job;
	}

}
