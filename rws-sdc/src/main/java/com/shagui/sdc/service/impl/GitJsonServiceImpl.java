package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.service.GitService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.documents.JsonDocument;
import com.shagui.sdc.util.documents.SdcDocument;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.GIT_JSON)
public class GitJsonServiceImpl extends GitService {
	@Override
	protected Class<? extends SdcDocument> documentOf() {
		return JsonDocument.class;
	}
}
