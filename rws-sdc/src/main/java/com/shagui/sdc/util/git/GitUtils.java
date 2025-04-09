package com.shagui.sdc.util.git;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.enums.UriType;
import com.shagui.sdc.json.model.UriModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.util.UrlUtils;

import feign.Response;

/**
 * The {@code GitUtils} class provides utility methods for interacting with Git
 * repositories.
 * It supports retrieving data from Git APIs using predefined operations or
 * custom parameters.
 * 
 * <p>
 * This class integrates with {@code UrlUtils} to resolve URIs and handle HTTP
 * responses,
 * and it uses a configurable {@code GitUtilsConfig} for dependency injection of
 * required components.
 */
public class GitUtils {

	/**
	 * The {@code GitOperations} enum defines predefined Git API operations,
	 * including their paths
	 * and optional query parameters.
	 */
	public enum GitOperations {
		DEPENDABOT_ALERTS("dependabot/alerts", "state=open&per_page=100"),
		LANGUAGES("languages");

		private String path;
		private String params = null;

		private GitOperations(String path) {
			this.path = path;
		}

		private GitOperations(String path, String params) {
			this.path = path;
			this.params = params;
		}

		/**
		 * Retrieves the API path for the operation.
		 *
		 * @return the API path as a {@code String}
		 */
		public String path() {
			return path;
		}

		/**
		 * Retrieves the optional query parameters for the operation.
		 *
		 * @return an {@code Optional} containing the query parameters, or empty if none
		 *         exist
		 */
		public Optional<String> params() {
			return Optional.ofNullable(params);
		}
	}

	private static GitUtilsConfig config;

	private GitUtils() {
		// Prevent instantiation
	}

	/**
	 * Sets the configuration for {@code GitUtils}.
	 *
	 * @param config the {@code GitUtilsConfig} object to set
	 */
	protected static void setConfig(GitUtilsConfig config) {
		GitUtils.config = config;
	}

	/**
	 * Retrieves data from a Git API using a predefined operation.
	 *
	 * @param <T>       the type of the data to retrieve
	 * @param component the {@code ComponentModel} representing the component
	 * @param operation the {@code GitOperations} to perform
	 * @param clazz     the class of the data to retrieve
	 * @return an {@code Optional} containing the retrieved data, or empty if not
	 *         found
	 */
	public static <T> Optional<T> retrieveGitData(ComponentModel component, GitOperations operation, Class<T> clazz) {
		return retrieveGitData(component, operation.path(), operation.params(), clazz);
	}

	/**
	 * Retrieves data from a Git API using a predefined operation and custom
	 * parameter values.
	 *
	 * @param <T>         the type of the data to retrieve
	 * @param component   the {@code ComponentModel} representing the component
	 * @param operation   the {@code GitOperations} to perform
	 * @param paramValues a map of custom parameter values
	 * @param clazz       the class of the data to retrieve
	 * @return an {@code Optional} containing the retrieved data, or empty if not
	 *         found
	 */
	public static <T> Optional<T> retrieveGitData(ComponentModel component, GitOperations operation,
			Map<String, String> paramValues, Class<T> clazz) {
		return retrieveGitData(component, operation.path(), operation.params(), paramValues, clazz);
	}

	/**
	 * Retrieves data from a Git API using a custom operation path and optional
	 * query parameters.
	 *
	 * @param <T>       the type of the data to retrieve
	 * @param component the {@code ComponentModel} representing the component
	 * @param operation the custom operation path
	 * @param params    optional query parameters
	 * @param clazz     the class of the data to retrieve
	 * @return an {@code Optional} containing the retrieved data, or empty if not
	 *         found
	 */
	public static <T> Optional<T> retrieveGitData(ComponentModel component, String operation, Optional<String> params,
			Class<T> clazz) {
		return retrieveGitData(component, operation, params, Collections.emptyMap(), clazz);
	}

	/**
	 * Retrieves data from a Git API using a custom operation path, optional query
	 * parameters,
	 * and custom parameter values.
	 *
	 * @param <T>         the type of the data to retrieve
	 * @param component   the {@code ComponentModel} representing the component
	 * @param operation   the custom operation path
	 * @param params      optional query parameters
	 * @param paramValues a map of custom parameter values
	 * @param clazz       the class of the data to retrieve
	 * @return an {@code Optional} containing the retrieved data, or empty if not
	 *         found
	 */
	private static <T> Optional<T> retrieveGitData(ComponentModel component, String operation, Optional<String> params,
			Map<String, String> paramValues, Class<T> clazz) {
		Optional<UriModel> uriModel = UrlUtils.componentUri(component, UriType.GIT);

		return uriModel.map(data -> {
			String uri = Arrays.asList(data.getApi(), operation).stream().filter(StringUtils::hasText)
					.collect(Collectors.joining("/"));

			String uriWithParams = params.map(addParams(uri, paramValues)).orElse(uri);

			try (Response response = UrlUtils.authorization(data).map(
					authorizationHeader -> config.gitClient().repoFile(URI.create(uriWithParams), authorizationHeader))
					.orElseGet(() -> config.gitClient().repoFile(URI.create(uriWithParams)))) {
				return Optional.ofNullable(UrlUtils.mapResponse(response, clazz));
			} catch (Exception e) {
				throw new SdcCustomException(
						"Error calling git uri '%s' for component '%s'".formatted(uri, component.getName()), e);
			}
		}).orElseThrow(() -> new SdcCustomException("Not git uri for component '%s'".formatted(component.getName())));
	}

	/**
	 * Adds query parameters to a URI.
	 *
	 * @param uri         the base URI
	 * @param paramValues a map of parameter values to add
	 * @return a {@code Function} that appends the parameters to the URI
	 */
	private static Function<String, String> addParams(String uri, Map<String, String> paramValues) {
		paramValues = CollectionUtils.isEmpty(paramValues) ? Collections.emptyMap() : paramValues;
		String params = CollectionUtils.isEmpty(paramValues) ? ""
				: paramValues.entrySet().stream()
						.map(entry -> entry.getKey() + "=" + entry.getValue())
						.collect(Collectors.joining("&"));

		return existingParams -> {
			String updatedParams = Stream.of(existingParams, params)
					.filter(StringUtils::hasText)
					.collect(Collectors.joining("&"));

			return StringUtils.hasText(updatedParams) ? uri + "?" + updatedParams : uri;
		};
	}
}
