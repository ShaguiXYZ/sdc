package com.shagui.sdc.util.git.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.util.git.GitUtils;

public class GitLib {

	private GitLib() {
	}

	// Metric library fn's
	public static final Function<ServiceDataDTO, Optional<String>> nclocLanguageDistribution = serviceData -> {
		Map<String, Integer> data = languages(serviceData);

		return Optional
				.of(data.entrySet().stream().map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue()))
						.collect(Collectors.joining(";")));
	};

	public static final Function<ServiceDataDTO, Optional<String>> lines = serviceData -> {
		Map<String, Integer> data = languages(serviceData);

		return Optional.of(data.entrySet().stream().map(Entry::getValue).reduce(0, (a, b) -> a + b).toString());
	};

	@SuppressWarnings("unchecked")
	private static Map<String, Integer> languages(ServiceDataDTO serviceData) throws SdcCustomException {
		return GitUtils
				.retrieveGitData(serviceData.getComponent(), GitUtils.GitOperations.LANGUAGES, HashMap.class)
				.orElseGet(HashMap::new);
	}
}
