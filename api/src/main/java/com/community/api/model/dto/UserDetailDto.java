package com.community.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.community.api.model.base.UserGrade;
import lombok.Data;


@Data
public class UserDetailDto {
    private Long userId;
    private String nickname;;
    private UserGrade userGrade;
    private int point;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdDt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String lastLogin;
    private String phoneNum;

}
