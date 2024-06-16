package com.example.elandweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private Object data;
    private LocalDateTime localDateTime;
}
