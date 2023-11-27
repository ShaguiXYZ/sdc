package com.shagui.sdc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shagui.sdc.model.TagModel;

public interface TagRepository extends JpaRepository<TagModel, Integer> {
        public Optional<TagModel> findByName(String name);

        @Query("""
                        SELECT new TagModel(t.id, t.name, ct.analysisTag) FROM TagModel t \
                        INNER JOIN t.components c \
                        INNER JOIN ComponentTagModel ct ON ct.id.componentId = c.id AND ct.id.tagId = t.id
                        """)
        public List<TagModel> findAll();

        @Query("""
                        SELECT new TagModel(t.id, t.name, ct.analysisTag) FROM TagModel t \
                        INNER JOIN t.components c \
                        INNER JOIN ComponentTagModel ct ON ct.id.componentId = c.id AND ct.id.tagId = t.id
                        """)
        public Page<TagModel> findAll(Pageable pageable);

        @Query("""
                        SELECT new TagModel(t.id, t.name, ct.analysisTag) FROM TagModel t \
                        INNER JOIN t.components c ON c.id = :componentId \
                        INNER JOIN ComponentTagModel ct ON ct.id.componentId = c.id AND ct.id.tagId = t.id
                        """)
        public List<TagModel> findByComponent(int componentId);

        @Query("""
                        SELECT new TagModel(t.id, t.name, ct.analysisTag) FROM TagModel t \
                        INNER JOIN t.components c ON c.id = :componentId \
                        INNER JOIN ComponentTagModel ct ON ct.id.componentId = c.id AND ct.id.tagId = t.id
                        """)
        public Page<TagModel> findByComponent(int componentId, Pageable pageable);
}
