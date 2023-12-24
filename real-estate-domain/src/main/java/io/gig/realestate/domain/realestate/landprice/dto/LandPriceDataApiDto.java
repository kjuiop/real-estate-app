package io.gig.realestate.domain.realestate.landprice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

    public static List<LandPriceDataApiDto> convertData(JSONObject nsdi) {

        long pnu = nsdi.optLong("NSDI:PNU");
        int pclndStdrYear = nsdi.optInt("NSDI:PBLNTF_PCLND_STDR_YEAR");

        List<LandPriceDataApiDto> result = new ArrayList<>();

        for (int i=0; i<5; i++) {
            LandPriceDataApiDto price;
            if (i == 0) {
                int pblntfPclnd = nsdi.optInt("NSDI:PBLNTF_PCLND");
                price = LandPriceDataApiDto.builder()
                        .pnu(pnu)
                        .pblntfPclnd(pblntfPclnd)
                        .pblntfPclndPy(calculatePyung(pblntfPclnd))
                        .pclndStdrYear(pclndStdrYear)
                        .build();
            } else {
                int pblntfPclnd = nsdi.optInt("NSDI:PSTYR_" + i + "_PBLNTF_PCLND");
                pclndStdrYear--;
                price = LandPriceDataApiDto.builder()
                        .pnu(pnu)
                        .pblntfPclnd(pblntfPclnd)
                        .pblntfPclndPy(calculatePyung(pblntfPclnd))
                        .pclndStdrYear(pclndStdrYear)
                        .build();
            }
            result.add(price);
        }
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
}
