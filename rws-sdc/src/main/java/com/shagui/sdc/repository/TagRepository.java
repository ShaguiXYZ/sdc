package com.shagui.sdc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.TagModel;

public interface TagRepository extends JpaRepository<TagModel, Integer> {
    public Optional<TagModel> findByName(String name);

    public List<TagModel> findByComponents_Id(int componentId);

    public Page<TagModel> findByComponents_Id(int componentId, Pageable pageable);
}
