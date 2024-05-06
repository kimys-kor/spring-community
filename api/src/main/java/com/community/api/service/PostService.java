package com.community.api.service;

import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.model.Post;
import com.community.api.model.User;
import com.community.api.model.dto.ReadPostContentDto;
import com.community.api.model.dto.ReadPostListDto;
import com.community.api.model.dto.RequestPostDto;
import com.community.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

        private final PostCustomRepository postCustomRepository;
        private final PostRepository postRepository;
        private final UserRepository userRepository;


        public Page<ReadPostListDto> getList(int typ, Pageable pageable) {
                return postCustomRepository.getList(typ, pageable);
        }

        public ReadPostContentDto getContent(Long id) {
                return postCustomRepository.getContent(id);
        }

        public List<ReadPostListDto> getNoticeList(int typ) {
                return postCustomRepository.getNoticeList(typ);
        }

        public void savePost(String userIp, String username, RequestPostDto requestPostDto) {
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isEmpty()) {
                        throw AuthenticationErrorCode.USER_NOT_EXIST.defaultException();
                }

                Post post = Post.builder()
                        .postType(requestPostDto.postType())
                        .notification(requestPostDto.notification())
                        .username(username)
                        .nickname(userOptional.get().getNickname())
                        .userIp(userIp)
                        .title(requestPostDto.title())
                        .content(requestPostDto.content())
                        .hit(1)
                        .hate(0)
                        .likes(0)
                        .isDeleted(false)
                        .replyNum(0)
                        .build();
                postRepository.save(post);
        }





}
