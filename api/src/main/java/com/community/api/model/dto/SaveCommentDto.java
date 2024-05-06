package com.community.api.model.dto;

import lombok.Builder;

@Builder
public record SaveCommentDto(
        String username,
        String nickname,
        String userIp,
        String content
) {

}
