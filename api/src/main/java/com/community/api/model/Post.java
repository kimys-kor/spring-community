package com.community.api.model;

import com.community.api.model.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    //    1꽁머니 2토토 3기타 4구인구직 5축구분석 6야구분석 7농구분석 8배구분석 9하키분석 10자유 11유머 12포토 13보증 14먹튀피해
    private int postType;
    private boolean notification;

    private String username;
    private String nickname;
    private String userIp;

//    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String title;
    @Lob
    private String content;

    private int hit;
    private int hate;
    private int likes;
    private boolean isDeleted;
    private int replyNum;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

}
