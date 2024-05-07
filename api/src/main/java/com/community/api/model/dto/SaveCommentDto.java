package com.community.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SaveCommentDto(
        @NotBlank(message = "boardId must not be blank")
        Long boardId,
        Long parentId,
        @NotBlank(message = "content must not be blank")
        String content
) {

}
