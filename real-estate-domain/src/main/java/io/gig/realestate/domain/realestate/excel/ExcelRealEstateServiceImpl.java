package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.event.RealEstateEvent;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Service
@RequiredArgsConstructor
public class ExcelRealEstateServiceImpl implements ExcelRealEstateService {

    private final ExcelRealEstateStore excelRealEstateStore;
    private final ApplicationEventPublisher eventPublisher;

    @SneakyThrows
    @Override
    public void create(List<ExcelRealEstateDto> excelRealEstateList, String username) {

        List<RealEstateEvent> eventList = new ArrayList<>();
        List<ExcelRealEstate> data = new ArrayList<>();
        for (ExcelRealEstateDto dto : excelRealEstateList) {
            ExcelRealEstate excelRealEstate = ExcelRealEstate.excelCreate(dto, username);
            data.add(excelRealEstate);

            RealEstateEvent event = new RealEstateEvent(excelRealEstate, "[excel-parser]-" + dto.getAddress());
            eventList.add(event);
        }

        excelRealEstateStore.storeAll(data);

        for (RealEstateEvent event : eventList) {
            Thread.sleep(1000);
            eventPublisher.publishEvent(event);
        }
    }
}
