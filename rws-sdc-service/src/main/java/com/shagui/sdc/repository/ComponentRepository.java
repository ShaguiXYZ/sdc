package com.shagui.sdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.SquadModel;

public interface ComponentRepository extends JpaRepository<ComponentModel, Integer> {
	List<ComponentModel> findBySquad(SquadModel squad);

	Page<ComponentModel> findBySquad(SquadModel squad, Pageable pageable);
}
