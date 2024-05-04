package com.community.api.model;

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
public class Board extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1꽁머니 2토토 3기타 4구인구직 5축구분석 6야구분석 7농구분석 8배구분석 9하키분석 10자유 11유머 12포토 13보증 14먹튀피해
    private int boardType;
    private boolean notification;
    private String username;
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
