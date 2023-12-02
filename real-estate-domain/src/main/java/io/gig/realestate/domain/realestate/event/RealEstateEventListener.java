package io.gig.realestate.domain.realestate.event;

import io.gig.realestate.domain.realestate.basic.RealEstateService;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RealEstateEventListener implements ApplicationListener<RealEstateEvent> {

    private final RealEstateService realEstateService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(RealEstateEvent event) {
        log.info("event : " + event.getQueueName());
        ExcelRealEstate data = (ExcelRealEstate) event.getSource();
        int timeWait = 1000 + (100 * event.getTimeSleep());
        Thread.sleep(timeWait);
        realEstateService.createByExcelUpload(data);
    }
}
