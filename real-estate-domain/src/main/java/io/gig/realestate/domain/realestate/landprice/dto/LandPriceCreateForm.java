package io.gig.realestate.domain.realestate.landprice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/12/25
 */
@Getter
@Setter
public class LandPriceCreateForm {

    private Long landPriceId;

    private String pnu;

    private int pclndStdrYear;

    private int pblntfPclnd;

    private int pblntfPclndPy;

    private double changeRate;

    private int responseCode;

    private LocalDateTime lastCurlApiAt;
}
