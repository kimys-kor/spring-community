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

    //    1.파트너 2.축구분석 3.야구분석 4.농구분석 5.배구분석 6.안구정화 7.유머이슈 8.나는분석왕 9.자유 10.피해사례
    //    11.이벤트 12.포인트교환 13.일반 14.꽁머니 15.구인구직 16.기타 17.공지사항 18.1:1문의
    private int postType;
    private boolean notification;
    private String username;
    private String nickname;
    private String userIp;
    private String title;
    @Lob
    @Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String content;
    private int hit;
    private int hate;
    private int likes;
    private boolean isDeleted;
    private int replyNum;

    @Builder.Default
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    List<Comment> commentList = new ArrayList<Comment>();

}
