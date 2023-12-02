package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public void create(List<ExcelRealEstateDto> excelRealEstateList, String username) {

        List<ExcelRealEstate> data = new ArrayList<>();
        for (ExcelRealEstateDto dto : excelRealEstateList) {
            ExcelRealEstate excelRealEstate = ExcelRealEstate.excelCreate(dto, username);
            data.add(excelRealEstate);
        }

        excelRealEstateStore.storeAll(data);
    }
}
