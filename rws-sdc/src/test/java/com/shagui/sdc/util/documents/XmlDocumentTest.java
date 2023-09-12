package com.shagui.sdc.util.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import com.shagui.sdc.util.documents.lib.xml.XmlDocument;

class XmlDocumentTest {
	@Test
	void urlConstructorTest() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		XmlDocument document = SdcDocumentFactory.newInstance(
				"<project><properties><version>1</version><version2>2</version2></properties></project>",
				XmlDocument.class);

		Optional<String> data = document.value("properties/version");

		assertNotNull(data);
		assertTrue(data.isPresent());
		assertEquals("1", data.get());
	}

}
