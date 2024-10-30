package com.community.api.model.dto;

import lombok.Data;

@Data
public class UserUpdateAdminDto {


    private String password;
    private String fullName;
    private String nickname;
    private String phoneNum;



}
