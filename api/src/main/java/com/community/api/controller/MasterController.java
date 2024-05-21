package com.community.api.controller;

import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.common.random.StringSecureRandom;
import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import com.community.api.model.ApprovedIp;
import com.community.api.model.BlockedIp;
import com.community.api.model.dto.DeleteCommentListDto;
import com.community.api.model.dto.DeletePostListDto;
import com.community.api.model.dto.SaveIpDto;
import com.community.api.model.dto.UserReadDto;
import com.community.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class MasterController {

    private final UserService userService;
    private final StringSecureRandom stringSecureRandom;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;
    private final IpService ipService;
    private final PostService postService;
    private final CommentService commentService;


    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }



    // FIXME (마스터컨트롤러)관리자계정생성 기능

}
