package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import io.gig.realestate.domain.notification.dto.NotificationListDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
public interface NotificationService {

    Long create(NotificationForm form, Administrator administrator);

    Long getNotificationCntByUsername(String username);

    void sendBuyerCreateToManager(Long buyerId, String customerName, Long senderId, List<Long> managerIds);

    List<NotificationListDto> getNotificationByUsername(String username);

    void read(Long notiId, NotificationForm readForm);
}
