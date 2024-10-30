package com.community.api.service;

import com.community.api.model.Banner;
import com.community.api.model.dto.SaveBannerDto;
import com.community.api.repository.BannerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional
    public Banner createBanner(SaveBannerDto saveBannerDto) {
        Banner banner = Banner.builder()
                .partnerName(saveBannerDto.getPartnerName())
                .thumbNail(saveBannerDto.getUrl())
                .build();
        return bannerRepository.save(banner);
    }

    @Transactional
    public boolean deleteBannerById(Long id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        if (banner.isPresent()) {
            bannerRepository.delete(banner.get());
            return true;
        }
        return false;
    }

    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    public List<Banner> get3Banners() {
        List<Banner> banners = bannerRepository.findAll();

        if (banners.size() <= 3) {
            return banners;
        }

        Collections.shuffle(banners);
        return banners.subList(0, 3);
    }
}