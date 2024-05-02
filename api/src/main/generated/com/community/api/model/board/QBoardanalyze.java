package com.community.api.model.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardanalyze is a Querydsl query type for Boardanalyze
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardanalyze extends EntityPathBase<Boardanalyze> {

    private static final long serialVersionUID = 1600139001L;

    public static final QBoardanalyze boardanalyze = new QBoardanalyze("boardanalyze");

    public final com.community.api.model.base.QBaseTime _super = new com.community.api.model.base.QBaseTime(this);

    public final NumberPath<Integer> boardType = createNumber("boardType", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final StringPath fullname = createString("fullname");

    public final NumberPath<Integer> hate = createNumber("hate", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final BooleanPath notification = createBoolean("notification");

    public final NumberPath<Integer> replyNum = createNumber("replyNum", Integer.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final StringPath userIp = createString("userIp");

    public final NumberPath<Long> username = createNumber("username", Long.class);

    public QBoardanalyze(String variable) {
        super(Boardanalyze.class, forVariable(variable));
    }

    public QBoardanalyze(Path<? extends Boardanalyze> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardanalyze(PathMetadata metadata) {
        super(Boardanalyze.class, metadata);
    }

}

