package com.community.api.model.dto;

import lombok.Data;


@Data
public class SaveBannerDto {

    private String partnerName;
    // 썸네일url
    private String thumbNail;
    // 파트너링크url
    private String partnerUrl;

}
