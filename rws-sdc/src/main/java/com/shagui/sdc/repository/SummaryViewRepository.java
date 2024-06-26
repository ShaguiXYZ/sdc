package com.shagui.sdc.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.enums.SummaryType;
import com.shagui.sdc.model.SummaryViewModel;
import com.shagui.sdc.model.pk.SummaryViewPk;
import com.shagui.sdc.util.jpa.JpaReadOnlyRepository;

public interface SummaryViewRepository extends JpaReadOnlyRepository<SummaryViewModel, SummaryViewPk> {
    @Query("""
            SELECT sm FROM SummaryViewModel sm
            WHERE
            (:name IS NULL OR LOWER(sm.name) LIKE :name) AND
            (:types IS NULL OR sm.id.type IN :types)
            """)
    Page<SummaryViewModel> filter(String name, Set<SummaryType> types, Pageable pageable);

    @Query("""
            SELECT sm FROM SummaryViewModel sm
            WHERE
            (:name IS NULL OR LOWER(sm.name) LIKE :name) AND
            (:types IS NULL OR sm.id.type IN :types)
            """)
    public List<SummaryViewModel> filter(String name, Set<SummaryType> types, Sort sort);

}
