package com.shagui.sdc.util.git.lib;

import java.util.HashMap;
import java.util.Map;
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

		return data.isEmpty() ? Optional.empty()
				: Optional.of(data.entrySet().stream()
						.map(entry -> entry.getKey() + "=" + entry.getValue())
						.collect(Collectors.joining(";")));
	};

	public static final Function<ServiceDataDTO, Optional<String>> lines = serviceData -> {
		Map<String, Integer> data = languages(serviceData);

		if (data.isEmpty()) {
			return Optional.empty();
		}

		int totalLines = data.values().stream().mapToInt(Integer::intValue).sum();

		return Optional.of(Integer.toString(totalLines));
	};

	@SuppressWarnings("unchecked")
	private static Map<String, Integer> languages(ServiceDataDTO serviceData) throws SdcCustomException {
		return GitUtils
				.retrieveGitData(serviceData.getComponent(), GitUtils.GitOperations.LANGUAGES, HashMap.class)
				.orElseGet(HashMap::new);
	}
}
