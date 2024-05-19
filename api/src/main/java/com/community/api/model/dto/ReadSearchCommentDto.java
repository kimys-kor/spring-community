package com.community.api.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReadSearchCommentDto {
    private Long id;
    private String content;
    private String username;
    private String nickname;
    private boolean isDeleted;
}
