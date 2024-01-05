package com.shagui.sdc.api.dto;

import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.MetricModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceDataDTO {
	private String workflowId;
	private ComponentModel component;
	private MetricModel metric;
}
