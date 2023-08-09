package com.shagui.sdc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.RequestPropertiesModel;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentModel;

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

	public static Optional<UriModel> componentUri(ComponentModel component, UriType type) {
		return Optional.ofNullable(uriModel(component, type).map(data -> {
			UriModel uri = config.getObjectMapper().convertValue(data, UriModel.class);
			uri.setValue(DictioraryReplacement.getInstance(ComponentUtils.dictionaryOf(component), true)
					.replace(data.getValue()));
			return uri;
		}).orElse(null));
	}

	public static Optional<String> uriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(data -> data.getName().equals(key))
				.map(RequestPropertiesModel::getValue).findFirst();
	}

	private static Optional<UriModel> uriModel(ComponentModel component, UriType type) {
		return StaticRepository.uris().stream().filter(uri -> type.equals(uri.getType()))
				.filter(uri -> component.getUris().stream().anyMatch(u -> u.getId().getUriName().equals(uri.getName())))
				.findFirst();
	}
}
