package com.community.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPointHistory is a Querydsl query type for PointHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointHistory extends EntityPathBase<PointHistory> {

    private static final long serialVersionUID = -208023953L;

    public static final QPointHistory pointHistory = new QPointHistory("pointHistory");

    public final com.community.api.model.base.QBaseTime _super = new com.community.api.model.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDt = _super.createdDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final NumberPath<Double> point = createNumber("point", Double.class);

    public final NumberPath<Long> riderId = createNumber("riderId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDt = _super.updatedDt;

    public QPointHistory(String variable) {
        super(PointHistory.class, forVariable(variable));
    }

    public QPointHistory(Path<? extends PointHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPointHistory(PathMetadata metadata) {
        super(PointHistory.class, metadata);
    }

}

