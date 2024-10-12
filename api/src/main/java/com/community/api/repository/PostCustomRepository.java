package com.community.api.repository;


import com.community.api.model.dto.ReadBestPostListDto;
import com.community.api.model.dto.ReadPostContentDto;
import com.community.api.model.dto.ReadPostListDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.community.api.model.QPost.post;

@Repository
public class PostCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<ReadPostListDto> getList(int typ, String keyword, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<ReadPostListDto> results = queryFactory.select(Projections.fields(ReadPostListDto.class,
                        post.id,
                        post.username,
                        post.nickname,
                        post.userIp,
                        post.title,
                        post.hit,
                        post.hate,
                        post.likes,
                        post.replyNum,
                        post.createdDt
                        ))
                .from(post)
                .where(
                        post.postType.eq(typ),
                        post.isDeleted.eq(false),
                        keywordFilter(keyword)
                )
                .orderBy(post.createdDt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // 결과를 Pageable 형태로 변환
        List<ReadPostListDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    public Page<ReadBestPostListDto> getBestList(String period, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        LocalDateTime timeThreshold;

        // Set the time threshold based on the period
        if ("week".equalsIgnoreCase(period)) {
            timeThreshold = LocalDateTime.now().minusDays(7); // Last 7 days
        } else if ("day".equalsIgnoreCase(period)) {
            timeThreshold = LocalDateTime.now().minusHours(24); // Last 24 hours
        } else {
            throw new IllegalArgumentException("Invalid period. Allowed values are 'week' or 'day'.");
        }

        QueryResults<ReadBestPostListDto> results = queryFactory.select(Projections.fields(ReadBestPostListDto.class,
                        post.id,
                        post.postType,
                        post.username,
                        post.nickname,
                        post.userIp,
                        post.title,
                        post.hit,
                        post.hate,
                        post.likes,
                        post.replyNum,
                        post.createdDt
                ))
                .from(post)
                .where(
                        post.isDeleted.eq(false)
                                .and(post.createdDt.after(timeThreshold)) // Use the dynamic time threshold
                                .and(post.postType.between(2, 10)) // Filter postType between 2 and 10
                )
                .orderBy(post.hit.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // Convert the results to a Pageable format
        List<ReadBestPostListDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    public ReadPostContentDto getContent(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ReadPostContentDto readPostContentDto = queryFactory.select(Projections.fields(ReadPostContentDto.class,
                        post.id,
                        post.username,
                        post.nickname,
                        post.userIp,
                        post.title,
                        post.hit,
                        post.hate,
                        post.likes,
                        post.replyNum
                ))
                .from(post)
                .where(
                        post.id.eq(id),
                        post.isDeleted.eq(false)
                )
                .fetchOne();

        return readPostContentDto;
    }

    public List<ReadPostListDto> getNoticeList(int typ2) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<ReadPostListDto> results = queryFactory.select(Projections.fields(ReadPostListDto.class,
                        post.id,
                        post.username,
                        post.nickname,
                        post.userIp,
                        post.title,
                        post.hit,
                        post.hate,
                        post.likes,
                        post.replyNum
                ))
                .from(post)
                .where(
                        post.postType.eq(typ2),
                        post.isDeleted.eq(false)
                )
                .fetchResults();

        // 결과를 Pageable 형태로 변환
        List<ReadPostListDto> content = results.getResults();
        return content;
    }

    private BooleanExpression keywordFilter(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        return post.title.contains(keyword).or(post.content.contains(keyword)).or(post.username.contains(keyword));
    }


}
