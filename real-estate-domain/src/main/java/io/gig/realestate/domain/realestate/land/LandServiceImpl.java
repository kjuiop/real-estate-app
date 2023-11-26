package io.gig.realestate.domain.realestate.land;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.land.dto.*;
import io.gig.realestate.domain.utils.CommonUtils;
import io.gig.realestate.domain.utils.properties.LandDataProperties;
import io.gig.realestate.domain.utils.properties.LandUsageDataProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandDataProperties landDataProperties;
    private final LandUsageDataProperties landUsageDataProperties;
    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    private final LandReader landReader;

    @Override
    @Transactional(readOnly = true)
    public List<LandListDto> getLandListInfoByRealEstateId(Long realEstateId) {
        return landReader.getLandInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional
    public Long create(LandCreateForm createForm, LoginUser loginUser) {

        RealEstate realEstate;
        if (createForm.getRealEstateId() == null) {
            realEstate = RealEstate.initialInfo(createForm.getLegalCode(), createForm.getAddress(), createForm.getLandType(), createForm.getBun(), createForm.getJi());
        } else {
            realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
        }

        realEstate.getLandInfoList().clear();
        for (LandCreateForm.LandInfoDto dto : createForm.getLandInfoList()) {
//            LandInfo landInfo = LandInfo.create(createForm.getAddress(), createForm.getCommercialYn(), dto, realEstate);
//            realEstate.addLandInfo(landInfo);
        }

        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long update(LandCreateForm createForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
        realEstate.getLandInfoList().clear();
        for (LandCreateForm.LandInfoDto dto : createForm.getLandInfoList()) {
//            LandInfo landInfo = LandInfo.create(createForm.getAddress(), createForm.getCommercialYn(), dto, realEstate);
//            realEstate.addLandInfo(landInfo);
        }

        return realEstateStore.store(realEstate).getId();
    }

    @Override
    public List<LandDataApiDto> getLandListInfo(String bCode, String landType, String bun, String ji) throws IOException {
        List<LandDataApiDto> list = callLandListInfo(bCode, landType, bun, ji);
        if (list.size() == 0) {
            return list;
        }

        LandUsageDataApiDto usageData = getLandUsagePublicData(bCode, landType, bun, ji);
        if (usageData != null) {
            list.get(0).withUsageData(usageData);
        }
        return list;
    }

    @Transactional
    public List<LandDataApiDto> callLandListInfo(String bCode, String landType, String bun, String ji) throws IOException {
        LandDataApiDto.Request request = LandDataApiDto.Request.assembleParam(bCode, landType, bun, ji);
        StringBuilder urlBuilder = new StringBuilder(landDataProperties.getUrl()); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + landDataProperties.getServiceKey()); /*Service Key*/
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
        List<LandDataApiDto> landDataApiDtoList = parseLandInfoJsonData(convertResult);
        return landDataApiDtoList;
    }


    private List<LandDataApiDto> parseLandInfoJsonData(JSONObject data) throws JsonProcessingException {
        JSONObject wfs = data.getJSONObject("wfs:FeatureCollection");

        if (!wfs.has("gml:featureMember")) {
            return null;
        }

        Object object = wfs.get("gml:featureMember");
        if (object == null) {
            return null;
        }

        List<LandDataApiDto> result = new ArrayList<>();

        if (object instanceof JSONObject) {
            JSONObject gml = (JSONObject) object;
            JSONObject nsdi = gml.getJSONObject("NSDI:F251");
            LandDataApiDto dataApiDto = LandDataApiDto.convertData(nsdi);
            result.add(dataApiDto);
            return result;
        }

        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject gml = jsonArray.getJSONObject(i);
                JSONObject nsdi = gml.getJSONObject("NSDI:F251");
                LandDataApiDto dataApiDto = LandDataApiDto.convertData(nsdi);
                result.add(dataApiDto);
            }
            return result;
        }

        return null;
    }


    @Override
    @Transactional
    public LandUsageDataApiDto getLandUsagePublicData(String legalCode, String landType, String bun, String ji) throws IOException {
        LandUsageDataApiDto.Request request = LandUsageDataApiDto.Request.assembleParam(legalCode, landType, bun, ji);
        StringBuilder urlBuilder = new StringBuilder(landUsageDataProperties.getUrl()); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + landUsageDataProperties.getServiceKey()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pnu","UTF-8") + "=" + URLEncoder.encode(request.getPnu(), "UTF-8")); /*각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호*/
        urlBuilder.append("&" + URLEncoder.encode("typeName","UTF-8") + "=" + URLEncoder.encode("F176", "UTF-8")); /*각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호*/
        urlBuilder.append("&" + URLEncoder.encode("srsName","UTF-8") + "=" + URLEncoder.encode("EPSG:5174", "UTF-8")); /*각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호*/
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
        return parseLandUsageJsonData(convertResult);
    }


    private LandUsageDataApiDto parseLandUsageJsonData(JSONObject data) throws JsonProcessingException {
        JSONObject wfs = data.getJSONObject("wfs:FeatureCollection");

        if (!wfs.has("gml:featureMember")) {
            return null;
        }

        Object object = wfs.get("gml:featureMember");
        if (object == null) {
            return null;
        }

        if (object instanceof JSONObject) {
            JSONObject gml = (JSONObject) object;
            JSONObject nsdi = gml.getJSONObject("NSDI:F176");
            LandUsageDataApiDto usageData = LandUsageDataApiDto.convertData(nsdi);
            return usageData;
        }

        return null;
    }
}
