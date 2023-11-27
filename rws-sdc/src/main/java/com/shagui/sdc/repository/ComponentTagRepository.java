package com.shagui.sdc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shagui.sdc.model.ComponentModel;
import com.shagui.sdc.model.ComponentTagModel;
import com.shagui.sdc.model.pk.ComponentTagPk;

public interface ComponentTagRepository extends JpaRepository<ComponentTagModel, ComponentTagPk> {
    public int countByTagId(int tagId);

    public int countByComponentId(int componentId);

    public List<ComponentModel> findByTagIdIn(int[] tagIds);

    public Page<ComponentModel> findByTagIdIn(int[] tagIds, Pageable pageable);

    public long deleteByComponent_IdAndAnalysisTag(int componentId, boolean analysisTag);
}
