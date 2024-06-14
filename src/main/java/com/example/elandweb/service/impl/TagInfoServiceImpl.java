package com.example.elandweb.service.impl;

import com.example.elandweb.category.TagCategory;
import com.example.elandweb.config.DataNotFoundException;
import com.example.elandweb.dao.TagInfoRepository;
import com.example.elandweb.dto.PageTagInfoDto;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagInfoEntity;
import com.example.elandweb.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagInfoServiceImpl implements TagInfoService {

    private final TagInfoRepository tagInfoRepository;

    @Override
    public ResponseDto findTagInfos(int page, int size) {
//        System.out.println( tagInfoRepository.findTagInfo((Pageable) PageRequest.of(page,size)));
        int offset = getSelectOffset(page,size);
        int limit = getSelectLimit(page, size);
        List<TagInfoEntity> TagInfoEntities = tagInfoRepository.findTagInfo(limit,offset).orElseThrow(() -> new DataNotFoundException("找不到tag"))  ;
        int total = getDataCount(TagInfoEntities);
        PageTagInfoDto pageTagInfoDto = PageTagInfoDto.builder()
                .tagInfoEntityList(TagInfoEntities)
                .page(page)
                .size(size)
                .total(total)
                .build();
        ResponseDto responseDto = getRestDto(pageTagInfoDto,"查詢成功");
        return responseDto;
    }

    @Override
    public ResponseDto createTagInfos(TagCategory tagCategory) {
        return null;
    }
}
