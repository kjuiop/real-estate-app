package io.gig.realestate.domain.notification;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.message.basic.dto.MessageForm;
import io.gig.realestate.domain.message.basic.dto.MessageListDto;
import io.gig.realestate.domain.notification.dto.NotificationForm;
import io.gig.realestate.domain.notification.dto.NotificationListDto;
import io.gig.realestate.domain.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : JAKE
 * @date : 2024/05/06
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationReader notificationReader;
    private final NotificationStore notificationStore;
    private final AdministratorService administratorService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public Long getNotificationCntByUsername(String username) {
        return notificationReader.getNotificationCntByUsername(username);
    }

    @Override
    @Transactional
    public Long create(NotificationForm form, Administrator administrator) {
        Notification notification = Notification.create(form, administrator);
        return notificationStore.store(notification).getId();
    }

    @Override
    @Transactional
    public void sendBuyerCreateToManager(Long buyerId, String customerName, Long senderId, List<Long> managerIds) {
        List<NotificationEvent> eventList = new ArrayList<>();
        Administrator sender = administratorService.getAdminById(senderId);
        for (Long adminId : managerIds) {
            Administrator receiver = administratorService.getAdminById(adminId);
            String msg = Objects.equals(sender.getId(), receiver.getId()) ? customerName + " 정보를 생성하였습니다." : sender.getName() + "님이 " + customerName + " 정보를 생성하였습니다.";
            String returnUrl = "/buyer/" + buyerId + "/edit";
            Notification notification = Notification.sendBuyerCreateOrUpdateManager(
                    msg,
                    returnUrl,
                    sender,
                    receiver
            );
            Notification saved = notificationStore.store(notification);
            MessageForm form = MessageForm.sendMsgByNotification(saved.getId(), msg, returnUrl, sender.getUsername(), receiver.getUsername());
            eventList.add(new NotificationEvent(form, "[send-notification-slack]-" + saved.getId()));
        }

        for (NotificationEvent event : eventList) {
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    @Transactional
    public void sendBuyerUpdateToManager(Long buyerId, String customerName, Long senderId, List<Long> managerIds) {
        List<NotificationEvent> eventList = new ArrayList<>();
        Administrator sender = administratorService.getAdminById(senderId);
        for (Long adminId : managerIds) {
            Administrator receiver = administratorService.getAdminById(adminId);
            String msg = Objects.equals(sender.getId(), receiver.getId()) ? customerName + " 정보를 수정하였습니다." : sender.getName() + "님이 " + customerName + " 정보를 수정하였습니다.";
            String returnUrl = "/buyer/" + buyerId + "/edit";
            Notification notification = Notification.sendBuyerCreateOrUpdateManager(
                    msg,
                    returnUrl,
                    sender,
                    receiver
            );
            Notification saved = notificationStore.store(notification);
            MessageForm form = MessageForm.sendMsgByNotification(saved.getId(), msg, returnUrl, sender.getUsername(), receiver.getUsername());
            eventList.add(new NotificationEvent(form, "[send-notification-slack]-" + saved.getId()));
        }

        for (NotificationEvent event : eventList) {
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    @Transactional
    public Long read(Long notiId, NotificationForm readForm, Administrator loginUser) {
        Notification notification = notificationReader.getNotificationById(notiId);
        notification.read(readForm.getReadYn());
        return getNotificationCntByUsername(loginUser.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationListDto> getNotificationByUsername(String username) {
        return notificationReader.getNotificationByUsername(username);
    }
}
