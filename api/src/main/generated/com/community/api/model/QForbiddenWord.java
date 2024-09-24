package com.community.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QForbiddenWord is a Querydsl query type for ForbiddenWord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QForbiddenWord extends EntityPathBase<ForbiddenWord> {

    private static final long serialVersionUID = -737650056L;

    public static final QForbiddenWord forbiddenWord = new QForbiddenWord("forbiddenWord");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath word = createString("word");

    public QForbiddenWord(String variable) {
        super(ForbiddenWord.class, forVariable(variable));
    }

    public QForbiddenWord(Path<? extends ForbiddenWord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QForbiddenWord(PathMetadata metadata) {
        super(ForbiddenWord.class, metadata);
    }

}

