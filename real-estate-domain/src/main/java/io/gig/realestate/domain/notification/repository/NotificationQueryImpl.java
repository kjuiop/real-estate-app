package io.gig.realestate.domain.notification.repository;

import io.gig.realestate.domain.notification.NotificationReader;
import io.gig.realestate.domain.notification.dto.NotificationListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<NotificationListDto> getNotificationByUsername(String username) {
        return queryRepository.getNotificationByUsername(username);
    }
}
