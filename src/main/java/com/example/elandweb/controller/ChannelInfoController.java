package com.example.elandweb.controller;

import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeCategoryEnum;
import com.example.elandweb.service.ChannelInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChannelInfoController {

    private final ChannelInfoService channelInfoService;
    @GetMapping(value = "channelInfo")
    public ResponseEntity<ResponseDto> findAllTable(
            @RequestParam(defaultValue = "",required = false)TypeCategoryEnum typeCategoryEnum,
            @RequestParam(defaultValue = "",required = false)TagNameEnum tagNameEnum
            ){
        return ResponseEntity.ok(channelInfoService.findAllTable(typeCategoryEnum,tagNameEnum));
    }
}
