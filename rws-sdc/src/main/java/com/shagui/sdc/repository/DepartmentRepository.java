package com.shagui.sdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.DepartmentModel;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {
    List<DepartmentModel> findByCompany_Id(int companyId);

    Page<DepartmentModel> findByCompany_Id(int companyId, Pageable pageable);
}
