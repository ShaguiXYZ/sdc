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
import com.shagui.sdc.util.ComponentUtils;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.DictioraryReplacement;
import com.shagui.sdc.util.UrlUtils;

import feign.Response;

public class GitUtils {

	public enum GitOperations {
		DEPENDABOT_ALERTS("dependabot/alerts", "state=open&per_page=100"), LANGUAGES("languages");

		private String path;
		private String params = null;

		private GitOperations(String path) {
			this.path = path;
		}

		private GitOperations(String path, String params) {
			this.path = path;
			this.params = params;
		}

		public String path() {
			return path;
		}

		public Optional<String> params() {
			return Optional.ofNullable(params);
		}
	}

	private static GitUtilsConfig config;

	private GitUtils() {
	}

	protected static void setConfig(GitUtilsConfig config) {
		GitUtils.config = config;
	}

	public static <T> Optional<T> retrieveGitData(ComponentModel component, GitOperations operation, Class<T> clazz) {
		return retrieveGitData(component, operation.path(), operation.params(), clazz);
	}

	public static <T> Optional<T> retrieveGitData(ComponentModel component, GitOperations operation,
			Map<String, String> paramValues, Class<T> clazz) {
		return retrieveGitData(component, operation.path(), operation.params(), paramValues, clazz);
	}

	public static <T> Optional<T> retrieveGitData(ComponentModel component, String operation, Optional<String> params,
			Class<T> clazz) {
		return retrieveGitData(component, operation, params, Collections.emptyMap(), clazz);
	}

	private static <T> Optional<T> retrieveGitData(ComponentModel component, String operation, Optional<String> params,
			Map<String, String> paramValues, Class<T> clazz) {
		Optional<UriModel> uriModel = UrlUtils.componentUri(component, UriType.GIT);

		if (uriModel.isPresent()) {
			String uri = Arrays.asList(uriModel.get().getApi(), operation).stream().filter(StringUtils::hasText)
					.collect(Collectors.joining("/"));

			String uriWithParams = params.map(addParams(uri, paramValues)).orElse(uri);

			try (Response response = authorization(uriModel.get()).map(
					authorizationHeader -> config.gitClient().repoFile(URI.create(uriWithParams), authorizationHeader))
					.orElseGet(() -> config.gitClient().repoFile(URI.create(uriWithParams)))) {
				return Optional.ofNullable(UrlUtils.mapResponse(response, clazz));
			} catch (Exception e) {
				throw new SdcCustomException("Not git uri for. component '%s'".formatted(component.getName()), e);
			}
		} else {
			throw new SdcCustomException("Not git uri for. component '%s'".formatted(component.getName()));
		}
	}

	private static Optional<String> authorization(UriModel uriModel) {
		Optional<String> authorization = UrlUtils.uriProperty(uriModel, Ctes.UriProperties.AUTHORIZATION);

		return authorization.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data, ""));
	}

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
