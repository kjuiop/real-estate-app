package io.gig.realestate.domain.realestate.excel.dto;

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

    public static ExcelRealEstateDto excelCreate(String legalCode, String agentName, String address, String sido, String gungu, String dong,
                              String bunJiStr, String bun, String ji, double salePrice) {

        return ExcelRealEstateDto.builder()
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
                .build();
    }
}
