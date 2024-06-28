package com.example.elandweb.config;

import com.example.elandweb.service.impl.TagInfoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException{
    private static final Logger logger = Logger.getLogger(DataNotFoundException.class);
    public DataNotFoundException(String msg){
        super(msg);
    }
}
