package com.shagui.sdc.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.GitService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.XmlDocument;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.GIT_XML)
public final class GitXmlServiceImpl extends GitService {
	@Override
	protected Class<? extends SdcDocument> documentOf() {
		return XmlDocument.class;
	}

	@Override
	protected ComponentAnalysisModel executeMetricFn(String fn, ComponentModel component, MetricModel metric,
			SdcDocument docuemnt) {

		if (isServiceFn(fn)) {
			String value = MetricLibrary.Library.valueOf(fn.toUpperCase())
					.apply(new ServiceData(component, metric, docuemnt));

			return new ComponentAnalysisModel(component, metric, value);
		}

		throw new SdcCustomException(String.format("%s function is not available for %s service", fn,
				Ctes.ANALYSIS_SERVICES_TYPES.GIT_XML));
	}

	private boolean isServiceFn(String fn) {
		return Arrays.stream(MetricLibrary.Library.values())
				.anyMatch(libraryValue -> libraryValue.name().equals(fn.toUpperCase()));
	}

	private static class MetricLibrary {
		enum Library {
			DEPRECATED_LIBRARY(hasDeprecatedLibrary);

			private Function<ServiceData, String> fn;

			private Library(Function<ServiceData, String> fn) {
				this.fn = fn;
			}

			public String apply(ServiceData data) {
				return this.fn.apply(data);
			}
		}

		private MetricLibrary() {
		}

		// Metric library fn's
		private static Function<ServiceData, String> hasDeprecatedLibrary = serviceData -> {
			List<String> depencencies = ((XmlDocument) serviceData.getDocuemnt())
					.values("dependencies/dependency");

			Boolean result = depencencies.stream().anyMatch(data -> {
				Pattern p = Pattern.compile("(?<=<groupId>)(.*)(?=</groupId>)");
				Matcher m = p.matcher(data);

				Optional<String> group = m.find() ? Optional.of(m.group()) : Optional.empty();
				return group.isEmpty() || !"deprecated lib".equals(group.get());
			});

			return result.toString();
		};
	}
}
