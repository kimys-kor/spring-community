package com.community.api.service;

import com.community.api.model.dto.ReadBoardContentDto;
import com.community.api.model.dto.ReadBoardListDto;
import com.community.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

        private final BoardCustomRepository boardCustomRepository;


        public Page<ReadBoardListDto> getList(int typ, Pageable pageable) {
                return boardCustomRepository.getList(typ, pageable);
        }

        public ReadBoardContentDto getContent(Long id) {
                return boardCustomRepository.getContent(id);
        }

        public List<ReadBoardListDto> getNoticeList(int typ) {
                return boardCustomRepository.getNoticeList(typ);
        }


//        public savePost() {
//
//        }



}
