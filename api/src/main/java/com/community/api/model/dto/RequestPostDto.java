package com.community.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
public record RequestPostDto(
        @NotBlank(message = "postType must not be blank")
        int postType,
        @NotBlank(message = "notification must not be blank")
        boolean notification,
        @NotBlank(message = "title must not be blank")
        String title,
        @NotBlank(message = "content must not be blank")
        String content
) {

}
