package com.example.elandweb.dto;

import com.example.elandweb.model.TagInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class PageTagInfoDto extends PageDto {
    private List<TagInfoEntity> tagInfoEntityList;
}
