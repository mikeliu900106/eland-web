package com.example.elandweb.dao;

import com.example.elandweb.dto.TargetDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TargetRowMapper<T> implements RowMapper<TargetDto> {
    @Override
    public TargetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TargetDto.builder()
                .tagName(rs.getString("標籤"))
                .newsCount(rs.getInt("新聞"))
                .blogCount(rs.getInt("部落格"))
                .forumCount(rs.getInt("討論區"))
                .socialCount(rs.getInt("社群網站"))
                .commentCount(rs.getInt("評論"))
                .qaCount(rs.getInt("問答網站"))
                .videoCount(rs.getInt("影音"))
                .type(rs.getShort("類型"))
                .build();
    }
}
