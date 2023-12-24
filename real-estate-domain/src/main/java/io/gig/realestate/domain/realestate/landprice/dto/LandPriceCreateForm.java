package io.gig.realestate.domain.realestate.landprice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2023/12/25
 */
@Getter
@Setter
public class LandPriceCreateForm {

    private String pnu;

    private int pclndStdrYear;

    private int pblntfPclnd;

    private int pblntfPclndPy;

    private double changeRate;
}
