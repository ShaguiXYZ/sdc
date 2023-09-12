package com.shagui.sdc.util.documents.lib.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.util.documents.SdcDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlDocument implements SdcDocument {
	private Document document;

	@Override
	public void input(InputStream data) throws IOException {
		try {
			DocumentBuilder builder = documentBuilder();
			this.document = builder.parse(data);
		} catch (SAXException | ParserConfigurationException e) {
			throw new SdcCustomException(e.getMessage());
		}
	}

	@Override
	public Optional<String> value(String path) {
		return streamFromPath(path).findFirst();
	}

	public List<String> values(String path) {
		return streamFromPath(path).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> values(String path, Class<T> clazz) {
		return (List<T>) nodeStream(path).map(node -> {
			try {
				return value(node, clazz);
			} catch (JAXBException e) {
				log.error(String.format("Error procesing XML path: %s", path), e);
				return Optional.empty();
			}
		}).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> value(Node node, Class<T> clazz) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		T data = (T) unmarshaller.unmarshal(node);
		return Optional.ofNullable(data);
	}

	private Stream<String> streamFromPath(String path) {
		return nodeStream(path).filter(n -> n.getNodeType() == Node.ELEMENT_NODE).map(Node::getTextContent);
	}

	private Stream<Node> nodeStream(String path) {
		try {
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(path);
			NodeList list = (NodeList) expr.evaluate(this.document.getDocumentElement(), XPathConstants.NODESET);

			log.debug("Num nodes from '{}': {}", path, list.getLength());

			return IntStream.range(0, list.getLength()).mapToObj(list::item);
		} catch (XPathExpressionException e) {
			throw new SdcCustomException(String.format("ERROR in metric '%s'.", path), e);
		}
	}

	private DocumentBuilder documentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Disable access to external entities in XML parsing.
		// XML parsers should not be vulnerable to XXE attacks
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setXIncludeAware(false);

		return factory.newDocumentBuilder();
	}
}
