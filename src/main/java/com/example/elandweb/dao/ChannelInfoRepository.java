package com.example.elandweb.dao;

import com.example.elandweb.model.ChannelInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelInfoRepository  extends JpaRepository<ChannelInfoEntity, String> {

     @Query(value = "SELECT * FROM channel_info LIMIT :limit OFFSET :offset  " , nativeQuery = true)
     Optional<List<ChannelInfoEntity>> findAllPage(@Param("limit") int limit, @Param("offset") int offset);

     @Query(value =
             "SELECT " +
               "*\n" +
             "FROM " +
               "eland_web.channel_info\n" +
             "WHERE " +
               "source_id = :sourceId\n" +
             "ORDER BY " +
               "source_area_id DESC\n" +
             "LIMIT " +
               "1 ; " , nativeQuery = true)
     Optional<ChannelInfoEntity> findLastChannelInfo(@Param("sourceId")String sourceId);
}
