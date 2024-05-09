package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.notification.dto.NotificationForm;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
public interface NotificationService {

    Long create(NotificationForm form, Administrator administrator);

    Long getNotificationCntByUsername(String username);
}
