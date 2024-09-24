package com.community.api.repository;

import com.community.api.model.ForbiddenWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForbiddenWordRepository extends JpaRepository<ForbiddenWord, Long> {

    List<ForbiddenWord> findAll();
}
