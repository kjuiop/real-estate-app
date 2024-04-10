package io.gig.realestate.domain.buyer.realestate.dto;

import io.gig.realestate.domain.buyer.realestate.HistoryRealEstate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/04/10
 */
@SuperBuilder
@Getter
public class HistoryRealEstateDto {

    private Long historyRealEstateId;
    private String address;
    private double salePrice;
    private double lndpclArByPyung;
    private double totAreaByPyung;
    private double archAreaByPyung;
    private String managerName;

    public HistoryRealEstateDto(HistoryRealEstate hr) {
        this.historyRealEstateId = hr.getId();
        this.address = hr.getRealEstate().getAddress();
        this.salePrice = hr.getRealEstate().getPriceInfoList().get(0).getSalePrice();
        this.lndpclArByPyung = hr.getRealEstate().getLandInfoList().get(0).getLndpclArByPyung();
        this.totAreaByPyung = hr.getRealEstate().getConstructInfoList().get(0).getTotAreaByPyung();
        this.archAreaByPyung = hr.getRealEstate().getConstructInfoList().get(0).getArchAreaByPyung();
        this.managerName = hr.getRealEstate().getCreatedBy().getName();
    }
}
