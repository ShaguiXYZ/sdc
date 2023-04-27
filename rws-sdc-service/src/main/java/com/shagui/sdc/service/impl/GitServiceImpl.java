package com.shagui.sdc.service.impl;

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

import com.shagui.sdc.api.client.GitClient;
import com.shagui.sdc.api.dto.git.ContentDTO;
import com.shagui.sdc.core.configuration.AppConfig;
import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.UriModel;
import com.shagui.sdc.service.GitService;
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.UrlUtils;
import com.shagui.sdc.util.XmlDocument;

import feign.Response;
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
				response.add(new ComponentAnalysisModel(component, metric, value.isPresent() ? value.get() : "N/A"));
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

		Response response = authorizationHeader.isPresent()
				? gitClient.repoFile(URI.create(uri), authorizationHeader.get())
				: gitClient.repoFile(URI.create(uri));

		return UrlUtils.mapResponse(response, ContentDTO.class);
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
