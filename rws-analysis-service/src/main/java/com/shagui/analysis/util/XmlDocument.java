package com.shagui.analysis.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlDocument {
	private Element root;

	public XmlDocument(URL url) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.root = builder.parse(url.openStream()).getDocumentElement();
	}

	public List<String> fromPath(String path) throws XPathExpressionException {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(path);
		NodeList list = (NodeList) expr.evaluate(this.root, XPathConstants.NODESET);

		log.debug("Num nodes: {}", list.getLength());

		Stream<Node> nodeStream = IntStream.range(0, list.getLength()).mapToObj(list::item);
		List<String> result = nodeStream.filter(n -> n.getNodeType() == Node.ELEMENT_NODE).map(Node::getTextContent)
				.collect(Collectors.toList());

		return result;
	}
}
