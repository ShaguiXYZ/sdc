package com.shagui.sdc.api.dto;

import com.shagui.sdc.api.domain.PageData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoricalCoverageDTO<T> {
	private T data;
	private PageData<TimeCoverageDTO> historical;
}
