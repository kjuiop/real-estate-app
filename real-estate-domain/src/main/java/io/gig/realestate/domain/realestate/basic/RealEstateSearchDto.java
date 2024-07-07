package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.common.BaseSearchDto;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Getter
@Setter
@NoArgsConstructor
public class RealEstateSearchDto extends BaseSearchDto {

    private ProcessType processType;

    private List<ProcessType> processTypeCds = new ArrayList<>();

    private String address;

    private String name;

    private String sido;

    private String gungu;

    private String dong;

    private String landType;

    private String bun;

    private String ji;

    private String buildingName;

    private Long realEstateId;

    private String usageCds;

    private String manager;

    private String customer;

    private String phone;

    private String team;

    private String prposArea1Nm;

    private Integer minSalePrice;

    private Integer maxSalePrice;

    private Integer minDepositPrice;

    private Integer maxDepositPrice;

    private Integer minGuaranteePrice;

    private Integer maxGuaranteePrice;

    private Integer minRentPrice;

    private Integer maxRentPrice;

    private Integer minLndpclArByPyung;

    private Integer maxLndpclArByPyung;

    private Integer minLndpclAr;

    private Integer maxLndpclAr;

    private Integer minTotArea;

    private Integer maxTotArea;

    private Integer minTotAreaByPyung;

    private Integer maxTotAreaByPyung;

    private Integer minArchArea;

    private Integer maxArchArea;

    private Integer minArchAreaByPyung;

    private Integer maxArchAreaByPyung;

    private Integer minRevenueRate;

    private Integer maxRevenueRate;

    private Integer startUseAprDay;

    private Integer endUseAprDay;

    private Integer minRoadWidth;

    private Integer maxRoadWidth;

    private YnType rYn;

    private YnType abYn;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }

}
