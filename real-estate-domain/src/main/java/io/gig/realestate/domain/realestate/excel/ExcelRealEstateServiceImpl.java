package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.realestate.event.RealEstateEvent;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelRealEstateServiceImpl implements ExcelRealEstateService {

    private final ExcelRealEstateReader excelRealEstateReader;
    private final ExcelRealEstateStore excelRealEstateStore;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void createAndPublish(List<ExcelRealEstateDto> excelRealEstateList, String username) {

        List<RealEstateEvent> eventList = new ArrayList<>();
        List<ExcelRealEstate> data = new ArrayList<>();
        for (ExcelRealEstateDto dto : excelRealEstateList) {
            ExcelRealEstate excelRealEstate = ExcelRealEstate.excelCreate(dto, username);
            excelRealEstate.isPublish();
            data.add(excelRealEstate);

            RealEstateEvent event = new RealEstateEvent(excelRealEstate, "[excel-parser]-" + dto.getAddress());
            eventList.add(event);
        }

        excelRealEstateStore.storeAll(data);

        int i=0;
        for (RealEstateEvent event : eventList) {
            i++;
            event.setTimeSleep(i);
            eventPublisher.publishEvent(event);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ExcelRealEstate findById(Long id) {
        return excelRealEstateReader.findById(id);
    }
}
