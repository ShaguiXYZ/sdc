package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.shagui.sdc.json.data.RequestPropertiesModel;
import com.shagui.sdc.test.utils.ResponseBodyMock;
import com.shagui.sdc.test.utils.RwsTestUtils;

class UrlUtilsTest {

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		UrlUtils.setConfig(RwsTestUtils.urlUtilsConfig());
	}

	@Test
	void urlTest() throws IOException {
		List<RequestPropertiesModel> properties = new ArrayList<RequestPropertiesModel>();
		RequestPropertiesModel model = new RequestPropertiesModel();
		model.setName("key");
		model.setValue("value");
		properties.add(model);

		URL result = UrlUtils.url("https://www.google.es/", properties);

		assertNotNull(result);
	}

	@Test
	void mapResponseRuntimeExceptionTest() {
		assertThrows(RuntimeException.class, () -> {
			UrlUtils.mapResponse(RwsTestUtils.response(400, RwsTestUtils.JSON_RESPONSE_TEST), ResponseBodyMock.class);
		});
	}

	@Test
	void mapResponseStatus200Test() {
		ResponseBodyMock result = UrlUtils.mapResponse(RwsTestUtils.response(200, RwsTestUtils.JSON_RESPONSE_TEST),
				ResponseBodyMock.class);

		assertEquals(100, result.getId());
	}

}
