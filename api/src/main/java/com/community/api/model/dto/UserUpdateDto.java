package com.community.api.model.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

    private String password;

    private String phoneNum;
    private String fullName;
    private String nickname;

}
