package com.shagui.analysis.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.shagui.analysis.api.client.XmlClient;
import com.shagui.analysis.core.configuration.AppConfig;
import com.shagui.analysis.enums.MetricType;
import com.shagui.analysis.enums.UriType;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.ComponentPropertyModel;
import com.shagui.analysis.model.MetricModel;
import com.shagui.analysis.model.UriModel;
import com.shagui.analysis.service.XmlService;
import com.shagui.analysis.util.Ctes;
import com.shagui.analysis.util.UrlUtils;
import com.shagui.analysis.util.XmlDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(Ctes.ANALYSIS_SERVICES_TYPES.XML)
public class XmlServiceImpl implements XmlService {
	@Autowired
	private AppConfig appConfig;

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<MetricModel> xmlMetrics = component.getComponentTypeArchitecture().getMetrics().stream()
				.filter(metric -> MetricType.XML.equals(metric.getType())).collect(Collectors.toList());

		if (!xmlMetrics.isEmpty()) {
			try {
				Optional<UriModel> uri;

				if ((uri = getUri(component.getUris(), UriType.FILE)).isPresent()) {
					String xmlPath = getXmlPath(component.getProperties());
					XmlDocument docuemnt = xmlDocument(component, uri.get(), xmlPath);
					return getResponse(component, xmlMetrics, docuemnt);
				}
			} catch (IOException | ParserConfigurationException | SAXException ex) {
				log.error("Reading XML docuemnt...", ex);
			}
		}

		return new ArrayList<>();
	}

	private List<ComponentAnalysisModel> getResponse(ComponentModel component, List<MetricModel> metrics,
			XmlDocument docuemnt) {
		List<ComponentAnalysisModel> response = new ArrayList<>();

		metrics.forEach(metric -> {
			try {
				Optional<String> value = docuemnt.fromPath(metric.getName()).stream().findFirst();

				if (value.isPresent()) {
					response.add(new ComponentAnalysisModel(component, metric, value.get()));
				}
			} catch (XPathExpressionException ex) {
				log.error("ERROR in metric {}.", metric.getName());
				log.error("ERROR ...", ex);
			}
		});

		return response;
	}

	private XmlDocument xmlDocument(ComponentModel component, UriModel uriModel, String xmlPath)
			throws IOException, ParserConfigurationException, SAXException {
		String uri = new StringBuffer(uriModel.getUri()).append("/")
				.append(StringUtils.hasText(component.getPath()) ? component.getPath() : component.getName())
				.append("/").append(xmlPath).toString();

		URL url = UrlUtils.url(uri, uriModel.getProperties());

		XmlClient client = () -> url;
		return client.getXmlDocument();
	}

	private String getXmlPath(List<ComponentPropertyModel> properties) {
		Optional<ComponentPropertyModel> model = properties.stream()
				.filter(property -> Ctes.COMPONENT_PROPERTIES.XML_PATH.equals(property.getName())).findFirst();
		return model.isPresent() ? model.get().getValue() : appConfig.getDefaultXmlPath();
	}
}
