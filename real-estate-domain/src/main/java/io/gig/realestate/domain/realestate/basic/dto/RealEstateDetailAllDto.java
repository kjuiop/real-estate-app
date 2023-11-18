package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorListDto;
import io.gig.realestate.domain.realestate.land.dto.LandDto;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import io.gig.realestate.domain.realestate.price.dto.PriceDto;
import io.gig.realestate.domain.realestate.print.dto.PrintDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateDetailAllDto extends RealEstateDto {

    private static final RealEstateDetailAllDto EMPTY;

    private Long managerId;

    private Long managerTeamId;

    private Long createdById;

    private Long usageCdId;

    private LandDto landInfo;

    private PriceDto priceInfo;

    private ConstructDto constructInfo;

    private PrintDto printInfo;

    private List<LandListDto> landInfoList;

    private List<FloorListDto> floorInfoList;


    static {
        EMPTY = RealEstateDetailAllDto.builder()
                .build();
    }

    public static RealEstateDetailAllDto emptyDto() {
        return EMPTY;
    }

    public RealEstateDetailAllDto(RealEstate r) {
        super(r);
        if (r.getLandInfoList().size() > 0) {
            this.landInfo = new LandDto(r.getLandInfoList().get(0));
            this.landInfoList = r.getLandInfoList().stream().map(LandListDto::new).collect(Collectors.toList());
        }

        if (r.getPriceInfoList().size() > 0) {
            this.priceInfo = new PriceDto(r.getPriceInfoList().get(0));
        }

        if (r.getConstructInfoList().size() > 0) {
            this.constructInfo = new ConstructDto(r.getConstructInfoList().get(0));
        }

        if (r.getPrintInfoList().size() > 0) {
            this.printInfo = new PrintDto(r.getPrintInfoList().get(0));
        }

        if (r.getFloorPriceInfo().size() > 0) {
            this.floorInfoList = r.getFloorPriceInfo().stream().map(FloorListDto::new).collect(Collectors.toList());
        }
    }
}
