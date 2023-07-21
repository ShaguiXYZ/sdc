package com.shagui.sdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentUriModel;
import com.shagui.sdc.model.pk.ComponentUriPk;

public interface ComponentUriRepository extends JpaRepository<ComponentUriModel, ComponentUriPk> {
}
