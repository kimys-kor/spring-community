package com.community.api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PointHistoryDto {
    private Long id;
    private Long postId;
    private String username;
    private String pointContent;
    private int point;
    private LocalDateTime createdDt;
}