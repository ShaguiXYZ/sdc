package com.shagui.sdc.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.ComponentAnalysisModel;
import com.shagui.sdc.model.pk.ComponentAnalysisPk;

public interface ComponentAnalysisRepository extends JpaRepository<ComponentAnalysisModel, ComponentAnalysisPk> {
	public static final String MAX_DATE = """
            SELECT MAX(ca2.id.analysisDate)\
             FROM ComponentAnalysisModel ca2\
             WHERE ca2.id.componentId = ca.id.componentId AND ca2.id.metricId = ca.id.metricId\
            """;

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             WHERE ca.id.componentId = :componentId AND ca.id.metricId = :metricId\
             AND ca.id.analysisDate = (\
            """ + MAX_DATE + ")")
	Optional<ComponentAnalysisModel> actualMetric(int componentId, int metricId);

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             WHERE ca.id.componentId = :componentId AND ca.id.metricId = :metricId\
             AND ca.id.analysisDate = (\
            """ + MAX_DATE + " AND ca2.id.analysisDate <= :date)")
	Optional<ComponentAnalysisModel> historicMetric(int componentId, int metricId, Timestamp date);

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             WHERE ca.id.componentId = :componentId AND ca.id.metricId = :metricId AND ca.id.analysisDate <= :date\
             ORDER BY ca.id.analysisDate ASC\
            """)
	List<ComponentAnalysisModel> metricHistory(int componentId, int metricId, Timestamp date);


	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             WHERE ca.id.componentId = :componentId AND ca.id.metricId = :metricId AND ca.id.analysisDate <= :date\
             ORDER BY ca.id.analysisDate ASC\
            """)
	Page<ComponentAnalysisModel> metricHistory(int componentId, int metricId, Timestamp date,
			Pageable pageable);

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             WHERE ca.id.componentId = :componentId\
             AND ca.id.analysisDate = (\
            """ + MAX_DATE + " AND ca2.id.analysisDate <= :date)"
			+ " ORDER BY ca.id.analysisDate ASC")
	List<ComponentAnalysisModel> componentAnalysis(int componentId, Timestamp date);

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             INNER JOIN ca.component c\
             INNER JOIN c.squad s ON s.id = :squadId\
             WHERE ca.id.analysisDate = (\
            """ + MAX_DATE + " AND ca2.id.analysisDate <= :date)"
			+ " ORDER BY ca.id.analysisDate ASC")
	List<ComponentAnalysisModel> squadAnalysis(int squadId, Timestamp date);

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca INNER JOIN ca.component c\
             INNER JOIN c.squad s\
             INNER JOIN s.department d ON (d.id = :departmentId)\
             WHERE ca.id.analysisDate = (\
            """ + MAX_DATE + " AND ca2.id.analysisDate <= :date)"
			+ " ORDER BY ca.id.analysisDate ASC")
	List<ComponentAnalysisModel> departmentAnalysis(int departmentId, Timestamp date);

	@Query("""
            SELECT ca FROM ComponentAnalysisModel ca\
             INNER JOIN ca.metric m ON (m.id = :metricId)\
             INNER JOIN ca.component c ON (:componentId IS NULL OR c.id = :componentId)\
             INNER JOIN c.squad s ON (:squadId IS NULL OR s.id = :squadId)\
             INNER JOIN s.department d ON (:departmentId IS NULL OR d.id = :departmentId)\
             WHERE ca.id.analysisDate = (\
            """ + MAX_DATE + " AND ca2.id.analysisDate <= :date)")
	List<ComponentAnalysisModel> filterAnalysis(Integer metricId, Integer componentId,
			Integer squadId, Integer departmentId, Timestamp date);
}
