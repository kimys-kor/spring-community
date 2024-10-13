package com.community.api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReadPartnerPostListDto {
    private Long id;
    private int postType;
    private String username;
    private String nickname;
    private String userIp;
    private String thumbNail;
    private String title;
    private String code;
    private int hit;
    private int hate;
    private int likes;
    private int replyNum;
    private LocalDateTime createdDt;
}
