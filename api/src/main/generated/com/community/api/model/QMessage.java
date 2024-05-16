package com.community.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = 450358172L;

    public static final QMessage message = new QMessage("message");

    public final com.community.api.model.base.QBaseTime _super = new com.community.api.model.base.QBaseTime(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final StringPath receiver = createString("receiver");

    public final StringPath sender = createString("sender");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public QMessage(String variable) {
        super(Message.class, forVariable(variable));
    }

    public QMessage(Path<? extends Message> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessage(PathMetadata metadata) {
        super(Message.class, metadata);
    }

}

