package io.gig.realestate.domain.realestate.construct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
import io.gig.realestate.domain.utils.CommonUtils;
import io.gig.realestate.domain.utils.properties.ConstructDataProperties;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
@Service
@RequiredArgsConstructor
public class ConstructServiceImpl implements ConstructService {

    private final ConstructDataProperties properties;

    @Override
    @Transactional
    public ConstructDataApiDto getConstructInfo(String bCode, String landType, String bun, String ji) throws IOException {
        ConstructDataApiDto.Request request = ConstructDataApiDto.Request.assembleParam(bCode, landType, bun, ji);
        StringBuilder urlBuilder = new StringBuilder(properties.getUrl());
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + properties.getServiceKey());
        urlBuilder.append("&" + URLEncoder.encode("sigunguCd","UTF-8") + "=" + URLEncoder.encode(request.getSigunguCd(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("bjdongCd","UTF-8") + "=" + URLEncoder.encode(request.getBjdongCd(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("platGbCd","UTF-8") + "=" + URLEncoder.encode(request.getPlatGbCd(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("bun","UTF-8") + "=" + URLEncoder.encode(request.getBun(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ji","UTF-8") + "=" + URLEncoder.encode(request.getJi(), "UTF-8"));
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

        JSONObject convertResult = CommonUtils.convertXmlToJson(sb.toString());
        ConstructDataApiDto dto = parseJsonData(convertResult);
        return dto;
    }

    private ConstructDataApiDto parseJsonData(JSONObject data) throws JsonProcessingException {
        JSONObject response = data.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONObject item = items.getJSONObject("item");


        return ConstructDataApiDto.convertData(item);
    }
}
