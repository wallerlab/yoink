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
package org.wallerlab.yoink.molecular.service.translator;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;
import org.wallerlab.yoink.api.model.regionizer.Region;
import org.wallerlab.yoink.api.service.adaptive.Smoothner;
import org.wallerlab.yoink.api.service.molecular.Translator;
import org.wallerlab.yoink.api.service.molecular.Converter.UnitConverterType;
import org.wallerlab.yoink.api.service.regionizer.Partitioner;
import org.xml_cml.schema.Cml;
import org.xml_cml.schema.Parameter;
import org.xml_cml.schema.ParameterList;

/**
 * this class is to get a Map(JobParameter -
 * {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter}, Object) by
 * parsing ParameterList in JAXBElement Cml.
 * 
 * @author Min Zheng
 *
 */
@Service
public class ParameterTranslator implements
		Translator<Map<JobParameter, Object>, JAXBElement<Cml>> {

	@Value("${yoink.job.unitconvertertype}")
	private UnitConverterType unitConverterType;

	/**
	 * get parameters a Map(JobParameter -
	 * {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter}, Object)
	 * from JAXBElement ParameterList {@link org.xml_cml.schema.ParameterList}
	 * in JAXBElement Cml {@link org.xml_cml.schema.Cml}.
	 * 
	 * @param cml
	 *            {@link javax.xml.bind.JAXBElement}
	 * @return parameters is a map, the key is a JobParameter -
	 *         {@link org.wallerlab.yoink.api.model.bootstrap.JobParameter} and
	 *         the value is an Object -{@link java.lang.Object }
	 */
	@Override
	public Map<JobParameter, Object> translate(JAXBElement<Cml> cml) {
		Map<JobParameter, Object> parameters = new HashMap<JobParameter, Object>();
		Cml cmlMolecularSystem = cml.getValue();
		parseParameterList(parameters, cmlMolecularSystem);
		return parameters;
	}

	private void parseParameterList(Map<JobParameter, Object> parameters,
			Cml cmlMolecularSystem) {
		for (Object elementList : cmlMolecularSystem.getAnyCmlOrAnyOrAny()) {
			checkIfParameterList(parameters, elementList);
		}
	}

	private void checkIfParameterList(Map<JobParameter, Object> parameters,
			Object elementList) {
		// check parameterList
		JAXBElement element = (JAXBElement) elementList;
		if (element.getDeclaredType() == ParameterList.class) {
			parseParameters(parameters, element);
		}
	}

	private void parseParameters(Map<JobParameter, Object> parameters,
			JAXBElement element) {
		ParameterList cmlParameterList = (ParameterList) element.getValue();
		if (cmlParameterList.getTitle().equalsIgnoreCase("parameters")) {
			parseParameter(parameters, cmlParameterList);
		}
	}

	private void parseParameter(Map<JobParameter, Object> parameters,
			ParameterList cmlParameterList) {
		for (Object elementParameter : cmlParameterList.getAnyCmlOrAnyOrAny()) {
			JAXBElement elementJAXB = (JAXBElement) elementParameter;
			// check parameter
			if (elementJAXB.getDeclaredType() == Parameter.class) {
				parseParameterValue(parameters, elementJAXB);
			}
		}
	}

	private void parseParameterValue(Map<JobParameter, Object> parameters,
			JAXBElement elementJAXB) {
		Parameter cmlParameter = (Parameter) elementJAXB.getValue();
		String name = cmlParameter.getName().toUpperCase();
		JobParameter jobParameter = JobParameter.valueOf(name);
		String value = cmlParameter.getValue();
		switch (jobParameter) {
		case REGION_CUBE:
			parameters.put(jobParameter, Region.Name.valueOf(value));
			break;
		case NUMBER_QM:
		case NUMBER_PARTITION:
		case NUMBER_BUFFER:
			parameters.put(jobParameter, Integer.parseInt(value));
			break;
		case DORI_STEPSIZE:
			double[] doriCubeStepSize = new double[3];
			String[] splited = value.split("\\s+");
			doriCubeStepSize[0] = Double.parseDouble(splited[0]);
			doriCubeStepSize[1] = Double.parseDouble(splited[1]);
			doriCubeStepSize[2] = Double.parseDouble(splited[2]);
			parameters.put(jobParameter, doriCubeStepSize);
			break;
		case SEDD_STEPSIZE:
			double[] seddCubeStepSize = new double[3];
			String[] splitedSize = value.split("\\s+");
			seddCubeStepSize[0] = Double.parseDouble(splitedSize[0]);
			seddCubeStepSize[1] = Double.parseDouble(splitedSize[1]);
			seddCubeStepSize[2] = Double.parseDouble(splitedSize[2]);
			parameters.put(jobParameter, seddCubeStepSize);
			break;
		case SMOOTHNER:
			parameters.put(jobParameter, Smoothner.Type.valueOf(value));
			break;
		case PARTITIONER:
			parameters.put(jobParameter, Partitioner.Type.valueOf(value));
			break;
		case JOB_NAME:
		//case INPUT_FOLDER:
		case OUTPUT_FOLDER:
			parameters.put(jobParameter, value);
			break;
		case DISTANCE_BUFFER:
		case DISTANCE_QM:
		case DISTANCE_S_QM_IN:
		case DISTANCE_T_QM_IN:
		case DISTANCE_S_QM_OUT:
		case DISTANCE_T_QM_OUT:
		case DISTANCE_S_MM_IN:
		case DISTANCE_T_MM_IN:
		case DISTANCE_S_MM_OUT:
		case DISTANCE_T_MM_OUT:
			parameters.put(jobParameter, Double.parseDouble(value)
					* unitConverterType.value());
			break;
		default:
			parameters.put(jobParameter, Double.parseDouble(value));
		}
	}

}
