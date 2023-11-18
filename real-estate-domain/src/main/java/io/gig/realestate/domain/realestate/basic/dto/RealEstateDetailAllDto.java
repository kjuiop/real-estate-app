package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorListDto;
import io.gig.realestate.domain.realestate.land.dto.LandDto;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.dto.PriceDto;
import io.gig.realestate.domain.realestate.print.dto.PrintDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
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

    private List<FloorListDto> floorUpList;

    private List<FloorListDto> floorUnderList;

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
            List<FloorListDto> floorUpList = new ArrayList<>();
            List<FloorListDto> floorUnderList = new ArrayList<>();

            for (FloorPriceInfo floor : r.getFloorPriceInfo()) {
                if (floor.getUnderFloorYn().equals(YnType.N)) {
                    floorUpList.add(new FloorListDto(floor));
                } else {
                    floorUnderList.add(new FloorListDto(floor));
                }
            }

            this.floorUpList = floorUpList;
            this.floorUnderList = floorUnderList;
        }
    }
}
