package io.gig.realestate.domain.realestate.basic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String exclusiveCds;

    private String buildingTypeCds;

    private String realEstateGradeCds;

    private String usageCds;

    private String surroundInfo;

    private String imgUrl;

    private String address;

    private String addressDetail;

    private YnType ownExclusiveYn;

    private YnType otherExclusiveYn;

    private Long propertyTypeId;

    private Long usageTypeId;

    private String managerUsername;

    private ProcessType processType;

    private YnType banAdvertisingYn;

    private String characterInfo;

    private String agentName;

    private String tradingAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate acquiredAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearBuiltAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate remodelingAt;

    private Double landPriceDiff;

    private List<Long> managerIds;

    private PriceCreateForm priceInfo;

    private LandUsageCreateForm landUsageInfo;

    private ConstructCreateForm constructInfo;

    private PrintCreateForm printInfo;

    private List<ImageCreateForm> subImages = new ArrayList<>();

    private List<LandInfoDto> landInfoList = new ArrayList<>();

    private List<FloorCreateForm> floorInfoList = new ArrayList<>();

    private List<CustomerCreateForm> customerInfoList = new ArrayList<>();

    private List<LandPriceCreateForm> landPriceInfoList = new ArrayList<>();
}
