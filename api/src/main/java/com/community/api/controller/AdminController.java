package com.community.api.controller;

import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.common.random.StringSecureRandom;
import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import com.community.api.model.dto.LoginRequestDto;
import com.community.api.service.RefreshTokenService;
import com.community.api.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final StringSecureRandom stringSecureRandom;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;


    @GetMapping(value = "/test")
    public Response<Object> test() {
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

    // 대시보드 일주일 신규 회원 라인 차트
    @GetMapping(value = "/dashboard/weekuser")
    public Response<Object> getWeekNewUserData(
    ) {
        List data = userService.countAllByCreatedDtBetween();

        List result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "week");
        map.put("data", data);
        result.add(map);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 유저 리스트
    @GetMapping(value = "/user/findall")
    public Response<Object> findAllUser(Pageable pageable
    ) {
        Map<String, Object> all = userService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, all);
    }

    // 유저 상세
    @GetMapping(value = "/user/findone")
    public Response<Object> findOneUser(
            @RequestParam Long userId
    ) {
        Map<String, Object> userInfo = userService.findById(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,userInfo);
    }

    // 유저 포인트 추가
    @GetMapping(value = "/user/add/point")
    public Response<Object> updateUserPoint(@RequestParam Long userId,
                                            @RequestParam Integer point) {
        userService.addPoint(userId, point);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 유저 임시 비밀번호 생성
    @GetMapping(value = "/user/password/reset")
    public Response<Object> userPasswordReset(@RequestParam Long userId) {
        String tempPassword = userService.updatePassword(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,tempPassword);
    }

    // 유저 정보 업데이트
    @PostMapping(value = "/user/update/info")
    public Response<Object> updateUserInfo(@RequestParam Long userId,
                                           @RequestParam String userNickname,
                                           @RequestParam String userGrade) {
        userService.updateInfo(userId, userNickname, userGrade);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }


}
