package com.community.api.model.board;

import com.community.api.model.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boardad extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1꽁머니 2토토 3기타 4구인구직
    private int boardType;

    private boolean notification;
    private Long username;
    private String fullname;
    private String userIp;
    private String title;
    private String content;
    private int hit;
    private int hate;
    private int likes;
    private boolean isDeleted;
    private int replyNum;
}
