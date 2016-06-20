package org.wallerlab.yoink.cluster.service.louvain

import org.wallerlab.yoink.cluster.domain.cluster.Community
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
