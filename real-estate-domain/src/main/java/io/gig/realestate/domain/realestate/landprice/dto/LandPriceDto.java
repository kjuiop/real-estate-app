package io.gig.realestate.domain.realestate.landprice.dto;

import io.gig.realestate.domain.realestate.landprice.LandPriceInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
@Getter
@Setter
public class LandPriceDto {

    private Long landPriceId;

    private String pnu;

    private Integer pclndStdrYear;

    private int pblntfPclnd;

    private int pblntfPclndPy;

    private double changeRate;

    private int responseCode;

    private LocalDateTime lastCurlApiAt;

    public LandPriceDto(LandPriceInfo l) {
        this.landPriceId = l.getId();
        this.pnu = l.getPnu();
        this.pclndStdrYear = l.getPclndStdrYear();
        this.pblntfPclnd = l.getPblntfPclnd();
        this.pblntfPclndPy = l.getPblntfPclndPy();
        this.changeRate = l.getChangeRate();
        this.responseCode = l.getResponseCode();
        this.lastCurlApiAt = l.getLastCurlApiAt();
    }
}
