package com.example.elandweb.service;

import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeCategoryEnum;
import org.springframework.stereotype.Service;

@Service
public interface ChannelInfoService {
    ResponseDto findAllTable(TypeCategoryEnum typeCategoryEnum, TagNameEnum tagNameEnum);
}
