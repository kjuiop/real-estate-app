package io.gig.realestate.domain.realestate.event;

import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Slf4j
@Component
public class RealEstateEventListener implements ApplicationListener<RealEstateEvent> {

    @Override
    public void onApplicationEvent(RealEstateEvent event) {
        log.info("event : " + event.getQueueName());
        List<ExcelRealEstate> list = (List<ExcelRealEstate>) event.getSource();
        if (list.size() == 0) {
            return;
        }

        for (ExcelRealEstate excelRealEstate : list) {
            log.info("address : " + excelRealEstate.getAddress());
        }
    }
}
