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

//     @Query(value = "SELECT\n" +
//             "    COALESCE(ti.tag_name, '內容標籤') ,\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音'\n" +
//             "FROM\n" +
//             "    channel_info ci\n" +
//             "    JOIN channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id\n" +
//             "    JOIN tag_info ti ON ctm.tag_id = ti.tag_id\n" +
//             "WHERE ti.type = 1 " +
//             "AND (:pType2 IS NULL OR ci.p_type_2 = :pType2)\n" +
//             "AND (:tagName IS NULL OR ti.tag_name = :tagName)" +
//             "GROUP BY\n" +
//             "    ti.tag_name WITH ROLLUP\n" +
//             "UNION \n" +
//             "SELECT\n" +
//             "    COALESCE(ti.tag_name, '屬性標籤') ,\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站',\n" +
//             "    SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音'\n" +
//             "FROM\n" +
//             "    channel_info ci\n" +
//             "    JOIN channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id\n" +
//             "    JOIN tag_info ti ON ctm.tag_id = ti.tag_id\n" +
//             "WHERE ti.type = 2 " +
//             "AND (:pType2 IS NULL OR ci.p_type_2 = :pType2)\n" +
//             "AND (:tagName IS NULL OR ti.tag_name = :tagName)" +
//             "GROUP BY\n" +
//             "    ti.tag_name WITH ROLLUP" , nativeQuery = true)
//     List<ChannelInfoSummaryDto> findAllTable(@Param("pType2")String pType2, @Param("tagName")String tagName);

}
