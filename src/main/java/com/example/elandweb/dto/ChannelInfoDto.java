package com.example.elandweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelInfoDto {

        private String sourceAreaId;

        private boolean isUsed;

        private String sourceId;

        private String pType2;

}
