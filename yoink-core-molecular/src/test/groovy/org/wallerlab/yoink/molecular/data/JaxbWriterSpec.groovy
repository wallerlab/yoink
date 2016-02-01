package org.wallerlab.yoink.molecular.data

import javax.xml.bind.JAXBElement
import org.apache.commons.io.FileUtils
import org.xml_cml.schema.Cml

import spock.lang.Specification

class JaxbWriterSpec extends Specification {

	def"write()"(){
		when:"jaxb writer writes  an object from jaxb reader"
		def reader= new JaxbFileReader()
		def msr=(JAXBElement<Cml>)reader.read("./src/test/resources/lih.xml", new Cml())
		def writer= new JaxbFileWriter()
		writer.write("./src/test/resources/lih-w.xml", msr.getValue())
		then:"the out file and the input file are the same"
		File file1= new File("./src/test/resources/lih.xml")
		File file2= new File("./src/test/resources/lih-w.xml")
		FileUtils.contentEquals(file1, file2);
	}
}
