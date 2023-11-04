package io.gig.realestate.domain.realestate.price.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Getter
@Setter
public class PriceUpdateForm {

    private Long realEstateId;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String address;

    private String imgUrl;

    private int salePrice;

    private int depositPrice;

    private int revenueRate;

    private int averageUnitPrice;

    private int guaranteePrice;

    private int rentMonth;

    private int management;

    private int managementExpense;

    private List<FloorDto> floorInfo = new ArrayList<>();


    @Getter
    @Builder
    public static class FloorDto {

        private int flrNo;

        private String flrNoNm;

        // 면적
        private Double area;

        // 주용도
        private String mainPurpsCdNm;

        // 부용도
        private String etcPurps;

        private int guaranteePrice;

        private int rent;

        private int management;

        private String companyName;

    }

}
