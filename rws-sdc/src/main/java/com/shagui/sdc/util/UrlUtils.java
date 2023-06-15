package com.shagui.sdc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.json.data.RequestPropertiesModel;

import feign.Response;

public class UrlUtils {

	private static UrlUtilsConfig config;

	private UrlUtils() {
	}

	public static void setConfig(UrlUtilsConfig config) {
		UrlUtils.config = config;
	}

	public static URL url(String uri) throws IOException {
		return new URL(uri);
	}

	public static URL url(String uri, List<RequestPropertiesModel> properties) throws IOException {
		URL url = UrlUtils.url(uri);
		URLConnection http = url.openConnection();

		properties.forEach(property -> http.setRequestProperty(property.getName(), property.getValue()));

		return url;
	}

	public static <T> T mapResponse(Response response, Class<T> clazz) {
		if (response.status() >= 400) {
			throw new SdcCustomException(
					String.format("status %s calling %s", response.status(), response.request().url()));
		}

		try (InputStream bodyIs = response.body().asInputStream()) {
			return config.getObjectMapper().readValue(bodyIs, clazz);
		} catch (IOException ex) {
			throw new SdcCustomException(String.format("error mapping response to %s", clazz.getName()));
		}
	}
}
