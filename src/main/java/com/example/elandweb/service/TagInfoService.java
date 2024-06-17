package com.example.elandweb.service;


import com.example.elandweb.category.TagCategory;
import com.example.elandweb.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TagInfoService extends BasicService {
        ResponseDto findAll(int page, int size);
        ResponseDto createTagInfos(List<TagCategory> tagCategories);
        ResponseDto createTagInfo(TagCategory tagCategory);
        ResponseDto updateTagInfo(int tagId, TagCategory tagCategory);
        ResponseDto deleteTagInfo(int tagId);
        ResponseDto findTagInfo(int tagId);

//        createTagInfos(TagInfoDto tagInfoDto);
}
