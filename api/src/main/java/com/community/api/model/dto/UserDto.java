package com.community.api.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;

    private String phoneNum;
    private String nickname;

//    private String fulllName;
}
