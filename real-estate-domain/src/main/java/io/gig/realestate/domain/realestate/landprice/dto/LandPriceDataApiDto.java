package io.gig.realestate.domain.realestate.landprice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

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

    private int pblntfPclnd1;

    private int pblntfPclnd2;

    private int pblntfPclnd3;

    private int pblntfPclnd4;

    public static LandPriceDataApiDto convertData(JSONObject nsdi) {
        return LandPriceDataApiDto.builder()
                .pnu(nsdi.optLong("NSDI:PNU"))
                .pclndStdrYear(nsdi.optInt("NSDI:PBLNTF_PCLND_STDR_YEAR"))
                .pblntfPclnd(nsdi.optInt("NSDI:PBLNTF_PCLND"))
                .pblntfPclnd1(nsdi.optInt("NSDI:PSTYR_1_PBLNTF_PCLND"))
                .pblntfPclnd2(nsdi.optInt("NSDI:PSTYR_2_PBLNTF_PCLND"))
                .pblntfPclnd3(nsdi.optInt("NSDI:PSTYR_3_PBLNTF_PCLND"))
                .pblntfPclnd4(nsdi.optInt("NSDI:PSTYR_4_PBLNTF_PCLND"))
                .build();
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
}
