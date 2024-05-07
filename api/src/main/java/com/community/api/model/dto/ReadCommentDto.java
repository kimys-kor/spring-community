package com.community.api.model.dto;

import com.community.api.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCommentDto {

    private Long id;
    private String content;
    private Long postId;
    private String username;
    private String nickname;
    private List<ReadCommentDto> children = new ArrayList<>();
    private boolean isDeleted;


    public ReadCommentDto(Long id, String content, Long postId, String username, String nickname, boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.postId = postId;
        this.username = username;
        this.nickname = nickname;
        this.isDeleted = isDeleted;

    }

    public static ReadCommentDto convertCommentToDto(Comment comment) {
        return comment.isDeleted() == true ?
                new ReadCommentDto(comment.getId(), "삭제된 댓글입니다.", null, null,null,true) :
                new ReadCommentDto(comment.getId(), comment.getContent(), comment.getPost().getId(), comment.getUsername(),
                        comment.getNickname(), comment.isDeleted());
    }
}
