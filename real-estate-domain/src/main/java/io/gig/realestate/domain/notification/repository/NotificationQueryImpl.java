package io.gig.realestate.domain.notification.repository;

import io.gig.realestate.domain.notification.NotificationReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/05/10
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationQueryImpl implements NotificationReader {

    private final NotificationQueryRepository queryRepository;

    @Override
    public Long getNotificationCntByUsername(String username) {
        return queryRepository.getNotificationCntByUsername(username);
    }
}
