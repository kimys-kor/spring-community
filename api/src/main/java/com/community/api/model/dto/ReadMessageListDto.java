package com.community.api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadMessageListDto {
    private Long id;
    private String title;
    private LocalDateTime createdDt;

}
