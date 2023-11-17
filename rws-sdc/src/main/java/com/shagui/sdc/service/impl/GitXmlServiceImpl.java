package com.shagui.sdc.service.impl;

import java.util.Arrays;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.GitDocumentService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.data.DocumentServiceDataDTO;
import com.shagui.sdc.util.documents.lib.xml.XmlDocument;
import com.shagui.sdc.util.documents.lib.xml.pom.PomLib;

@Service(Ctes.AnalysisServicesTypes.GIT_XML)
public final class GitXmlServiceImpl extends GitDocumentService {
	@Override
	protected Class<? extends SdcDocument> documentOf() {
		return XmlDocument.class;
	}

	@Override
	protected ComponentAnalysisModel executeMetricFn(String fn, ComponentModel component, MetricModel metric,
			SdcDocument docuemnt) {

		if (isServiceFn(fn)) {
			String value = MetricLibrary.Library.valueOf(fn.toUpperCase())
					.apply(new DocumentServiceDataDTO(component, metric, docuemnt));

			return new ComponentAnalysisModel(component, metric, value);
		}

		throw new SdcCustomException(
				"%s function is not available for %s service".formatted(fn, Ctes.AnalysisServicesTypes.GIT_XML));
	}

	private boolean isServiceFn(String fn) {
		return Arrays.stream(MetricLibrary.Library.values())
				.anyMatch(libraryValue -> libraryValue.name().equals(fn.toUpperCase()));
	}

	private static class MetricLibrary {
		enum Library {
			DEPRECATED_LIBRARY(PomLib.hasDeprecatedLibrary);

			private Function<DocumentServiceDataDTO, String> fn;

			private Library(Function<DocumentServiceDataDTO, String> fn) {
				this.fn = fn;
			}

			public String apply(DocumentServiceDataDTO data) {
				return this.fn.apply(data);
			}
		}

		private MetricLibrary() {
		}
	}
}
