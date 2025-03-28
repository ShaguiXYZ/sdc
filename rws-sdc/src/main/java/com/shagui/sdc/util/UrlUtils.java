package com.shagui.sdc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JavaType;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.RequestPropertiesModel;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.util.DictioraryReplacement.Replacement;

import feign.Response;

public class UrlUtils {
	private static final String STATUS_MESSAGE = "status %s for url %s";

	private static UrlUtilsConfig config;

	private UrlUtils() {
	}

	protected static void setConfig(UrlUtilsConfig config) {
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
		JavaType type = config.getObjectMapper().getTypeFactory().constructType(clazz);
		return mapResponse(response, type);
	}

	public static <C extends Collection<T>, T> C mapResponse(Response response, Class<C> collectionClass,
			Class<T> clazz) {
		JavaType type = config.getObjectMapper().getTypeFactory().constructCollectionType(collectionClass, clazz);
		return mapResponse(response, type);
	}

	public static <T> T mapResponse(Response response, JavaType type) {
		return responseToType(response, type);
	}

	public static Optional<UriModel> componentUri(ComponentModel component, UriType type) {
		return uriModel(component, type).map(data -> {
			UriModel uri = config.getObjectMapper().convertValue(data, UriModel.class);
			Replacement replacement = DictioraryReplacement.getInstance(ComponentUtils.dictionaryOf(component), true);

			uri.setApi(replacement.replace(data.getApi()));
			uri.setUrl(replacement.replace(data.getUrl()));

			return uri;
		});
	}

	public static Optional<String> uriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(data -> data.getName().equals(key))
				.map(RequestPropertiesModel::getValue).findFirst();
	}

	private static <T> T responseToType(Response response, JavaType type) {
		try (InputStream bodyIs = statusResponse(response).body().asInputStream()) {
			return config.getObjectMapper().readValue(bodyIs, type);
		} catch (IOException ex) {
			throw new SdcCustomException(HttpStatus.resolve(response.status()),
					"error mapping response to %s".formatted(type.getTypeName()));
		}
	}

	private static Response statusResponse(Response response) {
		if (response.status() >= 400) {
			throw new SdcCustomException(HttpStatus.resolve(response.status()),
					STATUS_MESSAGE.formatted(response.status(), response.request().url()));
		}

		return response;
	}

	private static Optional<UriModel> uriModel(ComponentModel component, UriType type) {
		Stream<Entry<String, UriModel>> urisByType = StaticRepository.uris().entrySet().stream()
				.filter(entry -> entry.getValue().getType().equals(type));

		return urisByType.map(Entry::getValue)
				.filter(uri -> component.getUris().stream().anyMatch(u -> u.getId().getUriName().equals(uri.getName())))
				.findFirst();
	}

	public static Optional<String> authorization(UriModel uriModel) {
		Optional<String> authorization = UrlUtils.uriProperty(uriModel, Ctes.UriProperties.AUTHORIZATION);

		return authorization.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data, ""));
	}
}
