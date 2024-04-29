package com.community.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QapprovedIp is a Querydsl query type for approvedIp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QapprovedIp extends EntityPathBase<approvedIp> {

    private static final long serialVersionUID = -306204055L;

    public static final QapprovedIp approvedIp = new QapprovedIp("approvedIp");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public QapprovedIp(String variable) {
        super(approvedIp.class, forVariable(variable));
    }

    public QapprovedIp(Path<? extends approvedIp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QapprovedIp(PathMetadata metadata) {
        super(approvedIp.class, metadata);
    }

}

