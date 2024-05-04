package com.community.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BoardReplyCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveReply() {

    }

}
