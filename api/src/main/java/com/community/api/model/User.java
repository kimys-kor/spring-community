package com.community.api.model;

import com.community.api.model.base.UserGrade;
import com.community.api.model.base.UserRole;
import com.community.api.model.base.BaseTime;
import com.community.api.model.base.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    private String phoneNum;
    private String fullname;
    private String nickname;


    private int point;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserGrade grade = UserGrade.BRONZE;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime lastLogin;



    public User(String username, String password, String nickname, String phoneNum) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.role = UserRole.ROLE_USER;
        this.status = UserStatus.NORMAL;
    }
}
