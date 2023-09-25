package io.gig.realestate.domain.utils;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/09/25
 */
@Component
@RequiredArgsConstructor
public class CommonUtils {

    public static JSONObject convertXmlToJson(String xmlStr) {
        return XML.toJSONObject(xmlStr);
    }
}
