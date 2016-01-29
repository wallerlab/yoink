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
package org.wallerlab.yoink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.bootstrap.Job;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.api.service.molecular.FilesWriter;
import org.wallerlab.yoink.api.model.molecular.Molecule;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.bootstrap.Wrapper;
import org.xml_cml.schema.Cml;
import org.xml_cml.schema.Gradient;
import org.xml_cml.schema.MoleculeList;
import org.xml_cml.schema.ObjectFactory;
import org.xml_cml.schema.Property;
import org.xml_cml.schema.PropertyList;
import org.xml_cml.schema.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * this class is to wrap adaptive qmmm result to JAXB element PropertyList
 * 
 * @author Min Zheng
 *
 */
@Service
public class PropertyWrapper implements Wrapper<Job<JAXBElement>> {

	@Autowired
	@Qualifier("jaxbFileWriter")
	private FilesWriter<Object> jaxbFileWriter;

	/**
	 * wrap molecular name(
	 * {@link org.wallerlab.yoink.api.model.regionizer.Region.Name}, like QM or
	 * QM_ADAPTIVE) and buffer region smoothing in JAXBElemnt Cml
	 * 
	 * @param job
	 *            -{@link org.wallerlab.yoink.api.model.bootstrap.Job}, contains
	 *            parameters and stores results
	 */
	public void wrap(Job<JAXBElement> job) {
		JAXBElement<Cml> cmlElement = job.getInput();
		Map<JobParameter, Object> parameters = job.getParameters();
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
		List<Molecule> molecules = job.getMolecularSystem().getMolecules();
		MoleculeList cmlMoleculeList = ((JAXBElement<MoleculeList>) elementList)
				.getValue();
		int moleculeCounter = 0;
		for (Object elementMolecule : cmlMoleculeList.getAnyCmlOrAnyOrAny()) {
			JAXBElement element = (JAXBElement) elementMolecule;
			// check molecule
			if (element.getDeclaredType() == org.xml_cml.schema.Molecule.class) {
				org.xml_cml.schema.Molecule cmlMolecule = ((JAXBElement<org.xml_cml.schema.Molecule>) element)
						.getValue();
				cmlMolecule.setId(molecules.get(moleculeCounter).getName()
						.toString());
				moleculeCounter++;
			}
		}
	}

	
	private void putPartitioningResultInProperty(Job<JAXBElement> job,
			JAXBElement<Cml> cmlElement, Map<JobParameter, Object> parameters,
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
			Map<String, Object> properties, ObjectFactory objectFactory,
			PropertyList propertyList) {
		Property property = objectFactory.createProperty();
		property.setTitle("QM and Buffer molecular indices");
		loopOverQMAndBufferRegions(job, objectFactory, propertyList, property);	
		propertyList.getAnyCmlOrAnyOrAny().add(property);
		return property;
	}
	
	private void putSmoothingResultInProperty(Job<JAXBElement> job,
			JAXBElement<Cml> cmlElement, Map<JobParameter, Object> parameters,
			Map<String, Object> properties) {
		ObjectFactory objectFactory = new ObjectFactory();
		PropertyList propertyList = objectFactory.createPropertyList();
		propertyList.setTitle("Smoothing result  :");
		Property property = wrapSmoothResult(job, properties, objectFactory,
				propertyList);
		wrapForceAndEnergy(properties, objectFactory, propertyList, property);
		JAXBElement propertyListJAXB = objectFactory
				.createPropertyList(propertyList);
		cmlElement.getValue().getAnyCmlOrAnyOrAny().add(propertyListJAXB);
	}

	private Property wrapSmoothResult(Job<JAXBElement> job,
			Map<String, Object> properties, ObjectFactory objectFactory,
			PropertyList propertyList) {
		Property property = objectFactory.createProperty();
		property.setTitle("weight and smooth factors ");
		loopOverAllWeightConfigurations(properties, objectFactory,
				propertyList, property);
		loopOverBufferMolecules(job, objectFactory, propertyList, property);
		propertyList.getAnyCmlOrAnyOrAny().add(property);
		return property;
	}

	private void wrapForceAndEnergy(Map<String, Object> properties,
			ObjectFactory objectFactory, PropertyList propertyList,
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

	private void loopOverAllWeightConfigurations(
			Map<String, Object> properties, ObjectFactory objectFactory,
			PropertyList propertyList, Property property) {
		if (properties.containsKey("weightfactors")) {
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor = (Map<List<Integer>, Double>) properties
					.get("weightfactors");
			propertyList.setTitle(propertyList.getTitle() + "  weight factors");
			for (List<Integer> indices : molecularIndicesAndWeightFactor
					.keySet()) {
				putEveryConfigurationWeightedFactorInScalar(objectFactory,
						property, molecularIndicesAndWeightFactor, indices);
			}
		}
	}

	private void putEveryConfigurationWeightedFactorInScalar(
			ObjectFactory objectFactory, Property property,
			Map<List<Integer>, Double> molecularIndicesAndWeightFactor,
			List<Integer> indices) {
		Scalar scalar = objectFactory.createScalar();
		scalar.setDataType("weighted function");
		scalar.setValue(String.valueOf(molecularIndicesAndWeightFactor
				.get(indices)));
		scalar.setTitle("molecular indeices");
		scalar.setId(String.valueOf(indices));
		property.getAnyCmlOrAnyOrAny().add(scalar);
	}

	private void loopOverBufferMolecules(Job<JAXBElement> job,
			ObjectFactory objectFactory, PropertyList propertyList,
			Property property) {
		if (job.getProperties().containsKey("smoothfactors")) {
			List<Double> smoothFactors = (List<Double>) job.getProperties()
					.get("smoothfactors");
			if (job.getRegions().containsKey(Region.Name.BUFFER)) {
				Set<Molecule> bufferMolecules = job.getRegions()
						.get(Region.Name.BUFFER).getMolecules();
				List<Integer> bufferIndices = new ArrayList<Integer>();
				for (Molecule molecule : bufferMolecules) {
					bufferIndices.add(molecule.getIndex());
				}

				for (int counter = 0; counter < bufferIndices.size(); counter++) {
					putEveryMoleculeSmoothFactorInScalar(objectFactory,
							property, bufferIndices.get(counter),
							smoothFactors.get(counter));
				}

				propertyList.setTitle(propertyList.getTitle()
						+ "   average smooth factor:  ");
			}
		}
	}

	private void loopOverQMAndBufferRegions(Job<JAXBElement> job,
			ObjectFactory objectFactory, PropertyList propertyList,
			Property property) {
		List<Region.Name> regions= new ArrayList<Region.Name>();
		regions.add(Region.Name.QM);
		regions.add(Region.Name.BUFFER);	
		for(Region.Name region:regions){
			if (job.getRegions().containsKey(region)) {
				Set<Molecule> molecules = job.getRegions()
						.get(region).getMolecules();
				List<Integer> indices = new ArrayList<Integer>();
				for (Molecule molecule : molecules) {
					indices.add(molecule.getIndex());
				}        
				Scalar scalar = objectFactory.createScalar();
				scalar.setDataType(region.toString()+" molecules");
				scalar.setValue(String.valueOf(indices));		
				property.getAnyCmlOrAnyOrAny().add(scalar);								
			}
		}	
		}
	
	private void putEveryMoleculeSmoothFactorInScalar(
			ObjectFactory objectFactory, Property property,
			Integer bufferIndex, Double smoothFactor) {
		Scalar scalar = objectFactory.createScalar();
		scalar.setDataType("smooth function");
		scalar.setValue(String.valueOf(smoothFactor));
		scalar.setTitle("molecular index");
		scalar.setId(String.valueOf(bufferIndex));
		property.getAnyCmlOrAnyOrAny().add(scalar);
	}

}
