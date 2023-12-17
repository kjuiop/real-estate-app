package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class RealEstateDto {

    private Long realEstateId;

    private String buildingName;

    private String surroundInfo;

    private CategoryDto usageType;

    private ProcessType processType;

    private String address;

    private String addressDetail;

    private YnType ownYn;

    private YnType ownExclusiveYn;

    private YnType otherExclusiveYn;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
        this.address = r.getAddress();
        this.addressDetail = r.getAddressDetail();
        this.ownExclusiveYn = r.getOwnExclusiveYn();
        this.otherExclusiveYn = r.getOtherExclusiveYn();
        this.imgUrl = r.getImgUrl();
        this.agentName = r.getAgentName();
        this.tradingAt = r.getTradingAt();
        this.processType = r.getProcessType();
        this.rYn = r.getRYn();
        this.abYn = r.getAbYn();
        this.createdAt = r.getCreatedAt();
        this.updatedAt = r.getUpdatedAt();
    }
}
