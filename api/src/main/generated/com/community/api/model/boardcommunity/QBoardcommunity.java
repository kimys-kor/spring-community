package com.community.api.model.boardcommunity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardcommunity is a Querydsl query type for Boardcommunity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardcommunity extends EntityPathBase<Boardcommunity> {

    private static final long serialVersionUID = 355136355L;

    public static final QBoardcommunity boardcommunity = new QBoardcommunity("boardcommunity");

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

    public QBoardcommunity(String variable) {
        super(Boardcommunity.class, forVariable(variable));
    }

    public QBoardcommunity(Path<? extends Boardcommunity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardcommunity(PathMetadata metadata) {
        super(Boardcommunity.class, metadata);
    }

}

