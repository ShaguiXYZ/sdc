package com.shagui.sdc.service.impl;

import org.springframework.stereotype.Service;

import com.shagui.sdc.service.GitService;
import com.shagui.sdc.util.Ctes;
import com.shagui.sdc.util.documents.SdcDocument;
import com.shagui.sdc.util.documents.XmlDocument;

@Service(Ctes.ANALYSIS_SERVICES_TYPES.GIT_XML)
public class GitXmlServiceImpl extends GitService {
	@Override
	protected Class<? extends SdcDocument> documentOf() {
		return XmlDocument.class;
	}
}
