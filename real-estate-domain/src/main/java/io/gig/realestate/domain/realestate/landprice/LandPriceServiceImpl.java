package io.gig.realestate.domain.realestate.landprice;

import io.gig.realestate.domain.realestate.landprice.dto.LandPriceDataApiDto;
import io.gig.realestate.domain.utils.CommonUtils;
import io.gig.realestate.domain.utils.properties.LandPriceDataProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LandPriceServiceImpl implements LandPriceService {

    private final LandPriceDataProperties dataProperties;

    @Override
    @Transactional(readOnly = true)
    public List<LandPriceDataApiDto> getLandPriceListInfo(String bCode, String landType, String bun, String ji) throws IOException {

        LandPriceDataApiDto.Request request = LandPriceDataApiDto.Request.assembleParam(bCode, landType, bun, ji);
        StringBuilder urlBuilder = new StringBuilder(dataProperties.getUrl()); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + dataProperties.getServiceKey()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pnu","UTF-8") + "=" + URLEncoder.encode(request.getPnu(), "UTF-8")); /*각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        log.debug(sb.toString());

        JSONObject convertResult = CommonUtils.convertXmlToJson(sb.toString());
        return parseLandPriceInfoJsonData(convertResult);
    }

    private List<LandPriceDataApiDto> parseLandPriceInfoJsonData(JSONObject data) {
        if (!data.has("wfs:FeatureCollection")) {
            return null;
        }

        JSONObject wfs = data.getJSONObject("wfs:FeatureCollection");

        if (!wfs.has("gml:featureMember")) {
            return null;
        }

        Object object = wfs.get("gml:featureMember");
        if (object == null) {
            return null;
        }

        JSONObject gml = (JSONObject) object;
        JSONObject nsdi = gml.getJSONObject("NSDI:F166");
        return LandPriceDataApiDto.convertData(nsdi);
    }
}
