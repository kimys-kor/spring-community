package com.community.api.controller;

import com.community.api.common.response.Response;
import com.community.api.common.response.ResultCode;
import com.community.api.model.dto.ReadPostListDto;
import com.community.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;

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

    // 게시글 상세
    @GetMapping(value = "/content")
    public Response<Object> BoardContent(
            Long boardId
    ) {
        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING, postService.getContent(boardId));
    }
}
