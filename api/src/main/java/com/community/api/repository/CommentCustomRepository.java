package com.community.api.repository;

import com.community.api.model.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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
}
