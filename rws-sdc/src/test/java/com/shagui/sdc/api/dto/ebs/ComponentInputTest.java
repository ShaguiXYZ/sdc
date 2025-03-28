package com.shagui.sdc.api.dto.ebs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.StaticRepositoryConfig;
import com.shagui.sdc.json.model.ComponentParamsModel;
import com.shagui.sdc.json.model.ParamConfigModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.test.utils.ReflectUtils;

class ComponentInputTest {
	@Mock
	private StaticRepositoryConfig config;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		ReflectUtils.invoke(StaticRepository.class, "setConfig", config);
	}

	@Test
	void constructorAndGetters() {
		ComponentInput input = new ComponentInput();
		assertNotNull(input);
		assertNull(input.getName());
		assertNull(input.getComponentType());
		assertNull(input.getNetwork());
		assertNull(input.getDeploymentType());
		assertNull(input.getPlatform());
		assertNull(input.getArchitecture());
		assertNull(input.getLanguage());
		assertEquals(0, input.getSquad());
		assertNull(input.getProperties());
		assertNull(input.getUriNames());
	}

	@Test
	void settersAndGetters() {
		ComponentInput input = new ComponentInput();
		input.setName("test");
		input.setComponentType("testType");
		input.setNetwork("testNetwork");
		input.setDeploymentType("testDeploymentType");
		input.setPlatform("testPlatform");
		input.setArchitecture("testArchitecture");
		input.setLanguage("testLanguage");
		input.setSquad(1);

		List<ComponentPropertyInput> properties = new ArrayList<>();
		ComponentPropertyInput property1 = new ComponentPropertyInput();
		property1.setName("prop1");
		property1.setValue("value1");
		properties.add(property1);

		ComponentPropertyInput property2 = new ComponentPropertyInput();
		property2.setName("prop2");
		property2.setValue("value2");
		properties.add(property2);

		input.setProperties(properties);

		List<String> uriNames = new ArrayList<>();
		uriNames.add("uri1");
		uriNames.add("uri2");
		input.setUriNames(uriNames);

		assertEquals("test", input.getName());
		assertEquals("testType", input.getComponentType());
		assertEquals("testNetwork", input.getNetwork());
		assertEquals("testDeploymentType", input.getDeploymentType());
		assertEquals("testPlatform", input.getPlatform());
		assertEquals("testArchitecture", input.getArchitecture());
		assertEquals("testLanguage", input.getLanguage());
		assertEquals(1, input.getSquad());
		assertEquals(2, input.getProperties().size());
		assertEquals("prop1", input.getProperties().get(0).getName());
		assertEquals("value1", input.getProperties().get(0).getValue());
		assertEquals("prop2", input.getProperties().get(1).getName());
		assertEquals("value2", input.getProperties().get(1).getValue());
		assertEquals(2, input.getUriNames().size());
		assertEquals("uri1", input.getUriNames().get(0));
		assertEquals("uri2", input.getUriNames().get(1));
	}

	@Test
	void asModel() {
		when(config.componentParams()).thenReturn(new ArrayList<>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				ComponentParamsModel model = new ComponentParamsModel();
				model.setType("testType");
				model.setParams(new ArrayList<>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
						ParamConfigModel param = new ParamConfigModel();
						param.setName("prop1");
						add(param);
					}
				});

				add(model);
			}
		});

		ComponentInput input = new ComponentInput();
		input.setName("test");
		input.setComponentType("testType");
		input.setNetwork("testNetwork");
		input.setDeploymentType("testDeploymentType");
		input.setPlatform("testPlatform");
		input.setArchitecture("testArchitecture");
		input.setLanguage("testLanguage");
		input.setSquad(1);

		List<ComponentPropertyInput> properties = new ArrayList<>();
		ComponentPropertyInput property1 = new ComponentPropertyInput();
		property1.setName("prop1");
		property1.setValue("value1");
		properties.add(property1);

		ComponentPropertyInput property2 = new ComponentPropertyInput();
		property2.setName("prop2");
		property2.setValue("value2");
		properties.add(property2);

		input.setProperties(properties);

		List<String> uriNames = new ArrayList<>();
		uriNames.add("uri1");
		uriNames.add("uri2");
		input.setUriNames(uriNames);

		ComponentModel model = input.asModel();

		assertNotNull(model);
		assertEquals("test", model.getName());
		assertEquals(2, model.getProperties().size());
		assertEquals("prop1", model.getProperties().get(0).getName());
		assertEquals("value1", model.getProperties().get(0).getValue());
		assertEquals("prop2", model.getProperties().get(1).getName());
		assertEquals("value2", model.getProperties().get(1).getValue());
	}

}