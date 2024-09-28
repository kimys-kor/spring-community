package com.community.api.controller;

import com.community.api.model.Comment;
import com.community.api.model.Dm;
import com.community.api.model.User;
import com.community.api.model.dto.*;
import com.community.api.service.*;
import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.common.random.StringSecureRandom;
import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final PostService postService;
    private final LikePostService likePostService;
    private final CommentService commentService;
    private final DmService dmService;

    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 토큰 리프레쉬
    @GetMapping(value = "/refresh")
    public Response<Object> refresh (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String accessToken = refreshTokenService.refresh(request);
        response.addHeader(jwtProperties.headerString(), "Bearer "+accessToken);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 내정보 조회
    @GetMapping(value = "/my-info")
    public Response<Object> viewMyinfo(
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        User byUsername = userService.findByUsername(username);

        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, byUsername);
    }


    // 내정보 수정
    @PatchMapping(value = "/update/myinfo")
    public Response<Object> updateMyInfo(
            @RequestBody UserUpdateDto userUpdateDto,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        userService.updateMyInfo(username, userUpdateDto);

        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }


    // 게시글 쓰기
    @PostMapping(value = "/save/post")
    public Response<Object> savePost(
            @RequestBody @Valid SavePostDto savePostDto,
            HttpServletRequest request,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        postService.savePost(request.getRemoteAddr(), username, savePostDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 게시글 수정
    @PatchMapping(value = "/update/post")
    public Response<Object> updatePost(
            @RequestBody @Valid UpdatePostDto updatePostDto,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        postService.updatePost(username, updatePostDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 게시글 좋아요
    @PatchMapping(value = "/like/post")
    public Response<Object> updateLikePost(
            @RequestParam Long boardId,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        likePostService.likePost(username, boardId);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 게시글 삭제
    @DeleteMapping(value = "/post/{postId}")
    public Response<Object> deletePost(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        postService.deletePost(username, postId);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 댓글 작성
    @PostMapping(value = "/write/comment")
    public Response<Object> writeComment(
            @RequestBody @Valid SaveCommentDto saveCommentDto,
            HttpServletRequest request,
            Authentication authentication
            ) {

        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        commentService.saveComment(request.getRemoteAddr(), username, saveCommentDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }



    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    private Response<Object> deleteComment(
            @PathVariable Long commentId,
            Authentication authentication
    ){
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        commentService.deleteComment(username, commentId);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 댓글 수정
    @PatchMapping("/comment")
    private Response<Object> updateComment(
            @RequestBody SaveCommentDto saveCommentDto,
            Authentication authentication
    ){
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        commentService.updateComment(username, saveCommentDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 메세지 보내기
    @PostMapping("/send/dm")
    private Response<Object> sendDm(
            @RequestBody DmDto dmDto,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        Dm dm = dmService.sendDm(username, dmDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, dm);
    }

    // 메세지 리스트
    @GetMapping("/get/dmlist")
    private Response<Object> getDmList(
            Pageable pageable,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        Page<ReadDmListDto> messageList = dmService.getDmList(username, pageable);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, messageList);
    }

    // 메세지 읽기
    @GetMapping("/get/{dmId}")
    private Response<Object> getDmContent(
            @PathVariable Long dmId
    ) {
        Dm dmContent = dmService.getDmContent(dmId);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, dmContent);
    }

    // 메세지 삭제
    @PutMapping("/delete/dm")
    private Response<Object> deleteDm(
            @RequestBody List<Long> dmIdList
    ) {
        dmService.deleteMessage(dmIdList);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }



    


}
