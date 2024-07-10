package com.example.elandweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TargetDto {
    private String tagName;
    private int newsCount;
    private int blogCount;
    private int forumCount;
    private int socialCount;
    private int commentCount;
    private int qaCount;
    private int videoCount;
    private short type;
}
