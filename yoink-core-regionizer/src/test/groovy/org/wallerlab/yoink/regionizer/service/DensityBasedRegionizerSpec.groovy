
 /* Copyright 2014-2015 the original author or authors.
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

package org.wallerlab.yoink.regionizer.service

import spock.lang.Specification;

import org.wallerlab.yoink.api.enums.*
import org.wallerlab.yoink.api.model.*
import org.wallerlab.yoink.api.service.regionizer.*;
import org.wallerlab.yoink.api.*
import org.wallerlab.yoink.api.model.bootstrap.JobParameter;

class DensityBasedRegionizerSpec extends Specification {


	def "test method regionize(Map<Region.Name, Region> regions,Map<JobParameter, Object> parameters)  return type"(){
		when:"set up a new BufferRegionizer"
		def regionizer= new DensityBasedRegionizer()
		def regions=Mock(Map)
		def parameters=Mock(Map)
		regionizer.adaptiveQMCoreRegionizer=Mock(RegionizerComponent)
		regionizer.adaptiveQMRegionizer=Mock(RegionizerComponent)
		regionizer.bufferRegionizer=Mock(RegionizerComponent)
		
		then:"method regionize can be  executed  and return regions"
		regionizer.regionize(regions, parameters)==regions
	}
}
