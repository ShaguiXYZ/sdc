package com.shagui.analysis.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.shagui.analysis.api.client.GitClient;
import com.shagui.analysis.api.dto.git.ContentDTO;
import com.shagui.analysis.core.configuration.AppConfig;
import com.shagui.analysis.enums.MetricType;
import com.shagui.analysis.enums.UriType;
import com.shagui.analysis.model.ComponentAnalysisModel;
import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.MetricModel;
import com.shagui.analysis.model.UriModel;
import com.shagui.analysis.service.GitService;
import com.shagui.analysis.util.ComponentUtils;
import com.shagui.analysis.util.Ctes;
import com.shagui.analysis.util.UrlUtils;
import com.shagui.analysis.util.XmlDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(Ctes.ANALYSIS_SERVICES_TYPES.GIT)
public class GitServiceImpl implements GitService {
	@Autowired
	private AppConfig appConfig;

	@Autowired
	private GitClient gitClient;

	@Override
	public List<ComponentAnalysisModel> analyze(ComponentModel component) {
		List<MetricModel> gitMetrics = component.getComponentTypeArchitecture().getMetrics().stream()
				.filter(metric -> MetricType.GIT.equals(metric.getType())).collect(Collectors.toList());

		if (!gitMetrics.isEmpty()) {
			Optional<UriModel> uri;

			if ((uri = getUri(component.getUris(), UriType.GIT)).isPresent()) {
				ContentDTO gitData = retrieveGitData(component, uri.get());
				XmlDocument docuemnt = xmlDocument(gitData.getDownloadUrl());

				return getResponse(component, gitMetrics, docuemnt);
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

	private ContentDTO retrieveGitData(ComponentModel component, UriModel uriModel) {
		String xmlPath = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.XML_PATH,
				appConfig.getDefaultXmlPath());
		String path = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.PATH, component.getName());
		String owner = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_OWNER);
		String repository = ComponentUtils.propertyValue(component, Ctes.COMPONENT_PROPERTIES.COMPONENT_REPOSITORY);

		String uri = Arrays.asList(uriModel.getUri(), owner, repository, "contents", path, xmlPath).stream()
				.filter(StringUtils::hasText).collect(Collectors.joining("/"));

		Optional<String> authorizationHeader = getUriProperty(uriModel, Ctes.URI_PROPERTIES.AUTHORIZATION);

		ContentDTO data = authorizationHeader.isPresent()
				? gitClient.repoFile(URI.create(uri), authorizationHeader.get())
				: gitClient.repoFile(URI.create(uri));

		return data;
	}

	private XmlDocument xmlDocument(String uri) {
		try {
			URL url = UrlUtils.url(uri);

			return new XmlDocument(url);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			log.error("Reading XML docuemnt...", e);
			return null;
		}

	}

}
