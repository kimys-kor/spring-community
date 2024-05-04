package com.community.api.repository;

import com.community.api.model.QBoard;
import com.community.api.model.dto.ReadBoardContentDto;
import com.community.api.model.dto.ReadBoardListDto;
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

@Repository
public class BoardCustomRepository {
    @PersistenceContext
    private EntityManager em;
    public Page<ReadBoardListDto> getList(int typ2, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<ReadBoardListDto> results = queryFactory.select(Projections.fields(ReadBoardListDto.class,
                        QBoard.board.id,
                        QBoard.board.username,
                        QBoard.board.fullname,
                        QBoard.board.userIp,
                        QBoard.board.title,
                        QBoard.board.hit,
                        QBoard.board.hate,
                        QBoard.board.likes,
                        QBoard.board.replyNum
                        ))
                .from(QBoard.board)
                .where(
                        QBoard.board.boardType.eq(typ2),
                        QBoard.board.isDeleted.eq(false)
                )
                .fetchResults();

        // 결과를 Pageable 형태로 변환
        List<ReadBoardListDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    public ReadBoardContentDto getContent(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        ReadBoardContentDto readBoardContentDto = queryFactory.select(Projections.fields(ReadBoardContentDto.class,
                        QBoard.board.id,
                        QBoard.board.username,
                        QBoard.board.fullname,
                        QBoard.board.userIp,
                        QBoard.board.title,
                        QBoard.board.hit,
                        QBoard.board.hate,
                        QBoard.board.likes,
                        QBoard.board.replyNum
                ))
                .from(QBoard.board)
                .where(
                        QBoard.board.id.eq(id),
                        QBoard.board.isDeleted.eq(false)
                )
                .fetchOne();

        return readBoardContentDto;
    }

    public List<ReadBoardListDto> getNoticeList(int typ2) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        QueryResults<ReadBoardListDto> results = queryFactory.select(Projections.fields(ReadBoardListDto.class,
                        QBoard.board.id,
                        QBoard.board.username,
                        QBoard.board.fullname,
                        QBoard.board.userIp,
                        QBoard.board.title,
                        QBoard.board.hit,
                        QBoard.board.hate,
                        QBoard.board.likes,
                        QBoard.board.replyNum
                ))
                .from(QBoard.board)
                .where(
                        QBoard.board.boardType.eq(typ2),
                        QBoard.board.isDeleted.eq(false)
                )
                .fetchResults();

        // 결과를 Pageable 형태로 변환
        List<ReadBoardListDto> content = results.getResults();
        return content;
    }




}
