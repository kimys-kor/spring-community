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

    private final AdminActionHistoryService adminActionHistoryService;


    @GetMapping(value = "/test")
    public Response<Object> test() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }



    @GetMapping(value = "/adminActionHistory")
    public Response<Object> findAllUser(
            String keyword,
            Pageable pageable) {
        Page<AdminActionHistoryDto> adminHistory = adminActionHistoryService.findAll(keyword, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, adminHistory);
    }



}
