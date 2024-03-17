package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.print.dto.PrintDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    private Long managerTeamId;

    private Long createdById;

    private Long usageCdId;

    private Long propertyCdId;

    private Long prevId;

    private Long nextId;

    private PrintDto printInfo;

    @Builder.Default
    private boolean isOwnUser = false;

    @Builder.Default
    private boolean isSuperAdmin = false;

    @Builder.Default
    private boolean empty = false;

    @Builder.Default
    private boolean existLandInfo = false;

    @Builder.Default
    private boolean existLandPriceInfo = false;

    @Builder.Default
    private boolean existPriceInfo = false;

    @Builder.Default
    private boolean existFloorInfo = false;

    @Builder.Default
    private boolean existConstructInfo = false;

    @Builder.Default
    private boolean existCustomerInfo = false;

    static {
        EMPTY = RealEstateDetailDto.builder()
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
        if (r.getLandPriceInfoList().size() > 0) {
            this.existLandPriceInfo = true;
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

        if (r.getFloorPriceInfo().size() > 0) {
            this.existFloorInfo = true;
        }

        if (r.getPropertyType() != null) {
            this.propertyCdId = r.getPropertyType().getId();
        }

        if (r.getManager() != null) {
            this.managerId = r.getManager().getId();
            this.managerTeamId = r.getManager().getTeam().getId();
        }

        if (r.getCreatedBy() != null) {
            this.createdById = r.getCreatedBy().getId();
        }

        if (r.getPrintInfoList().size() > 0) {
            this.printInfo = new PrintDto(r.getPrintInfoList().get(0));
        }
    }

    public static RealEstateDetailDto initDetailDto(String legalCode, String landType, String bun, String ji, String address, Long usageCdId, String dongCode) {

        if (!StringUtils.hasText(legalCode)) {
            legalCode = dongCode;
        }

        return RealEstateDetailDto.builder()
                .legalCode(legalCode)
                .landType(landType)
                .bun(bun)
                .ji(ji)
                .address(address)
                .usageCdId(usageCdId)
                .exclusiveCds("CD_EXCLUSIVE_01")
                .build();
    }

    public void checkIsOwnUser(LoginUser loginUser) {
        if (loginUser.getLoginUser() == null) {
            return;
        }

        // 담당자
        if (this.managerId != null && this.managerId.equals(loginUser.getLoginUser().getId())) {
            this.isOwnUser = true;
            return;
        }

        Collection<GrantedAuthority> roles = loginUser.getAuthorities();
        for (GrantedAuthority role : roles) {

            if (StringUtils.hasText(role.getAuthority())
                    && role.getAuthority().equals("ROLE_MANAGER")
                    && this.managerTeamId != null
                    && this.managerTeamId.equals(loginUser.getLoginUser().getTeam().getId())
            ) {
                this.isOwnUser = true;
                return;
            }

            if (StringUtils.hasText(role.getAuthority()) && role.getAuthority().equals("ROLE_SUPER_ADMIN")) {
                this.isOwnUser = true;
                return;
            }
        }
    }

    public void checkIsSuperAdmin(LoginUser loginUser) {
        if (loginUser.getLoginUser() == null) {
            return;
        }

        Collection<GrantedAuthority> roles = loginUser.getAuthorities();
        for (GrantedAuthority role : roles) {

            if (StringUtils.hasText(role.getAuthority()) && role.getAuthority().equals("ROLE_SUPER_ADMIN")) {
                this.isSuperAdmin = true;
                return;
            }
        }
    }

    public void setPrevId(Long prevId) {
        this.prevId = prevId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }
}
