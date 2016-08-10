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
package org.wallerlab.yoink.cluster.service;

import org.wallerlab.yoink.api.model.*;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.density.DensityCalculator;
import org.wallerlab.yoink.api.service.cube.Voronoizer;
import org.wallerlab.yoink.cluster.data.InteractionRepo;
import org.wallerlab.yoink.cluster.domain.Interaction;
import static org.wallerlab.yoink.api.model.Job.JobParameter.*;
import static org.wallerlab.yoink.api.model.DensityPoint.DensityType.DORI;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import static java.util.stream.Collectors.toSet;

/**
 * This class is to get all pairs having interaction
 * (yes or no) base on DORI analysis.
 * 
 */
@Service
public class InteractionService {

	@Resource
	private Voronoizer voronoizer;

	@Resource
	private DensityCalculator densityCalculator;

	private static final double doriDensityThreshold = 0.0001d;
	private static final double doriThreshold 		 = 0.9d;

    private InteractionRepo interactionRepo;

	//This is performed before the clustering
	public Set<Interaction> getDoriInteractionSet(Job job) {

        Set<Interaction> interactions = new HashSet<>();

            List<VoronoiPoint> gridPoints = voronoizer.voronoize(DORI,
                                        job.getMolecularSystem().getMolecules(), job.getMolecularSystem());
            System.out.println("Number of Grid Points = " + gridPoints.size());


            Set<MolecularSystem.Molecule> moleculesInCube = job.getMolecularSystem().getMolecules();

            interactions = gridPoints.parallelStream()
                            .filter((VoronoiPoint gridPoint) -> {
                                double density = densityCalculator.electronic(gridPoint.getCoordinate(), moleculesInCube);
                                return (density >= doriDensityThreshold);
                            })
                            .filter((VoronoiPoint gridPoint) -> {
                                double dori = densityCalculator.dori(gridPoint.getCoordinate(), mapToAtoms(moleculesInCube));
                                return (1 >= dori && dori >= doriThreshold);
                            })
                            .map((VoronoiPoint gridPoint) -> {
                                List<MolecularSystem.Molecule> nearestMolecules =
                                        new ArrayList<MolecularSystem.Molecule>(gridPoint.getNearestMolecules());
                                return new Interaction((Set<MolecularSystem.Molecule>)gridPoint.getNearestMolecules(),
                                                        densityCalculator.electronic(gridPoint.getCoordinate(), moleculesInCube),0.0);
                            })
                            .collect(toSet());

            if ((Boolean) job.getParameter(INTERACTION_WEIGHT)) {
                double weightMin = interactions.stream().mapToDouble(Interaction::getWeight).min().getAsDouble();
                double weightMax = interactions.stream().mapToDouble(Interaction::getWeight).max().getAsDouble();
                double normal = 1.0 / (weightMax - weightMin);
                interactions = interactions.stream().map(interaction ->
                        new Interaction(interaction.getMolecules(),interaction.getWeight() * normal,0.0))
                        .collect(toSet());
            } else {
                interactions = interactions.stream().map(interaction ->
                        new Interaction(interaction.getMolecules(),1.0,0.0))
                        .collect(toSet());
            }

        interactionRepo.save(interactions);

        return interactions;
    }

	//convenience method
	private Set<MolecularSystem.Molecule.Atom> mapToAtoms(Set<MolecularSystem.Molecule> molecules){
		return molecules.stream().flatMap(molecule -> molecule.getAtoms().stream()).collect(Collectors.toSet());
	}

}
