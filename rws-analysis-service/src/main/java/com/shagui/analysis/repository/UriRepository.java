package com.shagui.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.UriModel;

public interface UriRepository extends JpaRepository<UriModel, Integer> {
}
