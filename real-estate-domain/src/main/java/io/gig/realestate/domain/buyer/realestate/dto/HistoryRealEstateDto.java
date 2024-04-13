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
    private Long realEstateId;

    public HistoryRealEstateDto(HistoryRealEstate hr) {
        this.historyRealEstateId = hr.getId();
        this.address = hr.getRealEstate().getAddress();
        if (hr.getRealEstate().getPriceInfoList().size() > 0) {
            this.salePrice = hr.getRealEstate().getPriceInfoList().get(0).getSalePrice();
        }
        if (hr.getRealEstate().getLandInfoList().size() > 0) {
            this.lndpclArByPyung = hr.getRealEstate().getLandInfoList().get(0).getLndpclArByPyung();
        }
        if (hr.getRealEstate().getConstructInfoList().size() > 0) {
            this.totAreaByPyung = hr.getRealEstate().getConstructInfoList().get(0).getTotAreaByPyung();
            this.archAreaByPyung = hr.getRealEstate().getConstructInfoList().get(0).getArchAreaByPyung();
        }
        this.managerName = hr.getRealEstate().getCreatedBy().getName();
        this.realEstateId = hr.getRealEstate().getId();
    }
}
