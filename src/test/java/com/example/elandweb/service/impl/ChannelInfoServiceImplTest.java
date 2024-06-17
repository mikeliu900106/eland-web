package com.example.elandweb.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChannelInfoServiceImplTest {
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
}