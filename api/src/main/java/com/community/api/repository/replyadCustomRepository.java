package com.community.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class replyadCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveReply() {

    }

}
