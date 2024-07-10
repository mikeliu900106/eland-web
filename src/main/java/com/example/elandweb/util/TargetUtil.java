package com.example.elandweb.util;

import com.example.elandweb.model.TargetEntity;
import com.example.elandweb.model.TypeEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TargetUtil {
    Class<?> typeClass;

    Class<?> targetEntityClass;

    private static final Logger logger = Logger.getLogger(TargetUtil.class);

    public TargetUtil() throws ClassNotFoundException {
        this.typeClass = Class.forName("com.example.elandweb.model.TypeEnum");
        this.targetEntityClass = Class.forName("com.example.elandweb.model.TargetEntity");
    }


    private List<String> getAllTypeEnumNames(TypeEnum type) {
        logger.debug("type:" + type);
        List<String> AllTypeEnumNames = Arrays.stream(this.typeClass.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.toList());
        logger.debug("全部typeEnumName:" + AllTypeEnumNames);
        return AllTypeEnumNames;
    }

    public List<String> chooseNotInType(TypeEnum typeEnumName) {
        logger.debug("typeEnumName:" + typeEnumName);
        List<String> allEnumNames = getAllTypeEnumNames(typeEnumName);
        return allEnumNames.stream()
                .filter(enumName -> !enumName.equals(typeEnumName.name()))
                .collect(Collectors.toList());
    }

    public void setChooseFieldsToZero(List<String> beChooseTypeEnumName, List<TargetEntity> targetsEntity) throws IllegalAccessException {
        for (TargetEntity targetEntity : targetsEntity) {
            Field[] fields = this.targetEntityClass.getDeclaredFields();
            for (Field field : fields) {
                for(String typeEnumName : beChooseTypeEnumName){
//                    logger.debug("變數名稱:"+field.getName());
                    if(field.getName().contains(typeEnumName.toLowerCase())){

                        field.setAccessible(true);
                        field.set(targetEntity,0);
                    }
                }
                field.setAccessible(true);
            }
        }

    }
}
