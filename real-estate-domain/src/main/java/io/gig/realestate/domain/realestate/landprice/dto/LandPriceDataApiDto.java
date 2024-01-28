package io.gig.realestate.domain.realestate.landprice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
@Getter
@SuperBuilder
public class LandPriceDataApiDto {

    private Long pnu;

    private Integer pclndStdrYear;

    private int pblntfPclnd;

    private int pblntfPclndPy;

    private double changeRate;

    private int responseCode;

    private LocalDateTime lastCurlApiAt;

    public static List<LandPriceDataApiDto> convertData(int responseCode, JSONObject sop) {

        long pnu = sop.optLong("sop:pnu");
        int pclndStdrYear = sop.optInt("sop:pblntf_pclnd_stdr_year");

        List<LandPriceDataApiDto> result = new ArrayList<>();

        int prevPrice = 0;
        for (int i=4; i>=0; i--) {
            LandPriceDataApiDto price;
            if (i == 0) {
                int pblntfPclnd = sop.optInt("sop:pblntf_pclnd");
                price = LandPriceDataApiDto.builder()
                        .pnu(pnu)
                        .pblntfPclnd(pblntfPclnd)
                        .pblntfPclndPy(calculatePyung(pblntfPclnd))
                        .pclndStdrYear(pclndStdrYear)
                        .changeRate(calculateChangeRate(pblntfPclnd, prevPrice))
                        .responseCode(responseCode)
                        .lastCurlApiAt(LocalDateTime.now())
                        .build();
            } else {
                int pblntfPclnd = sop.optInt("sop:pstyr_" + i + "_pblntf_pclnd");
                int year = pclndStdrYear - i;

                price = LandPriceDataApiDto.builder()
                        .pnu(pnu)
                        .pblntfPclnd(pblntfPclnd)
                        .pblntfPclndPy(calculatePyung(pblntfPclnd))
                        .pclndStdrYear(year)
                        .changeRate(calculateChangeRate(pblntfPclnd, prevPrice))
                        .responseCode(responseCode)
                        .lastCurlApiAt(LocalDateTime.now())
                        .build();

                prevPrice = pblntfPclnd;
            }
            result.add(price);
        }
        Collections.reverse(result);
        return result;
    }

    @Getter
    @Builder
    public static class Request {
        private final String pnu;

        public static LandPriceDataApiDto.Request assembleParam(String bCode, String landType, String bun, String ji) {

            String landCode = "1";
            if (landType.equals("mountain")) {
                landCode = "2";
            }

            if (!StringUtils.hasText(ji)) {
                ji = "0";
            }
            String bunCode = String.format("%04d", Integer.parseInt(bun));
            String jiCode = String.format("%04d", Integer.parseInt(ji));

            StringBuilder pnuBuilder = new StringBuilder();
            pnuBuilder.append(bCode);
            pnuBuilder.append(landCode);
            pnuBuilder.append(bunCode);
            pnuBuilder.append(jiCode);

            return LandPriceDataApiDto.Request.builder()
                    .pnu(pnuBuilder.toString())
                    .build();
        }
    }

    private static int calculatePyung(int area) {
        if (area == 0) {
            return 0;
        }

        double price = area * 3.305785;
        BigDecimal bigDecimal = new BigDecimal(price);
        BigDecimal rounded = bigDecimal.setScale(-4, RoundingMode.HALF_UP);
        return rounded.intValue();
    }

    private static double calculateChangeRate(double currentYearValue, double previousYearValue) {
        if (previousYearValue == 0) {
            return 0.0;
        }

        double changeRate = ((currentYearValue - previousYearValue) / previousYearValue) * 100;
        changeRate = Math.round(changeRate * 10.0) / 10.0;
        return changeRate;
    }
}
