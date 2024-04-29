package com.community.api.model.board;

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
public class Boardhide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1숨김게시판 2신고삭제게시판
    private int boardType;

    private boolean notification;
    private Long userId;
    private String userIp;
    private String title;
    private String content;
    private int hit;
    private int hate;
    private int likes;
    private String imgPath;
}
