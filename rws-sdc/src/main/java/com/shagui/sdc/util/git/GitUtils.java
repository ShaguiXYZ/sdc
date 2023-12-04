package com.shagui.sdc.util.git;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

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
		DEPENDABOT_ALERTS("dependabot/alerts", "state=open"), LANGUAGES("languages");

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

	public static <T> Optional<T> retrieveGitData(ComponentModel component, String operation, Optional<String> params,
			Class<T> clazz) {
		Optional<UriModel> uriModel = UrlUtils.componentUri(component, UriType.GIT);

		if (uriModel.isPresent()) {
			String uri = Arrays.asList(uriModel.get().getValue(), operation).stream().filter(StringUtils::hasText)
					.collect(Collectors.joining("/"));

			String uriWithParams = params.map(addParams(uri)).orElse(uri);

			Response response = authorization(uriModel.get()).map(
					authorizationHeader -> config.gitClient().repoFile(URI.create(uriWithParams), authorizationHeader))
					.orElseGet(() -> config.gitClient().repoFile(URI.create(uriWithParams)));

			return Optional.ofNullable(UrlUtils.mapResponse(response, clazz));
		}

		return Optional.empty();
	}

	private static Optional<String> authorization(UriModel uriModel) {
		Optional<String> authorization = UrlUtils.uriProperty(uriModel, Ctes.UriProperties.AUTHORIZATION);

		return authorization.map(data -> DictioraryReplacement.getInstance(ComponentUtils.tokens()).replace(data, ""));
	}

	private static Function<String, String> addParams(String uri) {
		return params -> Arrays.asList(uri, params).stream().filter(StringUtils::hasText)
				.collect(Collectors.joining("?"));
	}
}
