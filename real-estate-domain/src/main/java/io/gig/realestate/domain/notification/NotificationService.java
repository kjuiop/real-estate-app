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

    void sendBuyerUpdateToManager(Long buyerId, String customerName, Long senderId, List<Long> managerIds);

    List<NotificationListDto> getNotificationByUsername(String username);

    Long read(Long notiId, NotificationForm readForm, Administrator administrator);

    void sendSchedulerCreateToManager(Long schedulerId, String customerName, Long senderId, List<Long> managerIds);

    void sendSchedulerUpdateToManager(Long id, String customerName, Long id1, List<Long> managerIds);
}
