package org.wallerlab.yoink.batch.clustering.louvain

import spock.lang.Specification

class CommuntyInterfaceSpec extends Specification {

	def testmethods(){
		setup:
		Community c = Mock()
		
		
		c.getInternalEdgeWeightSum();
		c.getId()
		c.getLabel()
		c.getNodes()
		c.size()
		
		
		
		
		
	}
	
	
}
