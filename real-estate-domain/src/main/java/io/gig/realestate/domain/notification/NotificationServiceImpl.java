package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    @Transactional
    public Long create(NotificationForm form, Administrator administrator) {
        return null;
    }
}
