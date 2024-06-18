package com.example.elandweb.dao;

import com.example.elandweb.dto.TargetDto;

import java.util.List;

public interface TargetDao {
    public List<TargetDto> findTargetByTagNameAndType(String tagName, String targetType);

}
