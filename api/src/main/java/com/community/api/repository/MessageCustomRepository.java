package com.community.api.repository;

import com.community.api.model.dto.ReadMessageListDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.community.api.model.QMessage.message;

@Repository
public class MessageCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<ReadMessageListDto> getList(String username, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<ReadMessageListDto> results = queryFactory.select(Projections.fields(ReadMessageListDto.class,
                        message.id,
                        message.sender,
                        message.title,
                        message.isChecked,
                        message.createdDt
                ))
                .from(message)
                .where(
                        message.receiver.eq(username)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // 결과를 Pageable 형태로 변환
        List<ReadMessageListDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}
