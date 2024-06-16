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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TagInfoServiceImpl implements TagInfoService {

    private final TagInfoRepository tagInfoRepository;

    @Override
    public ResponseDto findAll(int page, int size) {
        int offset = getSelectOffset(page,size);
        int limit = getSelectLimit(page, size);
        List<TagInfoEntity> TagInfoEntities = tagInfoRepository.findTagInfo(limit,offset).orElseThrow(() -> new DataNotFoundException("找不到tag"))  ;
        int total = TagInfoEntities.size();
        PageTagInfoDto pageTagInfoDto = PageTagInfoDto.builder()
                .tagInfoEntityList(TagInfoEntities)
                .page(page)
                .size(size)
                .total(total)
                .build();
        ResponseDto responseDto = getRestDto(pageTagInfoDto,"查詢成功",getLocalDateTimeNow());
        return responseDto;
    }
    @Override
    public ResponseDto findTagInfo(int tagId) {
        TagInfoEntity tagInfoEntity = tagInfoRepository.findById(tagId).orElseThrow(() -> new DataNotFoundException("找不到tag"));
        ResponseDto responseDto = getRestDto(tagInfoEntity,"查詢成功",getLocalDateTimeNow());
        return responseDto;
    }
    @Override
    public ResponseDto createTagInfos(List<TagCategory> tagCategories) {
        List<TagInfoEntity> tagInfosEntity  = new ArrayList<>();
        tagCategories.stream().forEach((tagCategory)->{
            TagInfoEntity tagInfoEntity = TagInfoEntity
                    .builder()
                    .tagName(tagCategory.getTagName())
                    .type(tagCategory.getType())
                    .build();
            tagInfosEntity.add(tagInfoEntity);
        });
        tagInfoRepository.saveAll(tagInfosEntity);
        return getRestDto(tagInfosEntity,"新增成功",getLocalDateTimeNow());
    }
    @Override
    public ResponseDto createTagInfo(TagCategory tagCategory) {
        TagInfoEntity tagInfoEntity = TagInfoEntity
                .builder()
                .tagName(tagCategory.getTagName())
                .type(tagCategory.getType())
                .build();
        TagInfoEntity insertTagInfoEntity = tagInfoRepository.save(tagInfoEntity);
        ResponseDto responseDto = getRestDto(insertTagInfoEntity,"新增成功",getLocalDateTimeNow());
        return responseDto;
    }
    @Override
    public ResponseDto updateTagInfo(int tagId, TagCategory tagCategory) {
        TagInfoEntity tagInfoEntity = TagInfoEntity
                .builder()
                .tagName(tagCategory.getTagName())
                .type(tagCategory.getType())
                .build();
        TagInfoEntity insertTagInfoEntity = tagInfoRepository.save(tagInfoEntity);
        ResponseDto responseDto = getRestDto(insertTagInfoEntity,"更新成功",getLocalDateTimeNow());
        return responseDto;
    }

    @Override
    public ResponseDto deleteTagInfo(int tagId) {
        TagInfoEntity TagInfoEntity = tagInfoRepository.findById(tagId).orElseThrow(()-> new DataNotFoundException("沒有該筆資料"));
        tagInfoRepository.deleteById(tagId);
        ResponseDto responseDto = getRestDto(tagId,"更新成功",getLocalDateTimeNow());
        return responseDto;
    }
}
