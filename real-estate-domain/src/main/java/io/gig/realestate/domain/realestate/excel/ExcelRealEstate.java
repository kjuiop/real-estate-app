package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
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

    private String username;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType publishYn = YnType.N;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType completeYn = YnType.N;


    public static ExcelRealEstate excelCreate(ExcelRealEstateDto dto, String username) {
        return ExcelRealEstate.builder()
                .legalCode(dto.getLegalCode())
                .agentName(dto.getAgentName())
                .address(dto.getAddress())
                .sido(dto.getSido())
                .gungu(dto.getGungu())
                .dong(dto.getDong())
                .bunJiStr(dto.getBunJiStr())
                .bun(dto.getBun())
                .ji(dto.getJi())
                .salePrice(dto.getSalePrice())
                .username(username)
                .build();
    }

}
