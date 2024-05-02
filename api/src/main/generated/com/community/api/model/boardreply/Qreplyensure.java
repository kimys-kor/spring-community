package com.community.api.model.boardreply;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * Qreplyensure is a Querydsl query type for replyensure
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class Qreplyensure extends EntityPathBase<replyensure> {

    private static final long serialVersionUID = 1465530409L;

    public static final Qreplyensure replyensure = new Qreplyensure("replyensure");

    public final com.community.api.model.base.QBaseTime _super = new com.community.api.model.base.QBaseTime(this);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final StringPath fullname = createString("fullname");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Long> parentNum = createNumber("parentNum", Long.class);

    public final NumberPath<Integer> ref = createNumber("ref", Integer.class);

    public final NumberPath<Integer> refOrder = createNumber("refOrder", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public final StringPath userIp = createString("userIp");

    public final StringPath username = createString("username");

    public Qreplyensure(String variable) {
        super(replyensure.class, forVariable(variable));
    }

    public Qreplyensure(Path<? extends replyensure> path) {
        super(path.getType(), path.getMetadata());
    }

    public Qreplyensure(PathMetadata metadata) {
        super(replyensure.class, metadata);
    }

}

