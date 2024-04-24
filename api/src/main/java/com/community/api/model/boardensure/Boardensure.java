package com.community.api.model.boardensure;

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
public class Boardensure extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1보증게시판 2먹튀피해게시판
    private int boardType;

    private boolean notification;
    private Long userId;
    private String title;
    private String content;
    private int hit;
    private int hate;
    private int likes;
    private String imgPath;
}
