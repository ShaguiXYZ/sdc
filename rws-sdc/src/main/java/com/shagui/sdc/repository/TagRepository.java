package com.shagui.sdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.TagModel;

public interface TagRepository extends JpaRepository<TagModel, Integer> {

}
