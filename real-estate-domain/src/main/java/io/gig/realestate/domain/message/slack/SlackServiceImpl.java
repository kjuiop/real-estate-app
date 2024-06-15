package io.gig.realestate.domain.message.slack;

import io.gig.realestate.domain.utils.properties.SlackProperties;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2024/05/01
 */
@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final SlackProperties slackProperties;

    @Value("${domain}")
    private String domain;

    @Override
    @Transactional
    public void sendMessage(String message) {
        SlackApi slackApi = new SlackApi(slackProperties.getUrl());
        slackApi.call(new SlackMessage("[" + domain + "] " + "An error occurred : " + message));
    }

    @Override
    @Transactional
    public void sendMessageToChannelByApp(String channelKey, String message) throws IOException {
        if (!StringUtils.hasText(channelKey)) {
            return;
        }

        String url = slackProperties.getChatApi();
        url += "?channel="+channelKey;
        url += "&text="+ message;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackProperties.getToken());
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        System.out.println(jsonObject);
    }

    @Override
    @Transactional(readOnly = true)
    public String getSlackIdByEmail(String email) {

        String url = "https://slack.com/api/users.lookupByEmail";
        url += "?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + slackProperties.getToken());
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        boolean isOk = jsonObject.getBoolean("ok");
        if (!isOk) {
            SlackApi slackApi = new SlackApi(slackProperties.getUrl());
            slackApi.call(new SlackMessage("not register slack user : " + email + ", json str : " + jsonObject));
            return "";
        }
        JSONObject profile = jsonObject.getJSONObject("user");
        String id = (String) profile.get("id");
        return id;
    }
}
