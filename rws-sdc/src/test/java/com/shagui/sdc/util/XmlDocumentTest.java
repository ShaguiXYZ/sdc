package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class XmlDocumentTest {
	@Test
	void urlConstructorTest() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		XmlDocument document = new XmlDocument("<project><properties><version>1</version></properties></project>");

		List<String> data = document.fromPath("properties/version");

		assertNotNull(data);
		assertEquals(1, data.size());
		assertEquals("1", data.get(0));
	}

}
