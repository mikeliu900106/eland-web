package com.example.elandweb.controller;

import com.example.elandweb.category.TagCategory;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagInfoController {
    private final TagInfoService tagInfoService;
    @GetMapping(value = "tagInfos")
    public ResponseEntity<ResponseDto> findAll(
            @RequestParam(defaultValue = "1" ,required = false  ) int page,
            @RequestParam(defaultValue = "10",required = false ) int size
    ){
        return ResponseEntity.ok(tagInfoService.findAll(page,size));
    }
    @GetMapping(value = "/tagInfo/{tagId}")
    public ResponseEntity<ResponseDto> findTagInfo(
            @PathVariable int tagId
    ){
        return ResponseEntity.ok(tagInfoService.findTagInfo(tagId));
    }
    @PostMapping("/tagInfo")
    public ResponseEntity<ResponseDto> createTagInfo(
            @RequestBody TagCategory tagCategory
    ){
        return ResponseEntity.ok(tagInfoService.createTagInfo(tagCategory));
    }
    @PostMapping("/tagInfos")
    public ResponseEntity<ResponseDto> createTagInfos(
            @RequestBody List<TagCategory> tagCategories
    ){
        return ResponseEntity.ok(tagInfoService.createTagInfos(tagCategories));
    }
    @PutMapping("/tagInfo/{tagId}")
    public ResponseEntity<ResponseDto> updateTagInfo(
            @PathVariable int tagId,
            @RequestBody TagCategory tagCategory
    ){
        return ResponseEntity.ok(tagInfoService.updateTagInfo(tagId,tagCategory));
    }
    @DeleteMapping("/tagInfo/{tagId}")
    public ResponseEntity<ResponseDto> deleteTagInfo(
            @PathVariable int tagId
    ){
        return ResponseEntity.ok(tagInfoService.deleteTagInfo(tagId));
    }
//
}
