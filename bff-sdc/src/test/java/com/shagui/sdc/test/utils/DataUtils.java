package com.shagui.sdc.test.utils;

import java.util.ArrayList;
import java.util.Date;

import com.shagui.sdc.api.domain.HistoricalCoverage;
import com.shagui.sdc.api.domain.PageData;
import com.shagui.sdc.api.domain.PageInfo;
import com.shagui.sdc.api.dto.ComponentDTO;
import com.shagui.sdc.api.dto.DepartmentDTO;
import com.shagui.sdc.api.dto.MetricAnalysisDTO;
import com.shagui.sdc.api.dto.SquadDTO;

public class DataUtils {
	public static final int DEFAULT_DEPARTMENT_ID = 1;
	public static final int DEFAULT_SQUAD_ID = 1;
	public static final int DEFAULT_COMPONENT_ID = 100;
	
	private DataUtils() {}
	
	public static <T>PageData<T> createEmptyPageData() {
		PageData<T> data =  new PageData<>();
		data.setPage(new ArrayList<>());
		data.setPaging(new PageInfo());
		
		return data;
	}
	
	public static <T>HistoricalCoverage<T> createEmptyHistoricalCoverage(T source) {
		HistoricalCoverage<T> data = new HistoricalCoverage<>();
		data.setData(source);
		data.setHistorical(createEmptyPageData());
		
		return data;
	}
	
	public static DepartmentDTO createDepartmentDTO() {
		DepartmentDTO data = new DepartmentDTO();
		data.setId(DEFAULT_DEPARTMENT_ID);
		data.setName("department name");
		data.setCoverage(50f);
		
		return data;
	}
	
	public static SquadDTO createSquadDTO() {
		SquadDTO data = new SquadDTO();
		data.setId(DEFAULT_SQUAD_ID);
		data.setDepartment(createDepartmentDTO());
		data.setName("squad name");
		data.setCoverage(50f);
		
		return data;
	}
	
	public static ComponentDTO createComponentDTO() {
		ComponentDTO data = new ComponentDTO();
		data.setId(DEFAULT_COMPONENT_ID);
		data.setName("component name");
		data.setSquad(createSquadDTO());
		
		return data;
	}
	
	public static MetricAnalysisDTO createMetricAnalysisDTO() {
		MetricAnalysisDTO data = new MetricAnalysisDTO();
		data.setAnalysisDate(new Date());
		data.setCoverage(50f);
		
		return data;
	}
}
