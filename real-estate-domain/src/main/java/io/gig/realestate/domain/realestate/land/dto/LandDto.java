package io.gig.realestate.domain.realestate.land.dto;

import io.gig.realestate.domain.realestate.land.LandInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class LandDto {

    private Long landId;

    private String pnu;

    public LandDto(LandInfo l) {
        this.landId = l.getId();
        this.pnu = l.getPnu();
    }
}
