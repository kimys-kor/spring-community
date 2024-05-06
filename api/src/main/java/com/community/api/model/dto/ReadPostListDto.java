package com.community.api.model.dto;

import lombok.Data;

@Data
public class ReadPostListDto {
    private Long id;
    private String username;
    private String nickname;
    private String userIp;
    private String title;
    private int hit;
    private int hate;
    private int likes;
    private int replyNum;
}
