package com.shagui.sdc.test.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.mockito.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.api.dto.cmdb.DepartmentInput;
import com.shagui.sdc.api.dto.cmdb.SquadInput;
import com.shagui.sdc.api.dto.ebs.ComponentInput;
import com.shagui.sdc.api.dto.ebs.ComponentPropertyInput;
import com.shagui.sdc.core.configuration.DictionaryConfig;
import com.shagui.sdc.enums.AnalysisType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentUriModel;
import com.shagui.sdc.model.ComponetTypeArchitectureMetricPropertiesModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.MetricValuesModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;
import com.shagui.sdc.model.pk.ComponentUriPk;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentPropertyRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.util.AnalysisUtilsConfig;
import com.shagui.sdc.util.ComponentUtilsConfig;
import com.shagui.sdc.util.Ctes.ComponentTypeArchitectureMetricConstants;
import com.shagui.sdc.util.MapperConfig;
import com.shagui.sdc.util.UrlUtilsConfig;

import feign.Request;
import feign.Response;

public class RwsTestUtils {

	@Mock
	private static DictionaryConfig securityTokenConfig;

	@Mock
	private static ComponentRepository componentRepository;

	@Mock
	private static ComponentAnalysisRepository componentAnalysisRepository;

	@Mock
	private static ComponentPropertyRepository componentPropertyRepository;

	@Mock
	private static ComponentHistoricalCoverageRepository historicalCoverageComponentRepository;

	@Mock
	private static SquadRepository squadRepository;

	@Mock
	private static MetricValueRepository metricValueRepository;

	@Mock
	private static ObjectMapper objectMapper;

	public static String XML_RESPONSE_TEST = "<data><project><properties><version>1</version><version2>2</version2></properties></project></data>";
	public static String JSON_SONAR_CONTENT = "{\"measures\":[{\"metric\":\"alert_status\",\"value\":\"ERROR\",\"component\":\"component:test\"},{\"metric\":\"blocker_violations\",\"value\":\"162\",\"component\":\"component:test\"}]}";
	public static String JSON_RESPONSE_TEST = "{\"id\": 100,\"name\": \"generic response\"}";
	public static String JSON_GIT_CONTENT = "{\"name\": \"generic response\", \"download_url\": \"http://www.url-pom.xml\",\"content\":\"%s\"}";
	public static String JSON_DEPENDABOT_CONTENT = "[]";

	public static String gitContentResponse(String content) {
		return String.format(JSON_GIT_CONTENT, Base64.encodeBase64String(content.getBytes()));
	}

	public static Response response(int status, String json) {
		Map<String, Collection<String>> headers = new HashMap<>();
		headers.put("Authorization", Collections.emptyList());
		byte[] body = new byte[0];
		Request request = Request.create(Request.HttpMethod.GET, "url", headers, body, null, null);
		Response response = Response.builder().status(status).reason("reason").request(request).headers(headers)
				.body(json, StandardCharsets.UTF_8).build();

		return response;
	}

	public static ComponentUtilsConfig componentUtilsConfig() {
		ComponentUtilsConfig config = new ComponentUtilsConfig(securityTokenConfig, componentRepository,
				componentAnalysisRepository, componentPropertyRepository, historicalCoverageComponentRepository,
				squadRepository);

		return config;
	}

	public static AnalysisUtilsConfig analysisUtilsConfig() {
		AnalysisUtilsConfig config = new AnalysisUtilsConfig(metricValueRepository);

		return config;
	}

	public static UrlUtilsConfig urlUtilsConfig() {
		UrlUtilsConfig config = new UrlUtilsConfig(new ObjectMapper());

		return config;
	}

	public static MapperConfig mapperConfig() {
		MapperConfig config = new MapperConfig(new ObjectMapper());

		return config;
	}

	public static ComponentPropertyModel componentProperty(String name) {
		ComponentPropertyModel model = new ComponentPropertyModel();
		model.setId(1);
		model.setName(name);
		model.setValue("test");

		return model;
	}

	public static ComponentTypeArchitectureModel componentTypeArchitectureModelMock() {
		List<MetricModel> metrics = new ArrayList<MetricModel>();
		metrics.add(metricModelMock(1, AnalysisType.GIT, "metric name 1", "metric name 1"));
		metrics.add(metricModelMock(2, AnalysisType.GIT_JSON, "metric name 2", "git metric"));
		metrics.add(metricModelMock(3, AnalysisType.DEPENDABOT, "metric name 3", "low_severity"));
		metrics.add(metricModelMock(4, AnalysisType.SONAR, "metric name 4", "sonar metric"));

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		componentTypeArchitecture.setId(1);
		componentTypeArchitecture.setMetrics(metrics);
		componentTypeArchitecture.setComponentType("componentType");
		componentTypeArchitecture.setArchitecture("architecture");
		componentTypeArchitecture.setDeploymentType("deploymentType");
		componentTypeArchitecture.setLanguage("language");
		componentTypeArchitecture.setNetwork("network");
		componentTypeArchitecture.setPlatform("platform");

		return componentTypeArchitecture;

	}

	public static ComponentModel componentModelMock() {
		List<ComponentPropertyModel> properties = new ArrayList<>();
		properties.add(componentProperty("property_name"));

		List<SquadModel> squads = new ArrayList<SquadModel>();
		SquadModel squad = new SquadModel();
		SquadModel squadModel = new SquadModel();
		squadModel.setCoverage((float) 90.1);
		squads.add(squadModel);
		squad.setCoverage((float) 90.1);
		squad.setId(1);
		squad.setName("test");

		DepartmentModel department = new DepartmentModel();
		department.setSquads(squads);
		department.setId(1);
		department.setName("test");

		squad.setDepartment(department);

		List<ComponentUriModel> uris = new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add(componentUriModelMock());
			}
		};

		ComponentModel source = new ComponentModel();
		source.setId(1);
		source.setName("test");
		source.setProperties(properties);
		source.setCoverage((float) 90.1);
		source.setComponentTypeArchitecture(componentTypeArchitectureModelMock());
		source.setSquad(squad);
		source.setUris(uris);

		return source;
	}

	public static MetricModel metricModelMock(Integer id, AnalysisType type, String name, String value) {

		MetricModel source = new MetricModel();
		source.setId(id);
		source.setName(name);
		source.setValue(value);
		source.setType(type);
		source.setValidation(MetricValidation.EQ);
		source.setValueType(MetricValueType.NUMERIC);

		return source;
	}

	public static SquadModel squadModelMock() {

		List<SquadModel> squads = new ArrayList<SquadModel>();
		SquadModel squadModel = new SquadModel();
		squadModel.setCoverage((float) 90.1);
		squads.add(squadModel);

		DepartmentModel department = new DepartmentModel();
		department.setSquads(squads);
		department.setId(1);
		department.setName("name");

		SquadModel source = new SquadModel();
		source.setId(1);
		source.setName("test");
		source.setDepartment(department);

		return source;
	}

	public static DepartmentModel departmentModelMock() {

		List<SquadModel> squads = new ArrayList<SquadModel>();
		SquadModel squadModel = new SquadModel();
		squadModel.setCoverage((float) 90.1);
		squads.add(squadModel);

		DepartmentModel source = new DepartmentModel();
		source.setSquads(squads);
		source.setId(1);
		source.setName("name");

		return source;
	}

	public static RequestPageInfo requestPageInfo() {

		return new RequestPageInfo(1);

	}

	public static MetricValuesModel metricValuesModelMock() {
		MetricValuesModel mock = new MetricValuesModel();
		mock.setId(1);
		mock.setComponentTypeArchitecture(componentTypeArchitectureModelMock());
		mock.setMetric(metricModelMock(1, AnalysisType.GIT_XML, "metric name 1", "git metric"));
		mock.setMetricValueDate(new Date());
		mock.setValue("0.0");
		mock.setGoodValue("10.0");
		mock.setPerfectValue("25.0");
		mock.setWeight(10);

		return mock;
	}

	public static ComponentAnalysisModel componentAnalysisModelMock(String value) {
		ComponentAnalysisModel mock = new ComponentAnalysisModel();
		mock.setId(new ComponentAnalysisPk(1, 1, new Date()));
		mock.setComponent(componentModelMock());
		mock.setMetric(metricModelMock(1, AnalysisType.GIT_XML, "metric name 1", "git metric"));
		mock.setComponentTypeArchitecture(componentTypeArchitectureModelMock());
		mock.setMetricValue(value);
		mock.setWeight(10);
		mock.setExpectedValue(("50"));
		mock.setGoodValue("75");
		mock.setPerfectValue("100");

		return mock;
	}

	public static ComponentInput componentInputMock() {
		ComponentInput mock = new ComponentInput();
		mock.setArchitecture("architecture");
		mock.setComponentType("component_type");
		mock.setDeploymentType("deployment_type");
		mock.setLanguage("language");
		mock.setName("component_name");
		mock.setNetwork("network");
		mock.setPlatform("platform");
		mock.setSquad(1);
		mock.setUriNames(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				add("uri_name");
			}
		});

		mock.setProperties(new ArrayList<>() {
			private static final long serialVersionUID = 1L;

			{
				ComponentPropertyInput property1 = new ComponentPropertyInput();
				property1.setName("property_name");
				property1.setValue("property value 1");

				add(property1);

				ComponentPropertyInput property2 = new ComponentPropertyInput();
				property2.setName("property name 2");
				property2.setValue("property value 2");

				add(property2);
			}
		});

		return mock;
	}

	public static DepartmentInput departmentInputMock() {
		DepartmentInput mock = new DepartmentInput();
		mock.setId(1);
		mock.setName("name");
		mock.setCia(1);
		mock.setSquads(new ArrayList<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(squadInputMock());
			}
		});

		return mock;
	}

	public static SquadInput squadInputMock() {
		SquadInput mock = new SquadInput();
		mock.setId(1);
		mock.setCia(1);
		mock.setName("name");

		return mock;
	}

	public static ComponentUriModel componentUriModelMock() {
		ComponentUriModel componentUriModel = new ComponentUriModel();
		componentUriModel.setId(new ComponentUriPk(0, "uri_name"));

		return componentUriModel;
	}

	public static UriModel uriModelMock(UriType type) {
		UriModel uri = new UriModel();
		uri.setName("uri_name");
		uri.setValue("");
		uri.setType(type);
		uri.setProperties(new ArrayList<>());

		return uri;
	}

	public static ComponetTypeArchitectureMetricPropertiesModel componetTypeArchitectureMetricPropertiesModelMock() {
		ComponetTypeArchitectureMetricPropertiesModel model = new ComponetTypeArchitectureMetricPropertiesModel();
		model.setId(1);
		model.setComponentTypeArchitecture(componentTypeArchitectureModelMock());
		model.setMetric(metricModelMock(1, AnalysisType.GIT_XML, "metric name 1", "git metric"));
		model.setName(ComponentTypeArchitectureMetricConstants.PATH);
		model.setValue("path");

		return model;
	}
}
