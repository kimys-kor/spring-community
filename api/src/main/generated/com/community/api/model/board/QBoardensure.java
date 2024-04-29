package com.community.api.model.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardensure is a Querydsl query type for Boardensure
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardensure extends EntityPathBase<Boardensure> {

    private static final long serialVersionUID = 305225969L;

    public static final QBoardensure boardensure = new QBoardensure("boardensure");

    public final com.community.api.model.base.QBaseTime _super = new com.community.api.model.base.QBaseTime(this);

    public final NumberPath<Integer> boardType = createNumber("boardType", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Integer> hate = createNumber("hate", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgPath = createString("imgPath");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final BooleanPath notification = createBoolean("notification");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userIp = createString("userIp");

    public QBoardensure(String variable) {
        super(Boardensure.class, forVariable(variable));
    }

    public QBoardensure(Path<? extends Boardensure> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardensure(PathMetadata metadata) {
        super(Boardensure.class, metadata);
    }

}

