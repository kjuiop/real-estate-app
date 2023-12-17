package io.gig.realestate.domain.realestate.excel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/16
 */
@Getter
@Setter
public class ExcelUploadDto {

    private int timeout;

    private List<ExcelRealEstateDto> estateList;

    public ExcelUploadDto(List<ExcelRealEstateDto> estateList, int timeout) {
        this.estateList = estateList;
        this.timeout = timeout;
    }
}
