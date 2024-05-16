package io.gig.realestate.admin;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.notification.NotificationService;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@ContextConfiguration(classes = TestConfiguration.class)
@SpringBootTest
public class NotificationTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdministratorService administratorService;

    @Test
    void createNotification() {
        NotificationForm form = NotificationForm
                .builder()
                .message("test1")
                .returnUrl("http://localhost:8080/buyer")
                .build();

        Administrator admin = administratorService.getAdminById(1L);

        notificationService.create(form, admin);

    }
}
