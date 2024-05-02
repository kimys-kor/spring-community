package com.community.api.service;

import com.community.api.repository.BoardadRepository;
import com.community.api.repository.BoardanalyzeRepository;
import com.community.api.repository.BoardcommunityRepository;
import com.community.api.repository.BoardensureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

        private final BoardadRepository boardadRepository;
        private final BoardanalyzeRepository boardanalyzeRepository;
        private final BoardcommunityRepository boardcommunityRepository;
        private final BoardensureRepository boardensureRepository;





}
