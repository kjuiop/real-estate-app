package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.realestate.event.RealEstateEvent;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public void create(List<ExcelRealEstateDto> excelRealEstateList, String username) {

        List<ExcelRealEstate> data = new ArrayList<>();
        for (ExcelRealEstateDto dto : excelRealEstateList) {
            ExcelRealEstate excelRealEstate = ExcelRealEstate.excelCreate(dto, username);
            data.add(excelRealEstate);
        }

        excelRealEstateStore.storeAll(data);

        RealEstateEvent event = new RealEstateEvent(data, "excel-parser");
        eventPublisher.publishEvent(event);
    }
}
