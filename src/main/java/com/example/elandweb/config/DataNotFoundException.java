package com.example.elandweb.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String msg){
        super(msg);
    }
}
