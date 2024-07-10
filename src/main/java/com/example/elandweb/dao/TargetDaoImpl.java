package com.example.elandweb.dao;

import com.example.elandweb.dto.TargetDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TargetDaoImpl implements TargetDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TargetDto> findTargetByTagNameAndType(String tagName, String targetType) {
        String sql =
                "SELECT\n" +
                        "    COALESCE(ti.tag_name, '內容標籤') AS '標籤',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論',\n" +
                        "     SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音',\n" +
                        "   ti.type AS '類型'\n" +
                        "FROM\n" +
                        "    channel_info ci\n" +
                        "    JOIN channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id\n" +
                        "    JOIN tag_info ti ON ctm.tag_id = ti.tag_id\n" +
                        "WHERE ti.type = 1 " +
                        "   AND (? IS NULL OR ci.p_type_2 = ?) " +
                        "   AND (? IS NULL OR ti.tag_name = ?) " +
                        "GROUP BY\n" +
                        "    ti.tag_name WITH ROLLUP\n" +
                        "UNION \n" +
                        "SELECT\n" +
                        "    COALESCE(ti.tag_name, '屬性標籤') AS '標籤' ,\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站',\n" +
                        "    SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音',\n" +
                        "   ti.type AS '類型'\n" +
                        "FROM\n" +
                        "    channel_info ci\n" +
                        "    JOIN channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id\n" +
                        "    JOIN tag_info ti ON ctm.tag_id = ti.tag_id\n" +
                        "WHERE ti.type = 2 " +
                        "    AND (? IS NULL OR ci.p_type_2 = ?) " +
                        "    AND (? IS NULL OR ti.tag_name = ?) " +
                        "GROUP BY\n" +
                        "    ti.tag_name WITH ROLLUP";
        List<TargetDto> TargetsDto = jdbcTemplate.query(sql, new Object[]{targetType, targetType, tagName, tagName, targetType, targetType, tagName, tagName}, new TargetRowMapper<TargetDao>() {
        });
        return TargetsDto;
    }

}

