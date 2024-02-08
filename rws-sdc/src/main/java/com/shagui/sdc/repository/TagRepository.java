package com.shagui.sdc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.TagModel;

public interface TagRepository extends JpaRepository<TagModel, Integer> {
        Optional<TagModel> findByName(String name);

        @Query("""
                        SELECT new com.shagui.sdc.model.TagModel(t.id, t.name, ct.analysisTag, ct.owner)
                        FROM TagModel t
                        INNER JOIN ComponentTagModel ct ON ct.id.componentId = :componentId
                                                        AND ct.id.tagId = t.id
                                                        AND (ct.owner LIKE :owner OR ct.owner IS NULL AND ct.analysisTag = true)
                        WHERE t.name = :name
                        """)
        Optional<TagModel> findByOwnedTag(String name, String owner, int componentId);

        @Query("""
                        SELECT new com.shagui.sdc.model.TagModel(t.id, t.name, ct.analysisTag, ct.owner)
                        FROM TagModel t
                        INNER JOIN ComponentTagModel ct ON (
                        (ct.owner IS NULL AND ct.analysisTag = true) OR ct.owner LIKE :owner
                        )
                        WHERE ct.id.componentId = :componentId AND ct.id.tagId = t.id
                        """)
        List<TagModel> findByComponent(int componentId, String owner);

        @Query("""
                        SELECT new com.shagui.sdc.model.TagModel(t.id, t.name, ct.analysisTag, ct.owner)
                        FROM TagModel t
                        INNER JOIN ComponentTagModel ct ON (
                        (ct.owner IS NULL AND ct.analysisTag = true) OR ct.owner LIKE :owner
                        )
                        WHERE ct.id.componentId = :componentId AND ct.id.tagId = t.id
                        """)
        Page<TagModel> findByComponent(int componentId, String owner, Pageable pageable);
}
