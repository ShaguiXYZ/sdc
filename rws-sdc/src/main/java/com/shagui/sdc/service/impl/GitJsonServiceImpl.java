package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.core.exception.SdcCustomException;
import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.service.GitDocumentService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.lib.json.JsonDocument;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.GIT_JSON)
public final class GitJsonServiceImpl extends GitDocumentService {
	@Override
	protected Class<? extends SdcDocument> documentOf() {
		return JsonDocument.class;
	}

	@Override
	protected ComponentAnalysisModel executeMetricFn(String fn, ComponentModel component, MetricModel metric,
			SdcDocument docuemnt) {
		throw new SdcCustomException("%s function is not available for %s service".formatted(fn,
                Ctes.ANALYSIS_SERVICES_TYPES.GIT_JSON));
	}
}
