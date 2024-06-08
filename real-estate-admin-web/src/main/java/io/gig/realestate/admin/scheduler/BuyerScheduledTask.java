package io.gig.realestate.admin.scheduler;

import io.gig.realestate.domain.buyer.basic.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Component
@RequiredArgsConstructor
public class BuyerScheduledTask {

    private final BuyerService buyerService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void checkBuyerLatest() {

    }
}
