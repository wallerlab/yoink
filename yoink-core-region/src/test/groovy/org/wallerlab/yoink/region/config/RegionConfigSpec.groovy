package org.wallerlab.yoink.region.config

import spock.lang.Specification

/**
 * Created by waller on 11/08/16.
 */
class RegionConfigSpec extends Specification{

    def "Name"() {
        when:
        def regionConfig= new RegionConfig()

        then:
            regionConfig.regionizer()

    }
}
