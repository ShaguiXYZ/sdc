package com.shagui.sdc.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;

public interface ComponentAnalysisRepository extends JpaRepository<ComponentAnalysisModel, ComponentAnalysisPk> {

	@Query("SELECT an FROM ComponentAnalysisModel an WHERE an.id.componentId = :componentId AND an.id.metricId = :metricId "
			+ "AND an.id.componentAnalysisDate = (SELECT MAX(an1.id.componentAnalysisDate) FROM ComponentAnalysisModel an1 "
			+ "WHERE an1.id.componentId = :componentId AND an1.id.metricId  = :metricId)")
	Optional<ComponentAnalysisModel> actualMetric(int componentId, int metricId);

	@Query("SELECT pa FROM ComponentAnalysisModel pa WHERE pa.id.componentId = :componentId AND pa.id.metricId = :metricId AND pa.id.componentAnalysisDate <= :date")
	List<ComponentAnalysisModel> metricHistory(int componentId, int metricId, Timestamp date);

	@Query("SELECT ca FROM ComponentAnalysisModel ca "
			+ "WHERE ca.id.componentId = :componentId "
			+ "AND ca.id.componentAnalysisDate = ("
			+ "	SELECT MAX(ca2.id.componentAnalysisDate) "
			+ "	FROM ComponentAnalysisModel ca2 "
			+ "	WHERE ca2.id.componentId = ca.id.componentId AND ca2.id.metricId = ca.id.metricId"
			+ "	AND ca2.id.componentAnalysisDate <= :date"
			+ "	)")
	List<ComponentAnalysisModel> componentAnalysis(int componentId, Timestamp date);

	@Query("SELECT ca FROM ComponentAnalysisModel ca "
			+ "INNER JOIN ca.component c ON c.squad.id = :squadId "
			+ "WHERE ca.id.componentAnalysisDate = ("
			+ "	SELECT MAX(ca2.id.componentAnalysisDate) "
			+ "	FROM ComponentAnalysisModel ca2 "
			+ "	WHERE ca2.id.componentId = ca.id.componentId AND ca2.id.metricId = ca.id.metricId"
			+ "	AND ca2.id.componentAnalysisDate <= :date"
			+ "	)")
	List<ComponentAnalysisModel> squadAnalysis(int squadId, Timestamp date);
}
