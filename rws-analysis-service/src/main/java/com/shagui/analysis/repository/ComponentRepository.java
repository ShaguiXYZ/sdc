package com.shagui.analysis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.ComponentModel;
import com.shagui.analysis.model.SquadModel;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {
	Page<ComponentModel> findBySquad(SquadModel squad, Pageable pageable);
}
