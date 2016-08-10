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
package org.wallerlab.yoink.batch.service.response;

import org.wallerlab.yoink.api.model.Job;
import org.wallerlab.yoink.api.model.molecular.MolecularSystem;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecule.FilesWriter;
import org.wallerlab.yoink.api.model.adaptive.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBElement;
import org.cml_v3.generated.Molecule;
import org.cml_v3.generated.Cml;
import org.cml_v3.generated.Gradient;
import org.cml_v3.generated.MoleculeList;
import org.cml_v3.generated.ObjectFactory;
import org.cml_v3.generated.Property;
import org.cml_v3.generated.PropertyList;
import org.cml_v3.generated.Scalar;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * this class is to wrap adaptive qmmm result to JAXB element PropertyList
 * 
 * @author Min Zheng
 *
 */
//TODO deprecate this, use a XML library
@Service
public class PropertyWrapper implements Wrapper<Job<JAXBElement>> {

	@Autowired
	@Qualifier("jaxbFileWriter")
	private FilesWriter<Object> jaxbFileWriter;

	/**
	 * wrap molecule name(
	 * {@link Region.Name}, like QM or
	 * QM_ADAPTIVE) and buffer region smoothing in JAXBElemnt Cml
	 * 
	 * @param job
	 *            -{@link Job}, contains
	 *            parameters and stores results
	 */
	public void wrap(Job<JAXBElement> job) {
		JAXBElement<Cml> cmlElement = job.getInput();
		Map<Job.JobParameter, Object> parameters = job.getParameters();
		Map<String, Object> properties = job.getProperties();
		changeMoleculeId(job, cmlElement);
		putPartitioningResultInProperty(job, cmlElement, parameters, properties);
		putSmoothingResultInProperty(job, cmlElement, parameters, properties);
	}

	private void changeMoleculeId(Job<JAXBElement> job,
			JAXBElement<Cml> cmlElement) {
		for (Object elementList : cmlElement.getValue().getAnyCmlOrAnyOrAny()) {
			// check moleucleList
			JAXBElement element = (JAXBElement) elementList;
			if (element.getDeclaredType() == MoleculeList.class) {
				loopOverAllMolecules(job, elementList);
			}

		}
	}

	private void loopOverAllMolecules(Job<JAXBElement> job, Object elementList) {
		Set<MolecularSystem.Molecule> molecules =  job.getMolecularSystem().getMolecules();
		MoleculeList cmlMoleculeList = ((JAXBElement<MoleculeList>) elementList).getValue();
		int moleculeCounter = 0;
		for (Object elementMolecule : cmlMoleculeList.getAnyCmlOrAnyOrAny()) {
			JAXBElement element = (JAXBElement) elementMolecule;
			// check molecule
			if (element.getDeclaredType() == Molecule.class) {
				Molecule cmlMolecule = ((JAXBElement<Molecule>) element).getValue();
				//cmlMolecule.setId(molecules.get(moleculeCounter).getName().toString());
				cmlMolecule.setId("processed");
				moleculeCounter++;
			}
		}
	}

	
	private void putPartitioningResultInProperty(Job<JAXBElement> job,
												JAXBElement<Cml> cmlElement, Map<Job.JobParameter, Object> parameters,
												Map<String, Object> properties) {
		ObjectFactory objectFactory = new ObjectFactory();
		PropertyList propertyList = objectFactory.createPropertyList();
		propertyList.setTitle("Partitioning result  : ");
		Property property = wrapPartitionResult(job, properties, objectFactory,
				propertyList);
		JAXBElement propertyListJAXB = objectFactory
				.createPropertyList(propertyList);
		cmlElement.getValue().getAnyCmlOrAnyOrAny().add(propertyListJAXB);
	}
	
	private Property wrapPartitionResult(Job<JAXBElement> job,
										 Map<String, Object> properties,
										 ObjectFactory objectFactory,
										 PropertyList propertyList) {
		Property property = objectFactory.createProperty();
		property.setTitle("QM and Buffer molecule indices");
		loopOverQMAndBufferRegions(job, objectFactory, propertyList, property);	
		propertyList.getAnyCmlOrAnyOrAny().add(property);
		return property;
	}
	
	private void putSmoothingResultInProperty(Job<JAXBElement> job,
											  JAXBElement<Cml> cmlElement, Map<Job.JobParameter, Object> parameters,
											  Map<String, Object> properties) {
		ObjectFactory objectFactory = new ObjectFactory();
		PropertyList propertyList = objectFactory.createPropertyList();
		propertyList.setTitle("Smoothing result  :");
		Property property = wrapSmoothResult(job, properties, objectFactory, propertyList);
		wrapForceAndEnergy(properties, objectFactory, propertyList, property);
		JAXBElement propertyListJAXB = objectFactory.createPropertyList(propertyList);
		cmlElement.getValue().getAnyCmlOrAnyOrAny().add(propertyListJAXB);
	}

	private Property wrapSmoothResult(Job<JAXBElement> job,
									  Map<String, Object> properties,
									  ObjectFactory objectFactory,
									  PropertyList propertyList) {
		Property property = objectFactory.createProperty();
		property.setTitle("weights and compute factors ");
		loopOverAllWeightConfigurations(properties, objectFactory,
				propertyList, property);
		loopOverBufferMolecules(job, objectFactory, propertyList, property);
		propertyList.getAnyCmlOrAnyOrAny().add(property);
		return property;
	}

	private void wrapForceAndEnergy(Map<String, Object> properties,
									ObjectFactory objectFactory,
									PropertyList propertyList,
									Property property) {
		Property propertyEnergy = objectFactory.createProperty();
		Property propertyForce = objectFactory.createProperty();
		if (properties.containsKey("forcesAndEnergy")) {
			Map<List<Vector>, Double> forcesAndEnergy = (Map<List<Vector>, Double>) properties
					.get("forcesAndEnergy");
			propertyEnergy.setTitle("  Energy");
			List<Double> energyList = new ArrayList<Double>(
					forcesAndEnergy.values());
			double energy = energyList.get(0);
			propertyEnergy.setId(String.valueOf(energy));
			List<List<Vector>> forces = new ArrayList<List<Vector>>(
					forcesAndEnergy.keySet());

			for (Vector force : forces.get(0)) {
				Gradient gradient = objectFactory.createGradient();

				gradient.setId(String.valueOf(force.toArray()));
				property.getAnyCmlOrAnyOrAny().add(gradient);
			}

		}
		propertyList.getAnyCmlOrAnyOrAny().add(propertyEnergy);

		propertyList.getAnyCmlOrAnyOrAny().add(propertyForce);
	}

	private void loopOverAllWeightConfigurations(Map<String, Object> properties,
												 ObjectFactory objectFactory,
												 PropertyList propertyList,
												 Property property) {
		if (properties.containsKey("weightfactors")) {
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor
								= (Map<List<Integer>, Double>) properties.get("weightfactors");
			propertyList.setTitle(propertyList.getTitle() + "  weights factors");
			for (List<Integer> indices : molecularIndicesAndWeightFactor
					.keySet()) {
				putEveryConfigurationWeightedFactorInScalar(objectFactory,
						property, molecularIndicesAndWeightFactor, indices);
			}
		}
	}

	private void putEveryConfigurationWeightedFactorInScalar(ObjectFactory objectFactory,
															 Property property,
															 Map<List<Integer>, Double> molecularIndicesAndWeightFactor,
															 List<Integer> indices) {
		Scalar scalar = objectFactory.createScalar();
		scalar.setDataType("weighted function");
		scalar.setValue(String.valueOf(molecularIndicesAndWeightFactor
				.get(indices)));
		scalar.setTitle("molecule indeices");
		scalar.setId(String.valueOf(indices));
		property.getAnyCmlOrAnyOrAny().add(scalar);
	}

	private void loopOverBufferMolecules(Job<JAXBElement> job,
										 ObjectFactory objectFactory,
										 PropertyList propertyList,
										 Property property) {
		if (job.getProperties().containsKey("smoothfactors")) {
			List<Double> smoothFactors = (List<Double>) job.getProperties().get("smoothfactors");
			if (job.getRegions().containsKey(Region.Name.BUFFER)) {
				Set<MolecularSystem.Molecule> bufferMolecules = job.getRegions()
						.get(Region.Name.BUFFER).getMolecules();
				List<Integer> bufferIndices = new ArrayList<Integer>();
				for (MolecularSystem.Molecule molecule : bufferMolecules) {
					bufferIndices.add(molecule.getIndex());
				}

				for (int counter = 0; counter < bufferIndices.size(); counter++) {
					putEveryMoleculeSmoothFactorInScalar(objectFactory,
							property, bufferIndices.get(counter),
							smoothFactors.get(counter));
				}
				propertyList.setTitle(propertyList.getTitle() + "   average compute factor:  ");
			}
		}
	}

	private void loopOverQMAndBufferRegions(Job<JAXBElement> job,
											ObjectFactory objectFactory,
											PropertyList propertyList,
											Property property) {
		List<Region.Name> regions= new ArrayList<Region.Name>();
		regions.add(Region.Name.QM);
		regions.add(Region.Name.BUFFER);
		for(Region.Name region: regions){
			if (job.getRegions().containsKey(region)) {
				Set<MolecularSystem.Molecule> molecules = job.getRegions().get(region).getMolecules();
				List<Integer> indices = new ArrayList<Integer>();
				for (MolecularSystem.Molecule molecule : molecules)
					indices.add(molecule.getIndex());
				Scalar scalar = objectFactory.createScalar();
				scalar.setDataType(region.toString()+" molecules");
				scalar.setValue(String.valueOf(indices));		
				property.getAnyCmlOrAnyOrAny().add(scalar);								
			}
		}	
	}
	
	private void putEveryMoleculeSmoothFactorInScalar(ObjectFactory objectFactory,
													  Property property,
													  Integer bufferIndex,
													  Double smoothFactor) {
		Scalar scalar = objectFactory.createScalar();
		scalar.setDataType("compute function");
		scalar.setValue(String.valueOf(smoothFactor));
		scalar.setTitle("molecule index");
		scalar.setId(String.valueOf(bufferIndex));
		property.getAnyCmlOrAnyOrAny().add(scalar);
	}

}
