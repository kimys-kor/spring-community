package com.community.api.model.dto;

import com.community.api.model.base.UserStatus;
import lombok.Data;

@Data
public class UserReadDto {

    private UserStatus status;
    private Long id;
    private String phoneNum;
    private String username;
    private String nickname;
    private String createdDt;
    private String lastLogin;
    private int point;
}
