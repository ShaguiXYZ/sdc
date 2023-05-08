package com.shagui.sdc.test.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;

import com.shagui.sdc.api.domain.RequestPageInfo;
import com.shagui.sdc.enums.MetricType;
import com.shagui.sdc.enums.MetricValidation;
import com.shagui.sdc.enums.MetricValueType;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.model.ArchitectureModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentPropertyModel;
import com.shagui.sdc.model.ComponentTypeArchitectureModel;
import com.shagui.sdc.model.ComponentTypeModel;
import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.model.SquadModel;
import com.shagui.sdc.model.UriModel;
import com.shagui.sdc.repository.ComponentAnalysisRepository;
import com.shagui.sdc.repository.ComponentHistoricalCoverageRepository;
import com.shagui.sdc.repository.ComponentPropertyRepository;
import com.shagui.sdc.repository.ComponentRepository;
import com.shagui.sdc.repository.MetricValueRepository;
import com.shagui.sdc.repository.SquadRepository;
import com.shagui.sdc.util.AnalysisUtilsConfig;
import com.shagui.sdc.util.ComponentUtilsConfig;
import com.shagui.sdc.util.MapperConfig;
import com.shagui.sdc.util.UrlUtilsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Request;
import feign.Response;

public class RwsTestUtils {

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

	public static String JSON_RESPONSE_TEST = "{\"id\": 100, \"name\": \"generic response\"}";
	public static String JSON_COMPONENT_DTO_TEST = "{\"name\": \"generic response\", \"download_url\": \"http://www.url-pom.xml\"}";

	public static Response response(int status, String json) {
		Map<String, Collection<String>> headers = new HashMap<>();
		headers.put("Authorization", Collections.emptyList());
		byte[] body = new byte[0];
		Request request = Request.create(Request.HttpMethod.GET, "url", headers, body, null, null);
		Response response = Response.builder().status(status).reason("reason").request(request).headers(headers)
				.body(json, StandardCharsets.UTF_8).build();

		return response;
	}

	public static ComponentUtilsConfig config() {
		ComponentUtilsConfig config = new ComponentUtilsConfig(componentRepository, componentAnalysisRepository,
				componentPropertyRepository, historicalCoverageComponentRepository, squadRepository);

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

	public static ComponentModel componentModelMock() {
		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel model = new ComponentPropertyModel();
		model.setName("component_analysis_date");
		model.setValue("test");

		MetricModel metric = new MetricModel();
		metric.setId(1);
		metric.setName("test");
		metric.setType(MetricType.GIT);
		metric.setValidation(MetricValidation.EQ);
		metric.setValueType(MetricValueType.NUMERIC);

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		List<MetricModel> metrics = new ArrayList<MetricModel>();
		metrics.add(metric);
		componentTypeArchitecture.setMetrics(metrics);

		ComponentTypeModel componentType = new ComponentTypeModel();
		componentType.setId(1);
		componentType.setName("test");
		componentTypeArchitecture.setComponentType(componentType);

		ArchitectureModel architecture = new ArchitectureModel();
		architecture.setId(1);
		architecture.setName("test");
		componentTypeArchitecture.setArchitecture(architecture);

		SquadModel squad = new SquadModel();

		DepartmentModel department = new DepartmentModel();

		List<SquadModel> squads = new ArrayList<SquadModel>();
		SquadModel squadModel = new SquadModel();
		squadModel.setCoverage((float) 90.1);
		squads.add(squadModel);

		department.setSquads(squads);
		department.setId(1);
		department.setName("test");

		squad.setCoverage((float) 90.1);
		squad.setId(1);
		squad.setName("test");
		squad.setDepartment(department);

		ComponentModel source = new ComponentModel();
		source.setId(1);
		source.setName("test");
		source.setProperties(properties);
		source.setCoverage((float) 90.1);
		source.setComponentTypeArchitecture(componentTypeArchitecture);
		source.setSquad(squad);

		return source;
	}

	public static ComponentModel componentModelSonarMock() {
		List<ComponentPropertyModel> properties = new ArrayList<ComponentPropertyModel>();
		ComponentPropertyModel model = new ComponentPropertyModel();
		model.setName("component_analysis_date");
		model.setValue("test");

		MetricModel metric = new MetricModel();
		metric.setId(1);
		metric.setName("test");
		metric.setType(MetricType.SONAR);
		metric.setValidation(MetricValidation.EQ);
		metric.setValueType(MetricValueType.NUMERIC);

		ComponentTypeArchitectureModel componentTypeArchitecture = new ComponentTypeArchitectureModel();
		List<MetricModel> metrics = new ArrayList<MetricModel>();
		metrics.add(metric);
		componentTypeArchitecture.setMetrics(metrics);

		ComponentTypeModel componentType = new ComponentTypeModel();
		componentType.setId(1);
		componentType.setName("test");
		componentTypeArchitecture.setComponentType(componentType);

		ArchitectureModel architecture = new ArchitectureModel();
		architecture.setId(1);
		architecture.setName("test");
		componentTypeArchitecture.setArchitecture(architecture);

		SquadModel squad = new SquadModel();

		DepartmentModel department = new DepartmentModel();

		List<SquadModel> squads = new ArrayList<SquadModel>();
		SquadModel squadModel = new SquadModel();
		squadModel.setCoverage((float) 90.1);
		squads.add(squadModel);

		department.setSquads(squads);
		department.setId(1);
		department.setName("test");

		squad.setCoverage((float) 90.1);
		squad.setId(1);
		squad.setName("test");
		squad.setDepartment(department);

		UriModel urimodel = new UriModel();
		urimodel.setType(UriType.GIT);
		urimodel.setName("test");

		List<UriModel> uris = new ArrayList<UriModel>();
		uris.add(urimodel);

		ComponentModel source = new ComponentModel();
		source.setId(1);
		source.setName("test");
		source.setProperties(properties);
		source.setCoverage((float) 90.1);
		source.setComponentTypeArchitecture(componentTypeArchitecture);
		source.setSquad(squad);
		source.setUris(uris);

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

}
