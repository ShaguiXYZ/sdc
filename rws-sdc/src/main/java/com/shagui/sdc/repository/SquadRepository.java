package com.shagui.sdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.DepartmentModel;
import com.shagui.sdc.model.SquadModel;

public interface SquadRepository extends JpaRepository<SquadModel, Integer> {
	List<SquadModel> findByDepartment(DepartmentModel department);

	Page<SquadModel> findByDepartment(DepartmentModel department, Pageable pageable);

	List<SquadModel> findByDepartment_Company_Id(int companyId);

	Page<SquadModel> findByDepartment_Company_Id(int companyId, Pageable pageable);

	@Query("SELECT COUNT(s) FROM SquadModel s WHERE s.coverage IS NOT NULL")
	Long countWithCoverage();
}
