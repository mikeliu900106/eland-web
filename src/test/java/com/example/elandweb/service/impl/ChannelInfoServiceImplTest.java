package com.example.elandweb.service.impl;

import com.example.elandweb.dao.TargetDao;
import com.example.elandweb.dto.TargetDto;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class ChannelInfoServiceImplTest {
    @Autowired
    private  TargetDao targetDao;
    @Test
    public  void test(){
        String LastChannelInfoSourceAreaId = "WH_B0002_0873";
        String cutNumber = LastChannelInfoSourceAreaId.substring(LastChannelInfoSourceAreaId.length() - 4);
        System.out.println(LastChannelInfoSourceAreaId);
        String newChannelInfoSourceAreaId = "WH_B0002";
        int number = Integer.parseInt(cutNumber);
        number = number + 1;
        System.out.println(number);
        if(number > 1000){
            newChannelInfoSourceAreaId = newChannelInfoSourceAreaId + "_" + number;
        }else if (number > 100){
            newChannelInfoSourceAreaId = newChannelInfoSourceAreaId + "_0" + number;
        }
        else if (number > 10){
            newChannelInfoSourceAreaId = newChannelInfoSourceAreaId + "_00" + number;
        }
        else if (number > 1){
            newChannelInfoSourceAreaId = newChannelInfoSourceAreaId + "_000" + number;
        }
        System.out.println(newChannelInfoSourceAreaId);
        assertEquals("WH_B0002_0874",newChannelInfoSourceAreaId);
    }
    @Test
    public  void test2(){
        List<TargetDto> targetsDto = targetDao.findTargetByTagNameAndType(null,null);
        System.out.println(targetsDto);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmmss"));
        String PathName = "C:\\test\\export" + "\\" + "target.txt";
        try (CSVWriter writer = new CSVWriter(new FileWriter(PathName))) {
            writer.writeNext(new String[]{"標籤", "新聞", "部落格", "討論區", "社群網站", "評論", "問答網站", "影音"});

            for (TargetDto target : targetsDto) {
                writer.writeNext(new String[]{
                        target.getTagName(),
                        String.valueOf(target.getNewsCount()),
                        String.valueOf(target.getBlogCount()),
                        String.valueOf(target.getForumCount()),
                        String.valueOf(target.getSocialCount()),
                        String.valueOf(target.getCommentCount()),
                        String.valueOf(target.getQaCount()),
                        String.valueOf(target.getVideoCount())
                });
            }
            OutputStream outputStream = new FileOutputStream(new File(PathName));
            System.out.println(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}