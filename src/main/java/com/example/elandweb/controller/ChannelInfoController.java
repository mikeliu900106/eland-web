package com.example.elandweb.controller;

import com.example.elandweb.category.ChannelInfoCategory;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeEnum;
import com.example.elandweb.service.ChannelInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ChannelInfoController {

    private final ChannelInfoService channelInfoService;
    @GetMapping(value = "/channelInfoAll")
    public ResponseEntity<ResponseDto> findAllTable(
            @RequestParam(defaultValue = "",required = false) Optional<TypeEnum> typeCategoryEnum,
            @RequestParam(defaultValue = "",required = false) Optional<TagNameEnum> tagNameEnum
            ){
        return ResponseEntity.ok(channelInfoService.findAllTable(typeCategoryEnum,tagNameEnum));
    }
    @GetMapping(value = "/channelInfos")
    public ResponseEntity<ResponseDto> findAll(
            @RequestParam(defaultValue = "1" ,required = false  ) int page,
            @RequestParam(defaultValue = "10",required = false ) int size
    ){
        return ResponseEntity.ok(channelInfoService.findAllPage(page,size));
    }
    @GetMapping(value = "/channelInfo/{sourceAreaId}")
    public ResponseEntity<ResponseDto> findChannelInfo(
            @PathVariable String sourceAreaId
    ){
        return ResponseEntity.ok(channelInfoService.findChannelInfo(sourceAreaId));
    }
    @PostMapping(value = "/channelInfo")
    public ResponseEntity<ResponseDto> createChannelInfo(
            @RequestBody ChannelInfoCategory channelInfoCategory
    ){
        return ResponseEntity.ok(channelInfoService.createChannelInfo(channelInfoCategory));
    }
    @PostMapping(value = "/channelInfos")
    public ResponseEntity<ResponseDto> createChannelInfos(
            @RequestBody List<ChannelInfoCategory> channelInfosCategory
    ){
        return ResponseEntity.ok(channelInfoService.createChannelInfos(channelInfosCategory));
    }
    @PutMapping(value = "/channelInfo/{sourceAreaId}")
    public ResponseEntity<ResponseDto> updateChannelInfo(
            @PathVariable String sourceAreaId,
            @RequestBody ChannelInfoCategory channelInfoCategory
    ){
        return ResponseEntity.ok(channelInfoService.updateChannelInfo(sourceAreaId,channelInfoCategory ));
    }
    @DeleteMapping(value = "/channelInfo/{sourceAreaId}")
    public ResponseEntity<ResponseDto> deleteChannelInfo(
            @PathVariable String sourceAreaId
    ){
        return ResponseEntity.ok(channelInfoService.deleteChannelInfo(sourceAreaId ));
    }
}
