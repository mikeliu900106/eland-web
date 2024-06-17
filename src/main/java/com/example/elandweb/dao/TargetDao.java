package com.example.elandweb.dao;

import com.example.elandweb.dto.TargetDto;
import com.example.elandweb.util.DataBaseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Getter
@Setter

@AllArgsConstructor
public class TargetDao {
    private final  String sql = "SELECT " +
            "    COALESCE(ti.tag_name, '內容標籤'), " +
            "    SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音' " +
            "FROM " +
            "    channel_info ci " +
            "    JOIN channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id " +
            "    JOIN tag_info ti ON ctm.tag_id = ti.tag_id " +
            "WHERE ti.type = 1 " +
            "AND (? IS NULL OR ci.p_type_2 = ?) " +
            "AND (? IS NULL OR ti.tag_name = ?) " +
            "GROUP BY " +
            "    ti.tag_name WITH ROLLUP " +
            "UNION " +
            "SELECT " +
            "    COALESCE(ti.tag_name, '屬性標籤'), " +
            "    SUM(CASE WHEN ci.p_type_2 = 'news' THEN 1 ELSE 0 END) AS '新聞', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'blog' THEN 1 ELSE 0 END) AS '部落格', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'forum' THEN 1 ELSE 0 END) AS '討論區', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'social' THEN 1 ELSE 0 END) AS '社群網站', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'comment' THEN 1 ELSE 0 END) AS '評論', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'qa' THEN 1 ELSE 0 END) AS '問答網站', " +
            "    SUM(CASE WHEN ci.p_type_2 = 'video' THEN 1 ELSE 0 END) AS '影音' " +
            "FROM " +
            "    channel_info ci " +
            "    JOIN channel_tag_mapping ctm ON ci.source_area_id = ctm.s_area_id " +
            "    JOIN tag_info ti ON ctm.tag_id = ti.tag_id " +
            "WHERE ti.type = 2 " +
            "AND (? IS NULL OR ci.p_type_2 = ?) " +
            "AND (? IS NULL OR ti.tag_name = ?) " +
            "GROUP BY " +
            "    ti.tag_name WITH ROLLUP ";

    public List<TargetDto> findTarget(String pType2, String tagName) throws SQLException {
        List<TargetDto> targetsDto = new ArrayList<>();
        try(Connection conn = DataBaseUtil.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
           stmt.setString(1, pType2);
           stmt.setString(2, pType2);
           stmt.setString(3, tagName);
           stmt.setString(4, tagName);
           stmt.setString(5, pType2);
           stmt.setString(6, pType2);
           stmt.setString(7, tagName);
           stmt.setString(8, tagName);
            try (ResultSet rs = stmt.executeQuery()) {
                // Step 5: Processing the ResultSet
                while (rs.next()) {
                    TargetDto dto = new TargetDto(
                            rs.getString("tagName"),
                            rs.getInt("newsCount"),
                            rs.getInt("blogCount"),
                            rs.getInt("forumCount"),
                            rs.getInt("socialCount"),
                            rs.getInt("commentCount"),
                            rs.getInt("qaCount"),
                            rs.getInt("videoCount")
                    );
                    System.out.printf("Tag Name: %s, 新聞: %d, 部落格: %d, 討論區: %d, 社群網站: %d, 評論: %d, 問答網站: %d, 影音: %d%n");
                    targetsDto.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return targetsDto;
    }
}
