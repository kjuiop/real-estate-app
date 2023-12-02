package io.gig.realestate.domain.realestate.excel;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.realestate.excel.dto.ExcelRealEstateDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
