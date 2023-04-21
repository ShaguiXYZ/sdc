package com.shagui.sdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.DepartmentModel;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {
}
