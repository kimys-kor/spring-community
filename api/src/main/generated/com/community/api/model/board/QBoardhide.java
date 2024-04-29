package com.community.api.model.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardhide is a Querydsl query type for Boardhide
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardhide extends EntityPathBase<Boardhide> {

    private static final long serialVersionUID = -683396427L;

    public static final QBoardhide boardhide = new QBoardhide("boardhide");

    public final NumberPath<Integer> boardType = createNumber("boardType", Integer.class);

    public final StringPath content = createString("content");

    public final NumberPath<Integer> hate = createNumber("hate", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgPath = createString("imgPath");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final BooleanPath notification = createBoolean("notification");

    public final StringPath title = createString("title");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userIp = createString("userIp");

    public QBoardhide(String variable) {
        super(Boardhide.class, forVariable(variable));
    }

    public QBoardhide(Path<? extends Boardhide> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardhide(PathMetadata metadata) {
        super(Boardhide.class, metadata);
    }

}

