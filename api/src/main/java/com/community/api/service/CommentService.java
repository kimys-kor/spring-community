package com.community.api.service;


import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.common.exception.BoardErrorCode;
import com.community.api.common.exception.CommentErrorCode;
import com.community.api.model.Comment;
import com.community.api.model.Post;
import com.community.api.model.User;
import com.community.api.model.base.UserRole;
import com.community.api.model.dto.ReadCommentDto;
import com.community.api.model.dto.ReadSearchCommentDto;
import com.community.api.model.dto.SaveCommentDto;
import com.community.api.repository.CommentCustomRepository;
import com.community.api.repository.CommentRepository;
import com.community.api.repository.PostRepository;
import com.community.api.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    @PersistenceContext
    EntityManager em;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentCustomRepository commentCustomRepository;
    private final UserRepository userRepository;


    @Transactional
    public void saveComment(String remoteAddr, String username, SaveCommentDto saveCommentDto) {
        
        // 대댓글 까지만 허용
        if (saveCommentDto.parentId() != null) {
            Comment parentComment = commentRepository.findById(saveCommentDto.parentId()).orElseThrow();
            if (parentComment.getParent() != null) {
                throw BoardErrorCode.COMMENT_ONLY_CAN_2STEP.defaultException();
            }
        }
        
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
            post.setReplyNum(post.getReplyNum()+1);
            em.flush();
            em.clear();
    }

    public List<ReadCommentDto> findCommentsByPostId(Long boardId) {
        return convertNestedStructure(commentCustomRepository.findByboardId(boardId));
    }

    public List<ReadSearchCommentDto> searchComment(String keyword) {
        return commentCustomRepository.searchComment(keyword);
    }

    public void deleteComment(String username, Long commentId) {
        Comment comment = commentRepository.findCommentByIdWithParent(commentId);
        User user = userRepository.findByUsername(username).orElseThrow(AuthenticationErrorCode.USER_NOT_EXIST::defaultException);

        if (!comment.getUsername().equals(username) && user.getRole().equals(UserRole.ROLE_USER)) {
            throw CommentErrorCode.COMMENT_WRITER_NOT_EQUALS.defaultException();
        }

        if(comment.getChildren().size() != 0) {
            comment.changeDeletedStatus(true);
        } else {
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }


    private Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getChildren().size() == 1 && parent.isDeleted() == true)
            return getDeletableAncestorComment(parent);
        return comment;
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

    @Transactional
    public void updateComment(String username, SaveCommentDto saveCommentDto) {
        Comment comment = commentRepository.findById(saveCommentDto.id()).orElseThrow(CommentErrorCode.COMMENT_NOT_EXIST::defaultException);
        User user = userRepository.findByUsername(username).orElseThrow(AuthenticationErrorCode.USER_NOT_EXIST::defaultException);

        if (!comment.getUsername().equals(username) && user.getRole().equals(UserRole.ROLE_USER)) {
            throw CommentErrorCode.COMMENT_WRITER_NOT_EQUALS.defaultException();
        }

        comment.setContent(saveCommentDto.content());
        em.flush();
        em.clear();
    }



}
