package org.wallerlab.yoink.region.config

import spock.lang.Specification;

import org.wallerlab.yoink.api.service.region.Partitioner;
import org.wallerlab.yoink.region.config.RegionizerConfig;

class RegionizerConfigSpec extends Specification {

	def"test regionizerServiceStarting"(){

		def config= new RegionizerConfig()

		when:
		def result = config.regionizerServiceStarting()

		then:
		assert result.class.name =="org.wallerlab.yoink.region.service.RegionizerService"
		assert result.getRegionNames().size() == 5
	}

	def"test regionizerServiceEnding"(){

		def config= new RegionizerConfig()

		when:
		def result = config.regionizerServiceEnding()

		then:
		assert result.class.name =="org.wallerlab.yoink.region.service.RegionizerService"
		assert result.getRegionNames().size() == 2
	}
	def "test adaptiveQMRegionizer()"(){

		def config= new RegionizerConfig()
		def densityOverlapRegionsIndicatorPartitioner = Mock(Partitioner)
		when:
		config.densityOverlapRegionsIndicatorPartitioner=densityOverlapRegionsIndicatorPartitioner
		def result = config.adaptiveQMRegionizer()
		then:
		assert result.class.name=="org.wallerlab.yoink.region.service.AdaptiveRegionizer"
		assert result.getDensityType().toString()=="DORI"
	}

	def "test adaptiveQMCoreRegionizer()"(){
		def config= new RegionizerConfig()
		def singleExponentialDecayDetectorPartitioner = Mock(Partitioner)
		when:
		config.singleExponentialDecayDetectorPartitioner=singleExponentialDecayDetectorPartitioner
		def result = config.adaptiveQMCoreRegionizer()
		then:
		assert result.class.name=="org.wallerlab.yoink.region.service.AdaptiveRegionizer"
		assert result.getDensityType().toString()=="SEDD"
	}
}
