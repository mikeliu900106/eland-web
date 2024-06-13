package com.example.elandweb.service;

import com.example.elandweb.dto.ResponseDto;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
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
    default int getDataCount(List<?> data){
        return data.size();
    }

//    default String getId(JpaRepository repository , String idType , int x){
//        long userCount = repository.count();
//        Date dNow = new Date( );
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMdd");
//        String today =ft.format(dNow);
//        int intToday = Integer.valueOf(today);
//        intToday *=100;
//        intToday +=userCount;
//        idType = idType.substring(0,x);
//        String studentId = idType + intToday;
//        return studentId;
//    }
//    default void deleteFile(String imageRealPath) {
//        System.out.println(imageRealPath);
//        File file = new File(imageRealPath);
//        System.out.println("File:"+file);
//        if(file.exists()){
//            file.delete();
//        }
//
//    }
    default ResponseDto getRestDto(Object o, String message){
        ResponseDto responseDto = ResponseDto.builder()
                .message(message)
                .data(o)
                .build();
        return responseDto;
    }
//    default String getResponseMessage(String message){
//
//    }
}
