package com.shagui.sdc.test.utils;

import java.util.ArrayList;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;

public class DataUtils {
	public static final int DEFAULT_DEPARTMENT_ID = 1;
	public static final int DEFAULT_SQUAD_ID = 1;
	public static final int DEFAULT_ARCHITECTURE_ID = 1;
	public static final int DEFAULT_METRIC_ID = 1;
	public static final int DEFAULT_COMPONENT_ID = 100;
	public static final int DEFAULT_COMPONENT_TYPE_ID = 100;

	private DataUtils() {
	}

	public static <T> PageData<T> createEmptyPageData() {
		PageData<T> data = new PageData<>();
		data.setPage(new ArrayList<>());
		data.setPaging(new PageInfo());

		return data;
	}

	public static <T> HistoricalCoverage<T> createEmptyHistoricalCoverage(T source) {
		HistoricalCoverage<T> data = new HistoricalCoverage<>();
		data.setData(source);
		data.setHistorical(createEmptyPageData());

		return data;
	}
}
