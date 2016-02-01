package org.wallerlab.yoink.molecular.data


import javax.xml.bind.JAXBElement
import org.wallerlab.yoink.molecular.domain.SimpleCoord
import org.wallerlab.yoink.molecular.domain.SimpleMolecularSystem
import org.xml_cml.schema.Cml
import spock.lang.Specification
import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationsException
import javax.xml.bind.JAXBElement;
import org.xml_cml.schema.MoleculeList;

class JaxbFileReaderSpec extends Specification{

	def "test method read()"(){
		when:"jaxb reader reads a given file"
			def reader= new JaxbFileReader()
			JAXBElement<Cml> msr= reader.read("./src/test/resources/lih.xml",new Cml())
			JAXBElement mlJAXB =((((JAXBElement)msr.getValue().getAnyCmlOrAnyOrAny().get(0))))
		MoleculeList ml= mlJAXB.getValue()
			then:"assert the content in the given file"
			msr.getValue().getAnyCmlOrAnyOrAny().size()==2
			ml.getAnyCmlOrAnyOrAny().size()==2
	}
}
