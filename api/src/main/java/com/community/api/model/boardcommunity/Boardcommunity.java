package com.community.api.model.boardcommunity;

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
public class Boardcommunity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1자유 2유머 3포토
    private int boardType;

    private boolean notification;
    private Long userId;
    private String title;
    private String content;
    private int hit;
    private int hate;
    private int like;
    private String imgPath;
}
