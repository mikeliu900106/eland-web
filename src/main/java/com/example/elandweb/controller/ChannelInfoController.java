package com.example.elandweb.controller;

import com.example.elandweb.category.ChannelInfoCategory;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.TagNameEnum;
import com.example.elandweb.model.TypeEnum;
import com.example.elandweb.service.ChannelInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Tag(name = "channelInfo api")
public class ChannelInfoController {

    private final ChannelInfoService channelInfoService;

    @GetMapping(value = "/channelInfos")
    public ResponseEntity<ResponseDto> findAll(
            @RequestParam(defaultValue = "1", required = false,name = "頁數") int page,
            @RequestParam(defaultValue = "10", required = false,name ="顯示資料數" ) int size
    ) {
        return ResponseEntity.ok(channelInfoService.findAllPage(page, size));
    }

    @GetMapping(value = "/channelInfo/{sourceAreaId}")
    public ResponseEntity<ResponseDto> findChannelInfo(
            @Parameter(description = "sourceAreaId 唯一值")
            @PathVariable String sourceAreaId
    ) {
        return ResponseEntity.ok(channelInfoService.findChannelInfo(sourceAreaId));
    }

    @PostMapping(value = "/channelInfo")
    public ResponseEntity<ResponseDto> createChannelInfo(
            @RequestBody ChannelInfoCategory channelInfoCategory
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(channelInfoService.createChannelInfo(channelInfoCategory));
    }

    @PostMapping(value = "/channelInfos")
    public ResponseEntity<ResponseDto> createChannelInfos(
            @RequestBody List<ChannelInfoCategory> channelInfosCategory
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(channelInfoService.createChannelInfos(channelInfosCategory));
    }

    @PutMapping(value = "/channelInfo/{sourceAreaId}")
    public ResponseEntity<ResponseDto> updateChannelInfo(
            @PathVariable String sourceAreaId,
            @RequestBody ChannelInfoCategory channelInfoCategory
    ) {

        return ResponseEntity.ok(channelInfoService.updateChannelInfo(sourceAreaId, channelInfoCategory));
    }

    @DeleteMapping(value = "/channelInfo/{sourceAreaId}")
    public ResponseEntity<ResponseDto> deleteChannelInfo(
            @PathVariable String sourceAreaId
    ) {
        return ResponseEntity.ok(channelInfoService.deleteChannelInfo(sourceAreaId));
    }

    @GetMapping(value = "/target")
    public ResponseEntity<?> findAllTable(
            HttpServletResponse response,
            @RequestParam(defaultValue = "", required = false) Optional<TypeEnum> typeCategoryEnum,
            @RequestParam(defaultValue = "", required = false) Optional<TagNameEnum> tagNameEnum,
            @RequestParam(defaultValue = "false", required = false) String HandleDownload
    ) throws IOException {
        if (HandleDownload.equals("true")) {
            byte[] b = channelInfoService.download(typeCategoryEnum, tagNameEnum, HandleDownload);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=target.txt");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(b);
        } else {
            ResponseDto responseDto = channelInfoService.findTargetByTagNameAndType(typeCategoryEnum, tagNameEnum, HandleDownload);
            return ResponseEntity.ok()
                    .body(responseDto);
        }

    }
}
