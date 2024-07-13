package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
public class RealEstateDto {

    private Long realEstateId;

    private Double landPriceDiff;
    
    private String exclusiveCds;

    private String realEstateGradeCds;

    private String buildingTypeCds;

    private String usageCds;

    private String buildingName;

    private String surroundInfo;

    private CategoryDto usageType;

    private CategoryDto propertyType;

    private ProcessType processType;

    private String address;

    private String addressDetail;

    private YnType ownYn;

    private YnType rYn;

    private YnType abYn;

    private String pnu;

    private String legalCode;

    private String landType;

    private String bun;

    private String ji;

    private String imgUrl;

    private String characterInfo;

    private String agentName;

    private String tradingAt;

    private LocalDate acquiredAt;

    private LocalDate yearBuiltAt;

    private LocalDate remodelingAt;

    private YnType banAdvertisingYn;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String changeBrightnessCds;

    private String ownBrightnessCds;

    private String brightnessPeriod;

    private String expectedBrightnessPrice;

    private String buildingImprovePoint;

    private String waterLeak;

    private String adjacentRoad;

    private String slope;

    private String landscape;

    private String restroom;

    private String heatingTypeCds;

    private String heatingCoolingTypeCds;

    private String goodNewsInfo;

    public RealEstateDto(RealEstate r) {
        this.realEstateId = r.getId();
        this.legalCode = r.getLegalCode();
        this.landType = r.getLandType();
        this.bun = r.getBun();
        this.ji = r.getJi();
        this.buildingName = r.getBuildingName();
        this.surroundInfo = r.getSurroundInfo();
        this.characterInfo = r.getCharacterInfo();
        if (r.getUsageType() != null) {
            this.usageType = new CategoryDto(r.getUsageType());
        }
        if (r.getPropertyType() != null) {
            this.propertyType = new CategoryDto(r.getPropertyType());
        }
        this.address = r.getAddress();
        this.addressDetail = r.getAddressDetail();
        this.imgUrl = r.getImgUrl();
        this.agentName = r.getAgentName();
        this.tradingAt = r.getTradingAt();
        this.acquiredAt = r.getAcquiredAt();
        this.processType = r.getProcessType();
        this.exclusiveCds = r.getExclusiveCds();
        this.realEstateGradeCds = r.getRealEstateGradeCds();
        this.buildingTypeCds = r.getBuildingTypeCds();
        this.usageCds = r.getUsageCds();
        this.banAdvertisingYn = r.getBanAdvertisingYn();
        this.landPriceDiff = r.getLandPriceDiff();
        this.createdAt = r.getCreatedAt();
        this.updatedAt = r.getUpdatedAt();
        this.yearBuiltAt = r.getYearBuiltAt();
        this.remodelingAt = r.getRemodelingAt();
        this.changeBrightnessCds = r.getChangeBrightnessCds();
        this.ownBrightnessCds = r.getOwnBrightnessCds();
        this.brightnessPeriod = r.getBrightnessPeriod();
        this.expectedBrightnessPrice = r.getExpectedBrightnessPrice();
        this.buildingImprovePoint = r.getBuildingImprovePoint();
        this.waterLeak = r.getWaterLeak();
        this.adjacentRoad = r.getAdjacentRoad();
        this.slope = r.getSlope();
        this.landscape = r.getLandscape();
        this.restroom = r.getRestroom();
        this.heatingTypeCds = r.getHeatingTypeCds();
        this.heatingCoolingTypeCds = r.getHeatingCoolingTypeCds();
        this.goodNewsInfo = r.getGoodNewsInfo();
    }
}
