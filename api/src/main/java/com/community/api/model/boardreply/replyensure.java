package com.community.api.model.boardreply;

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
public class replyensure extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private int ref;
    private int refOrder;
    private int depth;
    private Long parentNum;

    private String username;
    private String fullname;
    private String userIp;
    private String content;
    private boolean isDeleted;

}
