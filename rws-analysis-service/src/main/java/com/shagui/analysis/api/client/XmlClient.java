package com.shagui.analysis.api.client;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.shagui.analysis.util.XmlDocument;

@FunctionalInterface
public interface XmlClient {
	public URL url();

	public default XmlDocument getXmlDocument() throws ParserConfigurationException, SAXException, IOException {
		return new XmlDocument(url());
	}

}
