package com.shagui.sdc.util.documents.data;

import com.shagui.sdc.api.dto.ServiceDataDTO;
import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.documents.SdcDocument;

import lombok.Getter;

@Getter
public class DocumentServiceDataDTO extends ServiceDataDTO {
	private SdcDocument docuemnt;

	public DocumentServiceDataDTO(String workflowId, ComponentModel component, MetricModel metric,
			SdcDocument docuemnt) {
		super(workflowId, component, metric);
		this.docuemnt = docuemnt;
	}
}
