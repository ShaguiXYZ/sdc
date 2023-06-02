package com.shagui.sdc.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;

public interface ComponentAnalysisRepository extends JpaRepository<ComponentAnalysisModel, ComponentAnalysisPk> {

	@Query("SELECT an FROM ComponentAnalysisModel an WHERE an.id.componentId = :componentId AND an.id.metricId = :metricId"
			+ " AND an.id.analysisDate = (SELECT MAX(an1.id.analysisDate) FROM ComponentAnalysisModel an1"
			+ " WHERE an1.id.componentId = :componentId AND an1.id.metricId  = :metricId)")
	Optional<ComponentAnalysisModel> actualMetric(int componentId, int metricId);

	@Query("SELECT pa FROM ComponentAnalysisModel pa WHERE pa.id.componentId = :componentId AND pa.id.metricId = :metricId AND pa.id.analysisDate <= :date")
	List<ComponentAnalysisModel> metricHistory(int componentId, int metricId, Timestamp date);

	@Query("SELECT ca FROM ComponentAnalysisModel ca WHERE ca.id.componentId = :componentId"
			+ " AND ca.id.analysisDate = (SELECT MAX(ca2.id.analysisDate)"
			+ " FROM ComponentAnalysisModel ca2"
			+ " WHERE ca2.id.componentId = ca.id.componentId AND ca2.id.metricId = ca.id.metricId"
			+ " AND ca2.id.analysisDate <= :date)")
	List<ComponentAnalysisModel> componentAnalysis(int componentId, Timestamp date);

	@Query("SELECT ca FROM ComponentAnalysisModel ca INNER JOIN ca.component c ON c.squad.id = :squadId"
			+ " WHERE ca.id.analysisDate = (SELECT MAX(ca2.id.analysisDate)"
			+ " FROM ComponentAnalysisModel ca2"
			+ " WHERE ca2.id.componentId = ca.id.componentId AND ca2.id.metricId = ca.id.metricId"
			+ " AND ca2.id.analysisDate <= :date)")
	List<ComponentAnalysisModel> squadAnalysis(int squadId, Timestamp date);

	@Query("SELECT ca FROM ComponentAnalysisModel ca INNER JOIN ca.component c"
			+ " INNER JOIN c.squad s ON s.department.id = :departmentId WHERE ca.id.analysisDate = ("
			+ " SELECT MAX(ca2.id.analysisDate) FROM ComponentAnalysisModel ca2"
			+ " WHERE ca2.id.componentId = ca.id.componentId AND ca2.id.metricId = ca.id.metricId"
			+ " AND ca2.id.analysisDate <= :date)")
	List<ComponentAnalysisModel> departmentAnalysis(int departmentId, Timestamp date);
}
