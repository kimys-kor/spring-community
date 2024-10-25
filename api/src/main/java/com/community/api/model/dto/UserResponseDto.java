package com.community.api.model.dto;

import com.community.api.model.User;
import com.community.api.model.base.UserRole;
import com.community.api.model.base.UserStatus;
import lombok.Data;

@Data
public class UserResponseDto {
    private String username;
    private String phoneNum;
    private String fullName;
    private String nickname;
    private int point;
    private UserStatus status;
    private UserRole role;
    private String sck;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.phoneNum = user.getPhoneNum();
        this.fullName = user.getFullName();
        this.nickname = user.getNickname();
        this.point = user.getPoint();
        this.status = user.getStatus();
        this.role = user.getRole();
    }

}
