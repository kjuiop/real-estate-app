package io.gig.realestate.domain.realestate.excel.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.excel.ExcelRealEstate;
import io.gig.realestate.domain.realestate.excel.types.UploadStatus;
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

    private String uploadId;

    private int timeoutLimit;

    private int rowIndex;

    private String agentName;

    private String sido;

    private String gungu;

    private String dong;

    private String bunJiGeneral;

    private String bunJiMountain;

    private String bun;

    private String ji;

    private double salePrice;

    private String address;

    private String legalCode;

    private YnType completeYn;

    @Builder.Default
    private YnType failYn = YnType.N;

    private String skipReason;

    private String uploadStatus;

    private ProcessType processType;

    private String characterInfo;

    public static ExcelRealEstateDto entityToDto(ExcelRealEstate data) {
        return ExcelRealEstateDto.builder()
                .id(data.getId())
                .uploadId(data.getUploadId())
                .timeoutLimit(data.getTimeoutLimit())
                .rowIndex(data.getRowIndex())
                .agentName(data.getAgentName())
                .sido(data.getSido())
                .gungu(data.getGungu())
                .dong(data.getDong())
                .bunJiGeneral(data.getBunJiGeneral())
                .bunJiMountain(data.getBunJiMountain())
                .bun(data.getBun())
                .ji(data.getJi())
                .salePrice(data.getSalePrice())
                .address(data.getAddress())
                .legalCode(data.getLegalCode())
                .skipReason(data.getSkipReason())
                .uploadStatus((data.getUploadStatus().getDescription()))
                .build();
    }

    public static ExcelRealEstateDto excelCreate(String uploadId, int timeoutLimit, int rowIndex, String legalCode, String agentName, String address, String sido, String gungu, String dong,
                              String bunJiGeneral, String bunJiMountain, String bun, String ji, double salePrice, String processValue, String characterInfo) {

        return ExcelRealEstateDto.builder()
                .uploadId(uploadId)
                .timeoutLimit(timeoutLimit)
                .rowIndex(rowIndex)
                .legalCode(legalCode)
                .agentName(agentName)
                .address(address)
                .sido(sido)
                .gungu(gungu)
                .dong(dong)
                .bunJiGeneral(bunJiGeneral)
                .bunJiMountain(bunJiMountain)
                .bun(bun)
                .ji(ji)
                .salePrice(salePrice)
                .completeYn(YnType.N)
                .uploadStatus(UploadStatus.PENDING.getDescription())
                .processType(ProcessType.convertStringValue(processValue))
                .characterInfo(characterInfo)
                .build();
    }

    public static ExcelRealEstateDto excelFailResponse(String uploadId, int timeoutLimit, int rowIndex, String address, String skipReason) {
        return ExcelRealEstateDto.builder()
                .uploadId(uploadId)
                .timeoutLimit(timeoutLimit)
                .rowIndex(rowIndex)
                .address(address)
                .completeYn(YnType.N)
                .skipReason(skipReason)
                .failYn(YnType.Y)
                .uploadStatus(UploadStatus.PENDING.getDescription())
                .build();
    }
}
