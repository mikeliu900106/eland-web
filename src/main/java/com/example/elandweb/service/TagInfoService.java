package com.example.elandweb.service;


import com.example.elandweb.category.TagCategory;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.dto.TagInfoDto;
import com.example.elandweb.model.TagInfoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagInfoService extends BasicService {
        ResponseDto findTagInfos(int page, int size);

        ResponseDto createTagInfos(TagCategory tagCategory);
//        createTagInfos(TagInfoDto tagInfoDto);
}
