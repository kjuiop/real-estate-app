package io.gig.realestate.domain.notification.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.notification.dto.NotificationListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.notification.QNotification.notification;

/**
 * @author : JAKE
 * @date : 2024/05/10
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Long getNotificationCntByUsername(String username) {
        return this.queryFactory
                .select(notification.count())
                .from(notification)
                .where(defaultCondition())
                .where(eqReceiver(username))
                .fetchOne()
                ;
    }

    private BooleanExpression defaultCondition() {
        return notification.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqReceiver(String username) {
        return notification.receiver.username.eq(username);
    }

    public List<NotificationListDto> getNotificationByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(NotificationListDto.class,
                        notification
                        ))
                .from(notification)
                .where(defaultCondition())
                .where(eqReceiver(username))
                .fetch();
    }
}
