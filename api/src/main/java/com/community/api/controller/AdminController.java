package com.community.api.controller;

import com.community.api.common.jwt.JwtTokenProvider;
import com.community.api.common.properties.JwtProperties;
import com.community.api.common.random.StringSecureRandom;
import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import com.community.api.model.ApprovedIp;
import com.community.api.model.BlockedIp;
import com.community.api.model.dto.*;
import com.community.api.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final IpService ipService;
    private final PostService postService;
    private final CommentService commentService;


    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }


    // ip추가
    @PostMapping(value = "/add/ip")
    public Response<Object> addIp(
            @RequestBody SaveIpDto saveIpDto
            ) {

        ipService.saveIp(saveIpDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 차단 ip리스트
    @GetMapping(value = "/blockediplist")
    public Response<Object> findAllBlockedIp(
    ) {
        List<BlockedIp> allBlockedIp = ipService.findAllBlockedIp();
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, allBlockedIp);
    }

    // 허용 ip리스트
    @GetMapping(value = "/approvediplist")
    public Response<Object> findAllApprovedIp(
    ) {
        List<ApprovedIp> allApprovedIp = ipService.findAllApprovedIp();
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, allApprovedIp);
    }

    // ip 삭제
    @DeleteMapping(value = "/delete/ip")
    public Response<Object> deleteIp(
        String type,
        Long ipId
    ) {
        ipService.deleteIp(type, ipId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }


    // 유저 리스트
    @GetMapping(value = "/user/findall")
    public Response<Object> findAllUser(Pageable pageable
    ) {
        Page<UserReadDto> all = userService.findAll(pageable);
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

    // 유저 접근차단,해제
    @PatchMapping(value = "/set/block/{username}")
    public Response<Object> setBlock(
            @PathVariable String username
    ) {
        userService.setBlock(username);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }


    // 게시글 다중 삭제
    @PutMapping(value = "/delete/postlist")
    public Response<Object> deletePostList(
            @RequestBody DeletePostListDto dto,
            Authentication authentication
            ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        for (Long id : dto.idList) {
            postService.deletePost(username, id);
        }
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 게시글 다중 이동
    @PutMapping(value = "/transfer/postlist")
    public Response<Object> transferPostList(
            @RequestBody TransferPostListDto dto,
            Authentication authentication
    ) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        for (Long id : dto.getIdList()) {
            postService.transferPost(dto.getPostType(),username, id);
        }

        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 댓글 다중 삭제
    @PutMapping(value = "/delete/commentlist")
    public Response<Object> deleteCommentList(
            @RequestBody DeleteCommentListDto dto,
            Authentication authentication
    ) {
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetailis.getUsername();

        for (Long id : dto.idList) {
            commentService.deleteComment(username, id);
        }
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }


}
