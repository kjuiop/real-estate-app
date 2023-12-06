package io.gig.realestate.domain.realestate.excel.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/06
 */
@Getter
@Setter
@Builder
public class ExcelUploadCheckDto {

    private YnType completeYn;

    private List<ExcelRealEstateDto> data;

    public static ExcelUploadCheckDto uploadCheck(YnType completeYn, List<ExcelRealEstateDto> data) {
        return ExcelUploadCheckDto.builder()
                .completeYn(completeYn)
                .data(data)
                .build();
    }
}
