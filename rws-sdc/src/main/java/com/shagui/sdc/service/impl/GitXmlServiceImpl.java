package com.shagui.sdc.service.impl;

import java.util.Arrays;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.GitService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.XmlDocument;
import com.shagui.sdc.util.documents.data.ServiceData;
import com.shagui.sdc.util.documents.lib.xml.pom.PomLib;

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

		throw new SdcCustomException(
				String.format("%s function is not available for %s service", fn, Ctes.ANALYSIS_SERVICES_TYPES.GIT_XML));
	}

	private boolean isServiceFn(String fn) {
		return Arrays.stream(MetricLibrary.Library.values())
				.anyMatch(libraryValue -> libraryValue.name().equals(fn.toUpperCase()));
	}

	private static class MetricLibrary {
		enum Library {
			DEPRECATED_LIBRARY(PomLib.hasDeprecatedLibrary);

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
	}
}
