package com.community.api.model.dto;

import com.community.api.model.base.UserStatus;
import lombok.Data;

@Data
public class UserReadDto {

    private Long id;
    private String username;
    private String phoneNum;
    private String fullName;
    private String nickname;
    private int point;
    private int exp;
    private UserStatus status;
    private String createdDt;
    private String lastLogin;
}
