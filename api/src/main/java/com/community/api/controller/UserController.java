package com.community.api.controller;

import com.community.api.model.dto.LoginRequestDto;
import com.community.api.model.dto.JoinRequestDto;
import com.community.api.model.dto.SavePostDto;
import com.community.api.model.dto.UpdatePostDto;
import com.community.api.service.PostService;
import com.community.api.service.RefreshTokenService;
import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.common.random.StringSecureRandom;
import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import com.community.api.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final StringSecureRandom stringSecureRandom;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PostService postService;

    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @GetMapping(value = "/refresh")
    public Response<Object> refresh (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String accessToken = refreshTokenService.refresh(request);
        response.addHeader(jwtProperties.headerString(), "Bearer "+accessToken);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @PostMapping(value = "/login")
    public Response<Object> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        PrincipalDetails principalDetailis = (PrincipalDetails) authenticate.getPrincipal();

        // access token 헤더 추가
        String jwtToken = jwtTokenProvider.generateToken(principalDetailis.getUser().getId(), principalDetailis.getUser().getUsername());
        response.addHeader(jwtProperties.headerString(), "Bearer "+jwtToken);

        // refresh token 쿠키 추가
        String refreshToken = stringSecureRandom.next(20);
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge(2_592_000);
        cookie.setDomain("");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        refreshTokenService.save(principalDetailis.getUser().getUsername(), refreshToken);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @PostMapping(value = "/join")
    public Response<Object> join(
            @RequestBody @Valid JoinRequestDto joinRequestDto
    ){
        userService.join(joinRequestDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 아이디, 비밀번호 찾기 (문자인증)
    // 내정보 수정
    // 내정보 확인

    @PostMapping(value = "/save/post")
    public Response<Object> savePost(
            @RequestBody SavePostDto savePostDto,
            HttpServletRequest request,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        postService.savePost(request.getRemoteAddr(), username, savePostDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @PatchMapping(value = "/update/post")
    public Response<Object> updatePost(
            @RequestBody UpdatePostDto updatePostDto,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        postService.updatePost(username, updatePostDto);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @DeleteMapping(value = "/delete/post")
    public Response<Object> deletePost(
            Long postId,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        postService.deletePost(username, postId);
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }






}
