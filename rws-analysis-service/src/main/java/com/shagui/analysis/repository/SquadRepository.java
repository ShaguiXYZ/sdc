package com.shagui.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.analysis.model.SquadModel;

public interface SquadRepository extends JpaRepository<SquadModel, Integer> {
}
