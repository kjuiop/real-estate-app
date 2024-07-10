package io.gig.realestate.domain.realestate.manager.dto;

import io.gig.realestate.domain.realestate.manager.RealEstateManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2024/04/06
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class RealEstateManagerDto {

    private Long realEstateManagerId;

    private String username;

    private String name;

    private Long adminId;

    public RealEstateManagerDto(RealEstateManager r) {
        this.realEstateManagerId = r.getId();
        this.username = r.getUsername();
        this.name = r.getName();
        this.adminId = r.getAdmin().getId();
    }
}
