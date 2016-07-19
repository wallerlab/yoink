package org.wallerlab.yoink.molecule.data

import org.cml_v3.generated.Cml
import spock.lang.Specification

import javax.xml.bind.JAXBElement;
import org.cml_v3.generated.MoleculeList;

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
