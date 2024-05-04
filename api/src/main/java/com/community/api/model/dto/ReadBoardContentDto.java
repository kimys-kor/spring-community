package com.community.api.model.dto;

import lombok.Data;

@Data
public class ReadBoardContentDto {
    private Long id;
    private String username;
    private String fullname;
    private String userIp;
    private String title;
    private int hit;
    private int hate;
    private int likes;
    private int replyNum;
}
