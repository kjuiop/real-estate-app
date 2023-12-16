package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import io.gig.realestate.domain.realestate.excel.types.UploadStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/12/02
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExcelRealEstate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String username;

    private String skipReason;

    private String characterInfo;

    private ProcessType processType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private UploadStatus uploadStatus = UploadStatus.PENDING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType publishYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType failYn = YnType.N;

    public static ExcelRealEstate excelCreate(ExcelRealEstateDto dto, String username) {
        return ExcelRealEstate.builder()
                .uploadId(dto.getUploadId())
                .rowIndex(dto.getRowIndex())
                .legalCode(dto.getLegalCode())
                .agentName(dto.getAgentName())
                .address(dto.getAddress())
                .sido(dto.getSido())
                .gungu(dto.getGungu())
                .dong(dto.getDong())
                .bunJiGeneral(dto.getBunJiGeneral())
                .bunJiMountain(dto.getBunJiMountain())
                .bun(dto.getBun())
                .ji(dto.getJi())
                .salePrice(dto.getSalePrice())
                .username(username)
                .processType(dto.getProcessType())
                .characterInfo(dto.getCharacterInfo())
                .build();
    }

    public static ExcelRealEstate failData(ExcelRealEstateDto dto, String username) {
        return ExcelRealEstate.builder()
                .uploadId(dto.getUploadId())
                .rowIndex(dto.getRowIndex())
                .legalCode(dto.getLegalCode())
                .agentName(dto.getAgentName())
                .address(dto.getAddress())
                .sido(dto.getSido())
                .gungu(dto.getGungu())
                .dong(dto.getDong())
                .bunJiGeneral(dto.getBunJiGeneral())
                .bunJiMountain(dto.getBunJiMountain())
                .bun(dto.getBun())
                .ji(dto.getJi())
                .salePrice(dto.getSalePrice())
                .username(username)
                .failYn(YnType.Y)
                .skipReason(dto.getSkipReason())
                .uploadStatus(UploadStatus.FAIL)
                .processType(dto.getProcessType())
                .characterInfo(dto.getCharacterInfo())
                .build();
    }

    public void isPublish() {
        this.publishYn = YnType.Y;
    }

    public void isComplete() {
        this.uploadStatus = UploadStatus.COMPLETE;
    }

}
