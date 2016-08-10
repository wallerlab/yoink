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

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.model.adaptive.Region;
import org.wallerlab.yoink.region.service.partitioners.Partitioner;
import org.wallerlab.yoink.region.domain.SimpleRegion;
import org.wallerlab.yoink.api.service.region.Regionizer;

import static org.wallerlab.yoink.math.SetOps.*;
import static org.wallerlab.yoink.api.model.adaptive.Region.Name.*;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.service.region.Regionizer.Type.*;

import java.util.*;
import javax.xml.bind.JAXBElement;
/**
 * A regionizer is to take a molecular system and partition it
 * into QM, (Buffer) and MM regions.
 *
 */
public class SimpleRegionizer implements Regionizer{

   private final Map<Regionizer.Type,Partitioner> partitioners;

   public SimpleRegionizer(Map<Regionizer.Type, Partitioner> partitioners){
       this.partitioners = partitioners;
   }

   public Job<JAXBElement> regionize(Job<JAXBElement> job) {
        Set<MolecularSystem.Molecule> moleculesInSystem = job.getMolecularSystem().getMolecules();
        Set<MolecularSystem.Molecule> moleculesInQmCore = job.getMolecularSystem().getMolecules("QM_CORE");
	//Hack, because of the way the code is setup.
	if (job.getParameter(PARTITIONER) == CLUSTER) return job;
	//Real work is done here.
	Map<Region.Name,Set<MolecularSystem.Molecule>> partitionedSets = 
	     this.partitioners.get(job.getParameter(PARTITIONER)).partition(job);
	Set<MolecularSystem.Molecule> moleculesInQmAdaptive = partitionedSets.get(QM_ADAPTIVE);
        Set<MolecularSystem.Molecule> moleculesInBuffer = partitionedSets.get(BUFFER);
	//Simple Set Operations
	Map<Region.Name,Region> regions = new EnumMap<>(Region.Name.class);
	regions.put(QM    , new SimpleRegion(QM,     union(moleculesInQmCore,moleculesInQmAdaptive)));
	regions.put(BUFFER, new SimpleRegion(BUFFER, equality(moleculesInBuffer)));
	regions.put(MM    , new SimpleRegion(MM,     difference(moleculesInSystem,union(moleculesInQmCore,moleculesInQmAdaptive))));
	job.setRegions(regions);
	return job;
    }

}
