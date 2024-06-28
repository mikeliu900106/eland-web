package com.example.elandweb.service.impl;

import com.example.elandweb.category.TagCategory;
import com.example.elandweb.config.DataNotFoundException;
import com.example.elandweb.dao.TagInfoRepository;
import com.example.elandweb.dto.PageDataDto;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagInfoEntity;
import com.example.elandweb.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class TagInfoServiceImpl implements TagInfoService {

    private final TagInfoRepository tagInfoRepository;
    private static final Logger logger = Logger.getLogger(TagInfoServiceImpl.class);
    private Lock lock = new ReentrantLock(true);

    @Override
    public ResponseDto findAll(int page, int size) {
        int offset = getSelectOffset(page, size);
        int limit = size;
        FutureTask<List<TagInfoEntity>> futureTask = new FutureTask<>(() -> tagInfoRepository.findAllPage(limit, offset).orElseThrow(() -> new DataNotFoundException("找不到tag")));
        new Thread(futureTask).start();
        long total = tagInfoRepository.count();
        PageDataDto pageTagInfoDto = null;
        try {
            logger.debug("取出來的資料:" + futureTask.get());
            pageTagInfoDto = PageDataDto.builder()
                    .basicEntity(futureTask.get())
                    .page(page)
                    .size(size)
                    .total(total)
                    .build();
        } catch (InterruptedException e) {
            logger.warn("多執行續發繩錯誤");
            throw new RuntimeException("多執行續堵塞");
        } catch (ExecutionException e) {
            throw new RuntimeException("多執行續發繩錯誤");
        }
        return getRestDto(pageTagInfoDto, "查詢成功");
    }

    @Override
    public ResponseDto findTagInfo(int tagId) {
        TagInfoEntity tagInfoEntity = tagInfoRepository.findById(tagId).orElseThrow(() -> new DataNotFoundException("找不到tag"));
        ResponseDto responseDto = getRestDto(tagInfoEntity, "查詢成功");
        return responseDto;
    }

    @Override
    public ResponseDto createTagInfos(List<TagCategory> tagCategories) {
        List<TagInfoEntity> tagInfosEntity = new ArrayList<>();
        tagCategories.stream().forEach((tagCategory) -> {
            TagInfoEntity tagInfoEntity = TagInfoEntity
                    .builder()
                    .tagName(tagCategory.getTagName())
                    .type(tagCategory.getType())
                    .build();
            tagInfosEntity.add(tagInfoEntity);
        });
        tagInfoRepository.saveAll(tagInfosEntity);
        return getRestDto(tagInfosEntity, "新增成功");
    }

    //8s
    @Override
    public ResponseDto createTagInfo(TagCategory tagCategory) {
        TagInfoEntity tagInfoEntity = TagInfoEntity
                .builder()
                .tagName(tagCategory.getTagName())
                .type(tagCategory.getType())
                .build();
        TagInfoEntity insertTagInfoEntity = tagInfoRepository.save(tagInfoEntity);
        ResponseDto responseDto = getRestDto(insertTagInfoEntity, "新增成功");
        return responseDto;
    }

    @Override
    public ResponseDto updateTagInfo(int tagId, TagCategory tagCategory) {
        TagInfoEntity insertTagInfoEntity = null;
        TagInfoEntity tagInfoEntity = TagInfoEntity
                .builder()
                .tagName(tagCategory.getTagName())
                .type(tagCategory.getType())
                .build();
        try {
            lock.lock();
            insertTagInfoEntity = tagInfoRepository.save(tagInfoEntity);
        } finally {
            lock.unlock();
        }
        return getRestDto(insertTagInfoEntity, "更新成功");
    }

    @Override
    public ResponseDto deleteTagInfo(int tagId) {
        TagInfoEntity TagInfoEntity = tagInfoRepository.findById(tagId).orElseThrow(() -> new DataNotFoundException("沒有該筆資料"));
        tagInfoRepository.deleteById(tagId);
        ResponseDto responseDto = getRestDto(TagInfoEntity, "刪除成功");
        return responseDto;
    }
}
