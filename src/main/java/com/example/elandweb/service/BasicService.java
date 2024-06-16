package com.example.elandweb.service;

import com.example.elandweb.dto.ResponseDto;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BasicService {
    default Pageable getPageable(int page, int size) {
        return (Pageable) PageRequest.of(page, size);
    }
    default int getSelectOffset(int page,int size){
        return (page-1)*size;
    }
    default int getSelectLimit(int page,int size){
        return page*size;
    }
//    default void deleteFile(String imageRealPath) {
//        System.out.println(imageRealPath);
//        File file = new File(imageRealPath);
//        System.out.println("File:"+file);
//        if(file.exists()){
//            file.delete();
//        }
//
//    }
    default ResponseDto getRestDto(Object o, String message,LocalDateTime localDateTime){
        ResponseDto responseDto = ResponseDto.builder()
                .message(message)
                .data(o)
                .localDateTime(localDateTime)
                .build();
        return responseDto;
    }
    default LocalDateTime getLocalDateTimeNow(){
        return LocalDateTime.now();
    }
//    default String getResponseMessage(String message){
//
//    }
}
