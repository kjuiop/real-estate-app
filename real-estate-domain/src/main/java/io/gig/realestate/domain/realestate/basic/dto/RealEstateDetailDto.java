package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateDetailDto extends RealEstateDto {

    private static final RealEstateDetailDto EMPTY;

    private Long managerId;
    private Long createdById;

    @Builder.Default
    private boolean isOwnUser = false;

    @Builder.Default
    private boolean empty = false;

    @Builder.Default
    private boolean existLandInfo = false;

    @Builder.Default
    private boolean existPriceInfo = false;

    @Builder.Default
    private boolean existConstructInfo = false;

    @Builder.Default
    private boolean existCustomerInfo = false;

    static {
        EMPTY = RealEstateDetailDto.builder()
                .ownYn(YnType.Y)
                .empty(true)
                .build();
    }

    public static RealEstateDetailDto emptyDto() {
        return EMPTY;
    }

    public RealEstateDetailDto(RealEstate r) {
        super(r);
        if (r.getLandInfoList().size() > 0) {
            this.existLandInfo = true;
        }

        if (r.getPriceInfoList().size() > 0) {
            this.existPriceInfo = true;
        }

        if (r.getConstructInfoList().size() > 0) {
            this.existConstructInfo = true;
        }

        if (r.getCustomerInfoList().size() > 0) {
            this.existCustomerInfo = true;
        }

        if (r.getManager() != null) {
            this.managerId = r.getManager().getId();
        }

        if (r.getCreatedBy() != null) {
            this.createdById = r.getCreatedBy().getId();
        }
    }

    public static RealEstateDetailDto initDetailDto(String legalCode, String landType, String bun, String ji, String address) {
        return RealEstateDetailDto.builder()
                .legalCode(legalCode)
                .landType(landType)
                .bun(bun)
                .ji(ji)
                .address(address)
                .ownYn(YnType.Y)
                .build();
    }

    public void checkIsOwnUser(Long loginUserId) {

        if (this.createdById != null && this.createdById.equals(loginUserId)) {
            this.isOwnUser = true;
            return;
        }

        if (this.managerId != null && this.managerId.equals(loginUserId)) {
            this.isOwnUser = true;
        }

        // 팀장

        // 슈퍼관리자
    }
}
