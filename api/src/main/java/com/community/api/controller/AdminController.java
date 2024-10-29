package com.community.api.controller;

import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.common.security.PrincipalDetails;
import com.community.api.model.ApprovedIp;
import com.community.api.model.BlockedIp;
import com.community.api.model.dto.*;
import com.community.api.service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PointHistoryService pointHistoryService;
    private final AdminActionHistoryService adminActionHistoryService;

    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 유저 리스트
    @GetMapping(value = "/user/findall")
    public Response<Object> findAllUser(Pageable pageable) {
        Page<UserReadDto> all = userService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, all);
    }

    // 유저 상세
    @GetMapping(value = "/user/findone")
    public Response<Object> findOneUser(@RequestParam Long userId) {
        Map<String, Object> userInfo = userService.findById(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, userInfo);
    }

    // 유저 포인트 추가 (ActionType 1)
    @GetMapping(value = "/user/add/point")
    public Response<Object> updateUserPoint(
            @RequestParam Long userId,
            @RequestParam Integer point,
            Authentication authentication
    ) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        userService.addPoint(userId, point);
        adminActionHistoryService.save(1, username);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 유저 임시 비밀번호 생성
    @GetMapping(value = "/user/password/reset")
    public Response<Object> userPasswordReset(@RequestParam Long userId) {
        String tempPassword = userService.updatePassword(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, tempPassword);
    }

    // 유저 접근차단, 해제 (ActionType 2)
    @PatchMapping(value = "/set/block/{username}")
    public Response<Object> setBlock(@PathVariable String username, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String adminUsername = principalDetails.getUsername();

        userService.setBlock(username);
        adminActionHistoryService.save(2, adminUsername);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 포인트 히스토리 (ActionType 3)
    @GetMapping(value = "/point-history")
    public Response<Object> findAllPointHistories(String keyword, Pageable pageable, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        Page<PointHistoryDto> all = pointHistoryService.findAll(keyword, pageable);
        adminActionHistoryService.save(3, username);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING, all);
    }

    // IP 추가 (ActionType 4)
    @PostMapping(value = "/add/ip")
    public Response<Object> addIp(@RequestBody SaveIpDto saveIpDto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        ipService.saveIp(saveIpDto);
        adminActionHistoryService.save(4, username);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 차단 IP 리스트
    @GetMapping(value = "/blockediplist")
    public Response<Object> findAllBlockedIp() {
        List<BlockedIp> allBlockedIp = ipService.findAllBlockedIp();
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, allBlockedIp);
    }

    // 허용 IP 리스트
    @GetMapping(value = "/approvediplist")
    public Response<Object> findAllApprovedIp() {
        List<ApprovedIp> allApprovedIp = ipService.findAllApprovedIp();
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, allApprovedIp);
    }

    // IP 삭제 (ActionType 5)
    @DeleteMapping(value = "/delete/ip")
    public Response<Object> deleteIp(String type, Long ipId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        ipService.deleteIp(type, ipId);
        adminActionHistoryService.save(5, username);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 게시글 다중 삭제 (ActionType 6)
    @PutMapping(value = "/delete/postlist")
    public Response<Object> deletePostList(@RequestBody DeletePostListDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        for (Long id : dto.idList) {
            postService.deletePost(username, id);
        }
        adminActionHistoryService.save(6, username);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 게시글 다중 이동 (ActionType 7)
    @PutMapping(value = "/transfer/postlist")
    public Response<Object> transferPostList(@RequestBody TransferPostListDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        for (Long id : dto.getIdList()) {
            postService.transferPost(dto.getPostType(), username, id);
        }
        adminActionHistoryService.save(7, username);

        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 댓글 다중 삭제 (ActionType 12)
    @PutMapping(value = "/delete/commentlist")
    public Response<Object> deleteCommentList(@RequestBody DeleteCommentListDto dto, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String username = principalDetails.getUsername();

        for (Long id : dto.idList) {
            commentService.deleteComment(username, id);
        }
        adminActionHistoryService.save(12, username);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}