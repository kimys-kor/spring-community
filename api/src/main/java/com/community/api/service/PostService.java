package com.community.api.service;

import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.common.exception.BoardErrorCode;
import com.community.api.common.exception.CommentErrorCode;
import com.community.api.model.Post;
import com.community.api.model.User;
import com.community.api.model.base.UserRole;
import com.community.api.model.dto.ReadPostContentDto;
import com.community.api.model.dto.ReadPostListDto;
import com.community.api.model.dto.SavePostDto;
import com.community.api.model.dto.UpdatePostDto;
import com.community.api.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

        @PersistenceContext
        EntityManager em;
        private final PostCustomRepository postCustomRepository;
        private final PostRepository postRepository;
        private final UserRepository userRepository;


        public Page<ReadPostListDto> getList(int typ, String keyword, Pageable pageable) {
                return postCustomRepository.getList(typ, keyword, pageable);
        }

        @Transactional
        public ReadPostContentDto getContent(Long id) {
                Post post = postRepository.findById(id).orElseThrow(BoardErrorCode.POST_NOT_EXIST::defaultException);
                post.setHit(post.getHit()+1);
                em.flush();
                em.clear();
                return mapToDTO(post);
        }


        private ReadPostContentDto mapToDTO(Post post) {
                ReadPostContentDto dto = new ReadPostContentDto();
                dto.setId(post.getId());
                dto.setUsername(post.getUsername()); // Assuming Post has a User field with getUsername method
                dto.setNickname(post.getNickname()); // Assuming Post has a User field with getNickname method
                dto.setUserIp(post.getUserIp());
                dto.setTitle(post.getTitle());
                dto.setHit(post.getHit());
                dto.setHate(post.getHate());
                dto.setLikes(post.getLikes());
                dto.setReplyNum(post.getReplyNum()); // Assuming commentList is the reply list
                return dto;
        }


        public List<ReadPostListDto> getNoticeList(int typ) {
                return postCustomRepository.getNoticeList(typ);
        }

        public void savePost(String userIp, String username, SavePostDto savePostDto) {
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isEmpty()) {
                        throw AuthenticationErrorCode.USER_NOT_EXIST.defaultException();
                }

                Post post = Post.builder()
                        .postType(savePostDto.postType())
                        .notification(savePostDto.notification())
                        .username(username)
                        .nickname(userOptional.get().getNickname())
                        .userIp(userIp)
                        .title(savePostDto.title())
                        .content(savePostDto.content())
                        .hit(1)
                        .hate(0)
                        .likes(0)
                        .isDeleted(false)
                        .replyNum(0)
                        .build();
                postRepository.save(post);
        }

        @Transactional
        public void updatePost(String username, UpdatePostDto updatePostDto) {
                Post post = postRepository.findById(updatePostDto.postId()).orElseThrow(
                        BoardErrorCode.POST_NOT_EXIST::defaultException);

                User user = userRepository.findByUsername(username).orElseThrow(AuthenticationErrorCode.USER_NOT_EXIST::defaultException);

                if (!post.getUsername().equals(username) && user.getRole().equals(UserRole.ROLE_USER)) {
                        throw BoardErrorCode.POST_WRITER_NOT_EQUALS.defaultException();
                }

                post.setPostType(updatePostDto.postType());
                post.setNotification(updatePostDto.notification());
                post.setTitle(updatePostDto.title());
                post.setContent(updatePostDto.content());
                em.flush();
                em.clear();
        }

        public void deletePost(String username, Long postId) {
                Post post = postRepository.findById(postId).orElseThrow(BoardErrorCode.POST_NOT_EXIST::defaultException);
                User user = userRepository.findByUsername(username).orElseThrow(AuthenticationErrorCode.USER_NOT_EXIST::defaultException);

                if (!post.getUsername().equals(username) && user.getRole().equals(UserRole.ROLE_USER)) {
                        throw BoardErrorCode.POST_WRITER_NOT_EQUALS.defaultException();
                }

                postRepository.delete(post);
        }

        public void deletePostList(List<Long> postList) {
                postRepository.deleteAllByIds(postList);
        }
}
