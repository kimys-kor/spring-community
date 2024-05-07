package com.community.api.service;


import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.common.exception.BoardErrorCode;
import com.community.api.common.exception.CommentErrorCode;
import com.community.api.model.Comment;
import com.community.api.model.Post;
import com.community.api.model.User;
import com.community.api.model.dto.ReadCommentDto;
import com.community.api.model.dto.SaveCommentDto;
import com.community.api.repository.CommentCustomRepository;
import com.community.api.repository.CommentRepository;
import com.community.api.repository.PostRepository;
import com.community.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentCustomRepository commentCustomRepository;
    private final UserRepository userRepository;


    public void saveComment(String remoteAddr, String username, SaveCommentDto saveCommentDto) {
        Post post = postRepository.findById(saveCommentDto.boardId()).orElseThrow(BoardErrorCode.POST_NOT_EXIST::defaultException);
        User user = userRepository.findByUsername(username).orElseThrow(AuthenticationErrorCode.USER_NOT_EXIST::defaultException);

            Comment comment = Comment.builder()
                    .username(username)
                    .nickname(user.getNickname())
                    .userIp(remoteAddr)
                    .content(saveCommentDto.content())
                    .isDeleted(false)
                    .post(post)
                    .parent(
                            saveCommentDto.parentId() != null ?
                                    commentRepository.findById(saveCommentDto.parentId())
                                            .orElseThrow(CommentErrorCode.COMMENT_NOT_EXIST::defaultException) : null
                    )
                    .build();
            commentRepository.save(comment);
    }

    public List<ReadCommentDto> findCommentsByPostId(Long boardId) {
        return convertNestedStructure(commentCustomRepository.findByboardId(boardId));
    }

    private List<ReadCommentDto> convertNestedStructure(List<Comment> comments) {
        List<ReadCommentDto> result = new ArrayList<>();
        Map<Long, ReadCommentDto> map = new HashMap<>();
        comments.stream().forEach(c -> {
            ReadCommentDto dto = ReadCommentDto.convertCommentToDto(c);
            map.put(dto.getId(), dto);
            if(c.getParent() != null) map.get(c.getParent().getId()).getChildren().add(dto);
            else result.add(dto);
        });
        return result;
    }
}
