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
package org.wallerlab.yoink.molecular.data

import org.wallerlab.yoink.api.model.molecular.Atom;
import org.wallerlab.yoink.api.model.molecular.Coord;
import org.wallerlab.yoink.api.model.molecular.Element;
import org.wallerlab.yoink.api.service.math.Vector;
import org.wallerlab.yoink.math.linear.SimpleMatrixFactory
import org.wallerlab.yoink.molecular.domain.RadialGrid;
import org.wallerlab.yoink.api.service.math.Matrix;

import spock.lang.Specification

class RadialGridReaderSpec extends Specification {
	def "test read(String wfc_file, RadialGrid radial_grid)"(){
		when:
		def grid= new RadialGrid()
		 def reader= new RadialGridReader()
		 def myMatrix= new SimpleMatrixFactory()
		myMatrix.matrixType=Matrix.Type.COMMONS
		reader.myMatrix=myMatrix
		then:
		reader.read("/Users/Zheng/Yoink-git/yoink/yoink-core-molecular/dat/c__lda.wfc",  grid)
		 
	}

}
