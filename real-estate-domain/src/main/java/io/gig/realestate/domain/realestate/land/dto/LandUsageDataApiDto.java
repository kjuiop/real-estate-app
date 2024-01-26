package io.gig.realestate.domain.realestate.land.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2023/09/25
 */
@Getter
@SuperBuilder
public class LandUsageDataApiDto {

    private String pnu;
    private String prposAreaDstrcNmList;
    private String prposAreaDstrcCodeList;
    private String posList;
    private int responseCode;
    private LocalDateTime lastCurlApiAt;

    public static LandUsageDataApiDto convertData(int responseCode, JSONObject sop) {
        return LandUsageDataApiDto.builder()
                .pnu(sop.has("sop:pnu") ? String.valueOf(sop.getLong("sop:pnu")) : null)
                .prposAreaDstrcNmList(sop.has("sop:prpos_area_dstrc_nm_list") ? sop.getString("sop:prpos_area_dstrc_nm_list") : null)
                .prposAreaDstrcCodeList(sop.has("sop:prpos_area_dstrc_code_list") ? sop.getString("sop:prpos_area_dstrc_code_list") : null)
                .responseCode(responseCode)
                .lastCurlApiAt(LocalDateTime.now())
                .build();
    }

    private static String parsePosList(JSONObject nsdi) {
        JSONObject shape = nsdi.has("NSDI:SHAPE") ? nsdi.getJSONObject("NSDI:SHAPE") : null;
        if (shape == null) {
            return null;
        }

        JSONObject multiSurface = shape.has("gml:MultiSurface") ? shape.getJSONObject("gml:MultiSurface") : null;
        if (multiSurface == null) {
            return null;
        }

        // json object 에러남
        Object object = multiSurface.has("gml:surfaceMember") ? multiSurface.get("gml:surfaceMember") : null;
        if (object == null) {
            return null;
        }

        if (!(object instanceof JSONObject)) {
            return null;
        }

        JSONObject surfaceMember = multiSurface.has("gml:surfaceMember") ? multiSurface.getJSONObject("gml:surfaceMember") : null;
        if (surfaceMember == null) {
            return null;
        }

        JSONObject polygon = surfaceMember.has("gml:Polygon") ? surfaceMember.getJSONObject("gml:Polygon") : null;
        if (polygon == null) {
            return null;
        }

        JSONObject exterior = polygon.has("gml:exterior") ? polygon.getJSONObject("gml:exterior") : null;
        if (exterior == null) {
            return null;
        }

        JSONObject linearRing = exterior.has("gml:LinearRing") ? exterior.getJSONObject("gml:LinearRing") : null;
        if (linearRing == null) {
            return null;
        }

        String posList = linearRing.has("gml:posList") ? linearRing.getString("gml:posList") : null;
        if (!StringUtils.hasText(posList)) {
            return null;
        }
        posList = posList.replaceAll(" ", ",");
        return posList;
    }

    @Getter
    @Builder
    public static class Request {
        private final String pnu;

        public static Request assembleParam(String bCode, String landType, String bun, String ji) {

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

            return Request.builder()
                    .pnu(pnuBuilder.toString())
                    .build();
        }
    }

}
