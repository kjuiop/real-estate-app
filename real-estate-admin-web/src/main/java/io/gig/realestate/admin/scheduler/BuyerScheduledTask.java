package io.gig.realestate.admin.scheduler;

import io.gig.realestate.domain.buyer.basic.BuyerService;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.event.BuyerOverDueEvent;
import io.gig.realestate.domain.buyer.manager.dto.BuyerManagerDto;
import io.gig.realestate.domain.message.basic.dto.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Component
@RequiredArgsConstructor
public class BuyerScheduledTask {

    private final BuyerService buyerService;

    private final ApplicationEventPublisher eventPublisher;

    //    @Scheduled(cron = "*/40 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void checkBuyerLatest() {
        List<BuyerOverDueEvent> eventList = new ArrayList<>();
        List<BuyerDetailDto> list = buyerService.getBuyerProcessingList();
        for (BuyerDetailDto dto : list) {
            String msg = "매수자 정보가 생성된지 2주가 경과되었습니다.";
            String returnUrl = "/buyer/" + dto.getBuyerId() + "/edit";
            for (BuyerManagerDto manager : dto.getManagers()) {
                MessageForm form = MessageForm.sendMsgByNotification(dto.getBuyerId(), msg, returnUrl, "system", manager.getUsername());
                eventList.add(new BuyerOverDueEvent(form, "[send-buyer-overdue-system]-" + dto.getBuyerId()));
            }
        }

        for (BuyerOverDueEvent event : eventList) {
            eventPublisher.publishEvent(event);
        }
    }
}
