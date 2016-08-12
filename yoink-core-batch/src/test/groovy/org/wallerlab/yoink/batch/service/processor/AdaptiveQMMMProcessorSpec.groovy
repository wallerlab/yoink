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
package org.wallerlab.yoink.batch.service.processor

import org.wallerlab.yoink.api.service.plugin.QmMmWrapper
import org.wallerlab.yoink.api.service.region.Regionizer
import org.wallerlab.yoink.region.service.SimpleRegionizer
import spock.lang.Ignore
import spock.lang.Specification
import org.wallerlab.yoink.api.enums.*

class AdaptiveQMMMProcessorSpec extends Specification{

    @Ignore //not implemented yet
	def "test method process(List<File> requests)"(){
		def file=new File("./src/test/resources/AdaptiveQMMMProcessorSpec.xml")
		def requests=[file]
		def propertyWrapper=Mock(Wrapper)
		def  adaptiveQMMMSmoothnerRouter=Mock(Adaptive)
		def jobBuilder=Mock(JobBuilder)
		def preRegionizer=Mock(SimpleRegionizer)
		def postRegionizer=Mock(SimpleRegionizer)
		def adaptiveQMMMRegionizer=Mock(SimpleRegionizer)
		List<Regionizer> regionizers=new ArrayList<SimpleRegionizer>();
		regionizers.add(adaptiveQMMMRegionizer)
		regionizers.add(adaptiveQMMMRegionizer)
		jobBuilder.build(_)>>Mock(Job)

		when:"set up a new QmMmWrapper"
		def processor = Mock(QmMmWrapper)
		processor.preRegionizer=preRegionizer
		processor.postRegionizer=postRegionizer
		processor.regionizers=regionizers
		processor.propertyWrapper=propertyWrapper
		processor.adaptiveQMMMSmoothnerRouter=adaptiveQMMMSmoothnerRouter

		then:"nothing is asserted here, except that no error is not thrown"
		processor.process(requests)
	}
}
