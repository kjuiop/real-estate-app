package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
public interface ExcelRealEstateService {
    void createAndPublish(List<ExcelRealEstateDto> excelRealEstateList, String username);

    void createData(ExcelRealEstate data);
}
