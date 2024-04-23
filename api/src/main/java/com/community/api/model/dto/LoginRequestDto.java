package com.community.api.model.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
