package com.example.elandweb.controller;

import com.example.elandweb.category.TagCategory;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.dto.TagInfoDto;
import com.example.elandweb.service.TagInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TagInfoController {
    private final TagInfoService tagInfoService;
    @GetMapping("/tagInfos")
    public ResponseEntity<ResponseDto> getTagInfos(
            @RequestParam(defaultValue = "1" ,required = false  ) int page,
            @RequestParam(defaultValue = "10",required = false ) int size
    ){
        return ResponseEntity.ok(tagInfoService.findTagInfos(page,size));
    }

    @PostMapping("/tagInfos")
    public ResponseEntity<ResponseDto> getTagInfos(
            @RequestBody TagCategory tagCategory
    ){
        return ResponseEntity.ok(tagInfoService.createTagInfos(tagCategory));
    }
//
}
