package io.gig.realestate.domain.realestate.landprice.dto;

import io.gig.realestate.domain.realestate.landprice.LandPriceInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
@Getter
@Setter
public class LandPriceDto {

    private String pnu;

    private Integer pclndStdrYear;

    private int pblntfPclnd;

    private int pblntfPclndPy;

    private double changeRate;

    public LandPriceDto(LandPriceInfo l) {
        this.pnu = l.getPnu();
        this.pclndStdrYear = l.getPclndStdrYear();
        this.pblntfPclnd = l.getPblntfPclnd();
        this.pblntfPclndPy = l.getPblntfPclndPy();
        this.changeRate = l.getChangeRate();
    }
}
