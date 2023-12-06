package io.gig.realestate.domain.realestate.excel.dto;

import io.gig.realestate.domain.common.YnType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Getter
@Setter
@Builder
public class ExcelRealEstateDto {

    private Long id;

    private int rowIndex;

    private String agentName;

    private String sido;

    private String gungu;

    private String dong;

    private String bunJiStr;

    private String bun;

    private String ji;

    private double salePrice;

    private String address;

    private String legalCode;

    private YnType completeYn;

    private YnType failYn;

    private String skipReason;

    private String uploadId;

    public static ExcelRealEstateDto excelCreate(String uploadId, int rowIndex, String legalCode, String agentName, String address, String sido, String gungu, String dong,
                              String bunJiStr, String bun, String ji, double salePrice) {

        return ExcelRealEstateDto.builder()
                .uploadId(uploadId)
                .rowIndex(rowIndex)
                .legalCode(legalCode)
                .agentName(agentName)
                .address(address)
                .sido(sido)
                .gungu(gungu)
                .dong(dong)
                .bunJiStr(bunJiStr)
                .bun(bun)
                .ji(ji)
                .salePrice(salePrice)
                .completeYn(YnType.N)
                .build();
    }

    public static ExcelRealEstateDto excelFailResponse(String uploadId, int rowIndex, String address, String skipReason) {
        return ExcelRealEstateDto.builder()
                .uploadId(uploadId)
                .rowIndex(rowIndex)
                .address(address)
                .completeYn(YnType.N)
                .skipReason(skipReason)
                .failYn(YnType.Y)
                .build();
    }
}
