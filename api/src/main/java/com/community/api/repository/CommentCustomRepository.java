package com.community.api.repository;

import com.community.api.model.Comment;
import com.community.api.model.dto.ReadSearchCommentDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.community.api.model.QComment.comment;
@Repository
public class CommentCustomRepository {

    @PersistenceContext
    private EntityManager em;


    public List<Comment> findByboardId(Long boardId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.post.id.eq(boardId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdDt.asc()
                ).fetch();
    }

    public List<ReadSearchCommentDto> searchComment(String keyword) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QueryResults<ReadSearchCommentDto> results = queryFactory.select(Projections.fields(ReadSearchCommentDto.class,
                        comment.id,
                        comment.content,
                        comment.username,
                        comment.nickname
                ))
                .from(comment)
                .where(
                        comment.isDeleted.isFalse(),
                        keywordFilter(keyword)

                )
                .fetchResults();

        // 결과를 Pageable 형태로 변환
        List<ReadSearchCommentDto> content = results.getResults();
        return content;
    }

    private BooleanExpression keywordFilter(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        return comment.content.contains(keyword).or(comment.username.contains(keyword)).or(comment.nickname.contains(keyword));
    }
}
