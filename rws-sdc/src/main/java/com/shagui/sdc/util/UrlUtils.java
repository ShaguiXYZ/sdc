package com.shagui.sdc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;

import com.fasterxml.jackson.databind.JavaType;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.StaticRepository;
import com.shagui.sdc.json.model.RequestPropertiesModel;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.util.DictioraryReplacement.Replacement;

import feign.Response;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The {@code UrlUtils} class provides utility methods for handling URLs, HTTP
 * responses,
 * and URI-related operations. It integrates with other components such as
 * {@code DictioraryReplacement},
 * {@code StaticRepository}, and {@code ComponentUtils} to perform tasks like
 * URL creation,
 * response mapping, and URI property resolution.
 *
 * <p>
 * This class relies on a configurable {@code UrlUtilsConfig} object for
 * dependency injection
 * of required components like the object mapper.
 */
public class UrlUtils {
	private static final String STATUS_MESSAGE = "status %s for url %s";

	private static UrlUtilsConfig config;

	private UrlUtils() {
		// Prevent instantiation
	}

	/**
	 * Sets the configuration for {@code UrlUtils}.
	 *
	 * @param config the {@code UrlUtilsConfig} object to set
	 */
	protected static void setConfig(UrlUtilsConfig config) {
		UrlUtils.config = config;
	}

	/**
	 * Creates a {@code URL} object from the given URI string.
	 *
	 * @param uri the URI string
	 * @return a {@code URL} object
	 * @throws IOException if the URI is invalid
	 */
	public static URL url(String uri) throws IOException {
		return new URL(uri);
	}

	/**
	 * Creates a {@code URL} object from the given URI string and applies request
	 * properties.
	 *
	 * @param uri        the URI string
	 * @param properties a list of {@code RequestPropertiesModel} to set as request
	 *                   properties
	 * @return a {@code URL} object
	 * @throws IOException if the URI is invalid
	 */
	public static URL url(String uri, List<RequestPropertiesModel> properties) throws IOException {
		URL url = UrlUtils.url(uri);
		URLConnection http = url.openConnection();

		properties.forEach(property -> http.setRequestProperty(property.getName(), property.getValue()));

		return url;
	}

	/**
	 * Maps the response body of an HTTP response to an object of the specified
	 * class.
	 *
	 * @param <T>      the type of the object to map to
	 * @param response the HTTP response
	 * @param clazz    the class of the object to map to
	 * @return the mapped object
	 */
	public static <T> T mapResponse(Response response, Class<T> clazz) {
		JavaType type = config.getObjectMapper().getTypeFactory().constructType(clazz);
		return mapResponse(response, type);
	}

	/**
	 * Maps the response body of an HTTP response to a collection of objects of the
	 * specified type.
	 *
	 * @param <C>             the type of the collection
	 * @param <T>             the type of the objects in the collection
	 * @param response        the HTTP response
	 * @param collectionClass the class of the collection
	 * @param clazz           the class of the objects in the collection
	 * @return the mapped collection
	 */
	public static <C extends Collection<T>, T> C mapResponse(Response response, Class<C> collectionClass,
			Class<T> clazz) {
		JavaType type = config.getObjectMapper().getTypeFactory().constructCollectionType(collectionClass, clazz);
		return mapResponse(response, type);
	}

	/**
	 * Maps the response body of an HTTP response to an object of the specified
	 * type.
	 *
	 * @param <T>      the type of the object to map to
	 * @param response the HTTP response
	 * @param type     the {@code JavaType} of the object to map to
	 * @return the mapped object
	 */
	public static <T> T mapResponse(Response response, JavaType type) {
		return responseToType(response, type);
	}

	/**
	 * Retrieves the URI model for a specific component and URI type.
	 *
	 * @param component the {@code ComponentModel} representing the component
	 * @param type      the {@code UriType} of the URI
	 * @return an {@code Optional} containing the {@code UriModel}, or empty if not
	 *         found
	 */
	public static Optional<UriModel> componentUri(ComponentModel component, UriType type) {
		return uriModel(component, type).map(data -> {
			UriModel uri = config.getObjectMapper().convertValue(data, UriModel.class);
			Replacement replacement = DictioraryReplacement.getInstance(ComponentUtils.dictionaryOf(component), true);

			uri.setApi(replacement.replace(data.getApi()));
			uri.setUrl(replacement.replace(data.getUrl()));

			return uri;
		});
	}

	/**
	 * Retrieves the value of a specific property from a URI model.
	 *
	 * @param uri the {@code UriModel} to search
	 * @param key the property key
	 * @return an {@code Optional} containing the property value, or empty if not
	 *         found
	 */
	public static Optional<String> uriProperty(UriModel uri, String key) {
		return uri.getProperties().stream().filter(data -> data.getName().equals(key))
				.map(RequestPropertiesModel::getValue).findFirst();
	}

	/**
	 * Retrieves the authorization token from a URI model.
	 *
	 * @param uriModel the {@code UriModel} to search
	 * @return an {@code Optional} containing the authorization token, or empty if
	 *         not found
	 */
	public static Optional<String> authorization(UriModel uriModel) {
		Optional<String> authorization = UrlUtils.uriProperty(uriModel, Ctes.UriProperties.AUTHORIZATION);

		return authorization.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data, ""));
	}

	public static Predicate<String> matchesPath(HttpServletRequest request) {
		final String requestPath = getRequestPath(request);

		return pattern -> {
			try {
				String normalizedPattern = pattern.startsWith("/") ? pattern : "/" + pattern;
				PathPattern pathPattern = config.getPathPatternParser().parse(normalizedPattern);
				PathContainer pathContainer = PathContainer.parsePath(requestPath);

				if (pathPattern.matches(pathContainer)) {
					return true;
				}

				// Try original pattern only if different
				return !pattern.equals(normalizedPattern)
						&& config.getPathPatternParser().parse(pattern).matches(pathContainer);
			} catch (Exception e) {
				return false;
			}
		};
	}

	/**
	 * Extracts the request path, removing the context path and ensuring a leading
	 * '/'.
	 */
	private static String getRequestPath(HttpServletRequest request) {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();

		if (contextPath != null && !contextPath.isEmpty() && path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}

		return path.startsWith("/") ? path : "/" + path;
	}

	/**
	 * Maps the response body of an HTTP response to the specified type, ensuring
	 * the response status is valid.
	 *
	 * @param <T>      the type of the object to map to
	 * @param response the HTTP response
	 * @param type     the {@code JavaType} of the object to map to
	 * @return the mapped object
	 * @throws SdcCustomException if the response status is invalid or mapping fails
	 */
	private static <T> T responseToType(Response response, JavaType type) {
		try (InputStream bodyIs = statusResponse(response).body().asInputStream()) {
			return config.getObjectMapper().readValue(bodyIs, type);
		} catch (IOException ex) {
			throw new SdcCustomException(HttpStatus.resolve(response.status()),
					"error mapping response to %s".formatted(type.getTypeName()));
		}
	}

	/**
	 * Validates the response status and returns the response if valid.
	 *
	 * @param response the HTTP response
	 * @return the validated response
	 * @throws SdcCustomException if the response status is invalid
	 */
	private static Response statusResponse(Response response) {
		if (response.status() >= 400) {
			throw new SdcCustomException(HttpStatus.resolve(response.status()),
					STATUS_MESSAGE.formatted(response.status(), response.request().url()));
		}

		return response;
	}

	/**
	 * Retrieves the URI model for a specific component and URI type from the static
	 * repository.
	 *
	 * @param component the {@code ComponentModel} representing the component
	 * @param type      the {@code UriType} of the URI
	 * @return an {@code Optional} containing the {@code UriModel}, or empty if not
	 *         found
	 */
	private static Optional<UriModel> uriModel(ComponentModel component, UriType type) {
		Stream<Entry<String, UriModel>> urisByType$ = StaticRepository.uris().entrySet().stream()
				.filter(entry -> entry.getValue().getType().equals(type));

		return urisByType$.map(Entry::getValue)
				.filter(uri -> component.getUris().stream().anyMatch(u -> u.getId().getUriName().equals(uri.getName())))
				.findFirst();
	}
}
