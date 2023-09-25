package io.gig.realestate.domain.realestate.land;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.gig.realestate.domain.realestate.land.dto.LandDataApiDto;
import io.gig.realestate.domain.realestate.land.dto.LandDto;
import io.gig.realestate.domain.realestate.land.dto.LandFrlDto;
import io.gig.realestate.domain.utils.CommonUtils;
import io.gig.realestate.domain.utils.properties.LandDataProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    private final LandDataProperties landProperties;

    @Override
    @Transactional
    public List<LandFrlDto> getLandListInfoByPnu(String pnu) throws IOException {
        String apiPath = landProperties.getUrl();
        StringBuilder urlBuilder = new StringBuilder(apiPath); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + landProperties.getServiceKey()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pnu","UTF-8") + "=" + URLEncoder.encode(pnu, "UTF-8")); /*각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*검색건수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
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
        List<LandFrlDto> landDataApiDtoList = parseJsonData(convertResult);

        return landDataApiDtoList;
    }

    private List<LandFrlDto> parseJsonData(JSONObject data) throws JsonProcessingException {
        JSONObject fields = data.getJSONObject("fields");

        JSONArray jsonArray = new JSONArray();
        Object object  = fields.getJSONObject("ladfrlVOList");
        if (object instanceof JSONObject) {
            jsonArray = new JSONArray();
            jsonArray.put(object);
        } else if (object instanceof JSONArray) {
            jsonArray = fields.getJSONArray("ladfrlVOList");
        } else {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<LandFrlDto> landDataApiList = objectMapper.readValue(jsonArray.toString(), new TypeReference<List<LandFrlDto>>() {
        });

        return landDataApiList;
    }
}
