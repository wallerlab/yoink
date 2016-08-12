package org.wallerlab.yoink.batch.service.processor

import org.wallerlab.yoink.api.model.Job
import org.wallerlab.yoink.api.service.region.Regionizer
import spock.lang.Specification

/**
 * Created by waller on 12/08/16.
 */
class RegionizerProcessorSpec extends Specification{

    def "test processor delegation"() {

        when:
         def regionizerProcessor  = new RegionizerProcessor()
          def job = Mock(Job)
        def regionizer = Mock(Regionizer)
        regionizerProcessor.regionizer = regionizer
        regionizer.regionize(job) >> job

        then:
         job ==  regionizerProcessor.process(job)

    }
}
