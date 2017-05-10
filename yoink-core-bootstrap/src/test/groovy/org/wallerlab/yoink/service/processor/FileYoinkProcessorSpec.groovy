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

package org.wallerlab.yoink.service.processor

import spock.lang.Specification;

import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.service.adaptive.Smoothner
import org.wallerlab.yoink.api.service.region.YoinkProcessor
import org.wallerlab.yoink.service.processor.YoinkProcessor;
import org.wallerlab.yoink.api.model.bootstrap.Job
import org.wallerlab.yoink.api.service.molecule.FilesReader
import org.wallerlab.yoink.api.service.region.Regionizer
import org.wallerlab.yoink.api.service.region.RegionizerMath;
import org.wallerlab.yoink.api.service.bootstrap.JobBuilder
import org.wallerlab.yoink.api.service.bootstrap.Wrapper;
import org.xml_cml.schema.ObjectFactory
import org.wallerlab.yoink.api.service.graph.Grapher;
import org.wallerlab.yoink.api.service.graph.Clusterer;

class FileYoinkProcessorSpec  extends Specification {

	
	def "test method process(String file)"(){
	
		def request = "test"
		def clusterer = Mock(Clusterer)
		def propertyWrapper=Mock(Wrapper)
		def  adaptiveQMMMSmoothnerRouter=Mock(Smoothner)
		def jobBuilder=Mock(JobBuilder)
		def regionizerServiceStarting=Mock(RegionizerMath)
		def regionizerServiceEnding=Mock(RegionizerMath)
		def adaptiveQMMMRegionizer=Mock(Regionizer)
		def interactionList = Mock(Grapher)
		List<Regionizer> adaptiveQMMMRegionizers=new ArrayList<Regionizer>();
		adaptiveQMMMRegionizers.add(adaptiveQMMMRegionizer)
		adaptiveQMMMRegionizers.add(adaptiveQMMMRegionizer)
		jobBuilder.build(_)>>Mock(Job)
	
		
		when:"set up a new AdaptiveQMMMProcessor"
		def processor=new FileYoinkProcessor()
		processor.jobFileBuilderImpl=jobBuilder
		processor.regionizerServiceStarting=regionizerServiceStarting
		processor.regionizerServiceEnding=regionizerServiceEnding
		processor.adaptiveQMMMRegionizers=adaptiveQMMMRegionizers
		processor.propertyWrapper=propertyWrapper
		processor.adaptiveQMMMSmoothnerRouter=adaptiveQMMMSmoothnerRouter
		processor.dORIGrapher=interactionList
		processor.clusterer = clusterer
		
		then:"nothing is asserted here, except that no error is not thrown"
		processor.process(request)
	}
}

