package com.example.elandweb.service.impl;

import com.example.elandweb.category.TagCategory;
import com.example.elandweb.config.DataNotFoundException;
import com.example.elandweb.dao.TagInfoRepository;
import com.example.elandweb.dto.PageDataDto;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagInfoEntity;
import com.example.elandweb.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagInfoServiceImpl implements TagInfoService {

    private final TagInfoRepository tagInfoRepository;

    @Override
    public ResponseDto findAll(int page, int size) {
        int offset = getSelectOffset(page,size);
        int limit = size;
        List<TagInfoEntity> TagInfoEntities = tagInfoRepository.findAllPage(limit,offset).orElseThrow(() -> new DataNotFoundException("找不到tag"))  ;
        long total = tagInfoRepository.count();
        PageDataDto pageTagInfoDto = PageDataDto.builder()
                .basicEntity(TagInfoEntities)
                .page(page)
                .size(size)
                .total(total)
                .build();
        ResponseDto responseDto = getRestDto(pageTagInfoDto,"查詢成功");
        return responseDto;
    }
    @Override
    public ResponseDto findTagInfo(int tagId) {
        TagInfoEntity tagInfoEntity = tagInfoRepository.findById(tagId).orElseThrow(() -> new DataNotFoundException("找不到tag"));
        ResponseDto responseDto = getRestDto(tagInfoEntity,"查詢成功");
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
        return getRestDto(tagInfosEntity,"新增成功");
    }
    @Override
    public ResponseDto createTagInfo(TagCategory tagCategory) {
        TagInfoEntity tagInfoEntity = TagInfoEntity
                .builder()
                .tagName(tagCategory.getTagName())
                .type(tagCategory.getType())
                .build();
        TagInfoEntity insertTagInfoEntity = tagInfoRepository.save(tagInfoEntity);
        ResponseDto responseDto = getRestDto(insertTagInfoEntity,"新增成功");
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
        ResponseDto responseDto = getRestDto(insertTagInfoEntity,"更新成功");
        return responseDto;
    }

    @Override
    public ResponseDto deleteTagInfo(int tagId) {
        TagInfoEntity TagInfoEntity = tagInfoRepository.findById(tagId).orElseThrow(()-> new DataNotFoundException("沒有該筆資料"));
        tagInfoRepository.deleteById(tagId);
        ResponseDto responseDto = getRestDto(TagInfoEntity,"更新成功");
        return responseDto;
    }
}
