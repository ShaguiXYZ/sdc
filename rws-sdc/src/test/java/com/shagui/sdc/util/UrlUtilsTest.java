package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.model.RequestPropertiesModel;
import com.shagui.sdc.test.utils.ResponseBodyTest;
import com.shagui.sdc.test.utils.RwsTestUtils;

class UrlUtilsTest {
	
	@BeforeEach
	void init(){
	    MockitoAnnotations.openMocks(this);
	    UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());
	}

	@Test
	void setConfigTest() {
		UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());
	}
	
	@Test
	void urlTest() throws IOException {
		List<RequestPropertiesModel> properties = new ArrayList<RequestPropertiesModel>();
		RequestPropertiesModel model = new RequestPropertiesModel();
		model.setId(1);
		model.setKey("key");
		model.setValue("value");
		properties.add(model);
		
		UrlUtils.url("https://www.google.es/", properties);
	}
	
	@Test
	void mapResponseRuntimeExceptionTest() {
		
		assertThrows(RuntimeException.class, () -> { UrlUtils.mapResponse(RwsTestUtils.response(400, RwsTestUtils.JSON_RESPONSE_TEST), ResponseBodyTest.class); });
	}
	
	@Test
	void mapResponseStatus200Test() {
		
		UrlUtils.mapResponse(RwsTestUtils.response(200, RwsTestUtils.JSON_RESPONSE_TEST), ResponseBodyTest.class);
	}

}
