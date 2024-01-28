package io.gig.realestate.domain.realestate.construct;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.construct.dto.*;
import io.gig.realestate.domain.utils.CommonUtils;
import io.gig.realestate.domain.utils.properties.ConstructDataProperties;
import io.gig.realestate.domain.utils.properties.ConstructFloorDataProperties;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
@Service
@RequiredArgsConstructor
public class ConstructServiceImpl implements ConstructService {

    private final ConstructDataProperties constructDataProperties;
    private final ConstructFloorDataProperties floorDataProperties;

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    private final ConstructReader constructReader;

    @Override
    @Transactional(readOnly = true)
    public ConstructDto getConstructInfoByRealEstateId(Long realEstateId) {
        return constructReader.getConstructInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FloorListDto> getFloorInfoByRealEstateId(Long realEstateId) {
        return constructReader.getFloorInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional
    public Long create(ConstructCreateForm createForm, LoginUser loginUser) {

        return null;
    }

    @Override
    @Transactional
    public ConstructDataApiDto getConstructInfo(String bCode, String landType, String bun, String ji) throws IOException {
        ConstructDataApiDto.Request request = ConstructDataApiDto.Request.assembleParam(bCode, landType, bun, ji);
        StringBuilder urlBuilder = new StringBuilder(constructDataProperties.getUrl());
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + constructDataProperties.getServiceKey());
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
        ConstructDataApiDto dto = parseConstructJsonData(conn.getResponseCode(), convertResult);
        return dto;
    }

    private ConstructDataApiDto parseConstructJsonData(int responseCode, JSONObject data) throws JsonProcessingException {
        if (!data.has("response")) {
            return null;
        }

        JSONObject response = data.getJSONObject("response");
        if (!response.has("body")) {
            return null;
        }

        JSONObject body = response.getJSONObject("body");

        if (body.getInt("totalCount") == 0) {
            return null;
        }

        JSONObject items = body.getJSONObject("items");

        Object object = items.get("item");
        if (object == null) {
            return null;
        }

        if (object instanceof JSONObject) {
            JSONObject item = items.getJSONObject("item");
            return ConstructDataApiDto.convertData(responseCode, item);
        }

        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            JSONObject item = (JSONObject) jsonArray.get(0);
            return ConstructDataApiDto.convertData(responseCode, item);
        }
        return null;
    }

    @Override
    @Transactional
    public List<ConstructFloorDataApiDto> getConstructFloorInfo(String bCode, String landType, String bun, String ji) throws IOException {
        ConstructFloorDataApiDto.Request request = ConstructFloorDataApiDto.Request.assembleParam(bCode, landType, bun, ji);
        StringBuilder urlBuilder = new StringBuilder(floorDataProperties.getUrl());
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + floorDataProperties.getServiceKey());
        urlBuilder.append("&" + URLEncoder.encode("sigunguCd","UTF-8") + "=" + URLEncoder.encode(request.getSigunguCd(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("bjdongCd","UTF-8") + "=" + URLEncoder.encode(request.getBjdongCd(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("platGbCd","UTF-8") + "=" + URLEncoder.encode(request.getPlatGbCd(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("bun","UTF-8") + "=" + URLEncoder.encode(request.getBun(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ji","UTF-8") + "=" + URLEncoder.encode(request.getJi(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("50", "UTF-8"));
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
        List<ConstructFloorDataApiDto> dto = parseFloorJsonData(conn.getResponseCode(), convertResult);
        if (dto != null && dto.size() > 0) {
            Comparator<ConstructFloorDataApiDto> comparator = Comparator.comparingInt(ConstructFloorDataApiDto::getFlrNo).reversed();
            dto.sort(comparator);
        }
        return dto;
    }

    private List<ConstructFloorDataApiDto> parseFloorJsonData(int responseCode, JSONObject data) throws JsonProcessingException {
        List<ConstructFloorDataApiDto> list = new ArrayList<>();

        if (!data.has("response")) {
            return null;
        }
        JSONObject response = data.getJSONObject("response");

        if (!response.has("body")) {
            return null;
        }
        JSONObject body = response.getJSONObject("body");

        if (body.getInt("totalCount") == 0) {
            return null;
        }

        JSONObject items = body.getJSONObject("items");

        Object object = items.get("item");
        if (object == null) {
            return null;
        }

        if (object instanceof JSONObject) {
            // json array 또는 object
            JSONObject item = items.getJSONObject("item");
            ConstructFloorDataApiDto dto = ConstructFloorDataApiDto.convertData(responseCode, item);
            list.add(dto);

        } else if (object instanceof JSONArray) {
            // json array 또는 object
            JSONArray item = items.getJSONArray("item");
            for (Object jData : item) {
                JSONObject json = (JSONObject) jData;
                ConstructFloorDataApiDto dto = ConstructFloorDataApiDto.convertData(responseCode, json);
                list.add(dto);
            }
        }


        return list;
    }
}
