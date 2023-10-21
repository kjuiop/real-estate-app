package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.common.BaseSearchDto;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Getter
@Setter
@NoArgsConstructor
public class RealEstateSearchDto extends BaseSearchDto {

    private ProcessType processType;

    private String name;

    private String sido;

    private String gungu;

    private String dong;

    private String landType;

    private String bun;

    private String ji;

    private String buildingName;

    private Long realEstateId;

    private String manager;

    private String team;

    private String prposArea1Nm;

    private int minSalePrice;

    private int maxSalePrice;

    private int minLndpclAr;

    private int maxLndpclAr;

    private int minTotArea;

    private int maxTotArea;

    public PageRequest getPageableWithSort() {
        return PageRequest.of(getPage(), getSize(), Sort.by(new Sort.Order(Sort.Direction.DESC, "id")));
    }

}
