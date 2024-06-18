package com.example.elandweb.dao;

import com.example.elandweb.model.TagInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagInfoRepository extends JpaRepository<TagInfoEntity, Integer> {
        @Query(value = "SELECT * FROM tag_info LIMIT :limit OFFSET :offset  ", nativeQuery = true)
        Optional<List<TagInfoEntity>> findAllPage(@Param("limit") int limit, @Param("offset") int offset);
}