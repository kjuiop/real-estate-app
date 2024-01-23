package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.realestate.image.dto.ImageCreateForm;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.types.ProcessType;
import io.gig.realestate.domain.realestate.construct.dto.ConstructCreateForm;
import io.gig.realestate.domain.realestate.construct.dto.FloorCreateForm;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceCreateForm;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageCreateForm;
import io.gig.realestate.domain.realestate.landusage.dto.LandUsageUpdateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.print.dto.PrintCreateForm;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/07
 */
@Getter
@Setter
public class RealEstateUpdateForm {

    private Long realEstateId;

    private String buildingName;

    private String surroundInfo;

    private String imgUrl;

    private String address;

    private String addressDetail;

    private YnType ownExclusiveYn;

    private YnType otherExclusiveYn;

    private Long usageTypeId;

    private String managerUsername;

    private ProcessType processType;

    private YnType rYn;

    private YnType abYn;

    private String characterInfo;

    private String agentName;

    private String tradingAt;

    private PriceCreateForm priceInfo;

    private LandUsageUpdateForm landUsageInfo;

    private ConstructCreateForm constructInfo;

    private PrintCreateForm printInfo;

    private List<ImageCreateForm> subImages = new ArrayList<>();

    private List<LandInfoDto> landInfoList = new ArrayList<>();

    private List<FloorCreateForm> floorInfoList = new ArrayList<>();

    private List<CustomerCreateForm> customerInfoList = new ArrayList<>();

    private List<LandPriceCreateForm> landPriceInfoList = new ArrayList<>();
}
