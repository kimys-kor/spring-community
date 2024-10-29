package com.community.api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminActionHistoryDto {

    private Long id;
    private int actionType;
    private String username;
    private String content;
    private LocalDateTime createdDt;
}
