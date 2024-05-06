package com.community.api.model.dto;

import lombok.Builder;

@Builder
public record RequestCommentDto(
        String username,
        String nickname,
        String userIp,
        String content
) {

}
