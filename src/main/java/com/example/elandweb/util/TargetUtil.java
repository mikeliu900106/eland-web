package com.example.elandweb.util;

import com.example.elandweb.model.BasicEntity;
import com.example.elandweb.model.TypeEnum;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class TargetUtil {
    Class<?> typeClass ;

    Class<?> TargetEntityClass ;

    public TargetUtil() throws ClassNotFoundException {
        this.typeClass =  Class.forName("com.example.elandweb.model.TypeEnum");
        this.TargetEntityClass =  Class.forName("com.example.elandweb.model.TargetEntity");
    }

    private  List<String> removeTargetType(TypeEnum type) {
        return chooseNotInType(type);
    }

    public List<String> chooseNotInType(TypeEnum type) {
        Field[] fields = this.typeClass.getFields();
        return Arrays.stream(fields)
                .filter(field -> !field.getName().equals(type))
                .map(field -> field.toString())
                .toList();
    }

}
