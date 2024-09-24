package com.community.api.service;

import com.community.api.model.ForbiddenWord;
import com.community.api.repository.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForbiddenWordService {

    private final ForbiddenWordRepository forbiddenWordRepository;


    // 금지어 모두 반환(단어만)
    public List<String> getAllForbiddenWords() {
        // 모든 ForbiddenWord 엔티티를 조회하고, word 속성만 추출하여 리스트로 반환
        return forbiddenWordRepository.findAll()
                .stream()
                .map(ForbiddenWord::getWord)
                .collect(Collectors.toList());
    }

    // 금지어 모두 반환(id포함)
    public List<ForbiddenWord> findAll(){
        return forbiddenWordRepository.findAll();
    }

    public void deleteForbiddenWord(Long id) {
        forbiddenWordRepository.deleteById(id);
    }

    public ForbiddenWord createForbiddenWord(String word) {
        ForbiddenWord forbiddenWord = ForbiddenWord.builder()
                .word(word)
                .build();

        return forbiddenWordRepository.save(forbiddenWord);
    }

}
