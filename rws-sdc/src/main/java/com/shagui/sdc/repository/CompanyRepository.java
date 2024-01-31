package com.shagui.sdc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.CompanyModel;

public interface CompanyRepository extends JpaRepository<CompanyModel, Integer> {
    Optional<CompanyModel> findByNameIgnoreCase(String name);
}
