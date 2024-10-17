package com.community.api.controller;

import com.community.api.common.exception.AuthenticationErrorCode;
import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.common.random.StringSecureRandom;
import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import com.community.api.model.User;
import com.community.api.model.base.UserStatus;
import com.community.api.model.dto.*;
import com.community.api.service.CommentService;
import com.community.api.service.PostService;
import com.community.api.service.RefreshTokenService;
import com.community.api.service.UserService;
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
import java.util.Map;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final RefreshTokenService refreshTokenService;
    private final StringSecureRandom stringSecureRandom;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 로그인
    @PostMapping(value = "/login")
    public Response<Object> login(
            @RequestBody @Valid LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        PrincipalDetails principalDetailis = (PrincipalDetails) authenticate.getPrincipal();
        String username = principalDetailis.getUser().getUsername();
        User user = userService.findByUsername(username);

        if (!user.getStatus().equals(UserStatus.NORMAL)) {
            throw AuthenticationErrorCode.USER_NOT_EXIST.defaultException();
        }

        // access token 헤더 추가
        String jwtToken = jwtTokenProvider.generateToken(principalDetailis.getUser().getId(), username);
        response.addHeader(jwtProperties.headerString(), "Bearer "+jwtToken);

        // refresh token 쿠키 추가
        String refreshToken = stringSecureRandom.next(20);
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        // 60초 × 60분 × 24시간 × 30일
        cookie.setMaxAge(2_592_000);
        cookie.setDomain("");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        refreshTokenService.save(principalDetailis.getUser().getUsername(), refreshToken);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 회원가입
    @PostMapping(value = "/join")
    public Response<Object> join(
            HttpServletRequest request,
            @RequestBody @Valid JoinRequestDto joinRequestDto
    ){

        userService.join(joinRequestDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 아이디, 비밀번호 찾기 (문자인증)

    // 댓글 리스트
    @GetMapping(value = "/list/comment")
    public Response<Object> listComment(
            Long boardId,
            Pageable pageable
    ) {
        Map<String, Object> commentsByPostId = commentService.findCommentsByPostId(boardId, pageable);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, commentsByPostId);
    }

    // 게시글 리스트
    @GetMapping(value = "/list")
    public Response<Object> BoardList(
            int typ,
            String keyword,
            Pageable pageable
    ) {
        Page<ReadPostListDto> list = postService.getList(typ, keyword, pageable);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, list);
    }

    // 베스트 게시글 리스트
    @GetMapping(value = "/bestList")
    public Response<Object> BestBoardList(
            String period,
            Pageable pageable
    ) {
        Page<ReadBestPostListDto> list = postService.getBestList(period, pageable);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, list);
    }

    // 최근 게시글 리스트
    @GetMapping(value = "/newList")
    public Response<Object> NewBoardList(
            Pageable pageable
    ) {
        Page<ReadBestPostListDto> list = postService.getNewList(pageable);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, list);
    }

    // 파트너 게시글 리스트
    @GetMapping(value = "/partnerList")
    public Response<Object> PartnerList(
            Pageable pageable
    ) {
        Page<ReadPartnerPostListDto> list = postService.getPartnerList(pageable);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, list);
    }

    // 게시글 상세
    @GetMapping(value = "/content")
    public Response<Object> BoardContent(
            Long boardId,
            Authentication authentication
    ) {
        String username = null;
        if (authentication != null) {
            PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
            username = principalDetailis.getUsername();
        }


        ReadPostContentDto post = postService.getContent(username, boardId);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, post);
    }
}
