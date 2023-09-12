package com.shagui.sdc.util.documents.data;

import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;
import com.shagui.sdc.util.documents.SdcDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceData {
	private ComponentModel component;
	private MetricModel metric;
	private SdcDocument docuemnt;
}
