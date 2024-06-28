package com.example.elandweb.service;

import com.example.elandweb.dto.PageDataDto;
import com.example.elandweb.dto.ResponseDto;
import com.example.elandweb.model.BasicEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface BasicService {

    default PageDataDto getPageDataDto(int page, int size, long total, List<? extends BasicEntity> BasicEntity) {
        return PageDataDto.builder()
                .basicEntity(BasicEntity)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    default int getSelectOffset(int page, int size) {
        return (page - 1) * size;
    }

    default ResponseDto getRestDto(Object o, String message) {
        ResponseDto responseDto = ResponseDto.builder()
                .message(message)
                .data(o)
                .localDateTime(LocalDateTime.now())
                .build();
        return responseDto;
    }
}
