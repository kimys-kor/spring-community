package com.community.api.service;

import com.community.api.model.ImgFile;
import com.community.api.repository.ImgFileRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImgFileService {

    private final ImgFileRepository imgFileRepository;

    public ImgFile save(ImgFile imgFile) {
        return imgFileRepository.save(imgFile);
    }

    public ImgFile findByIdEquals(int id) {
        return imgFileRepository.findByIdEquals(id);
    }
}
