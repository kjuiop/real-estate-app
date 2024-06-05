package io.gig.realestate.domain.message.slack;

import io.gig.realestate.domain.utils.properties.SlackProperties;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
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

/**
 * @author : JAKE
 * @date : 2024/05/01
 */
@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final SlackProperties slackProperties;

    @Override
    @Transactional
    public void sendMessage(String message) {
        SlackApi slackApi = new SlackApi(slackProperties.getUrl());
        slackApi.call(new SlackMessage("An error occurred : " + message));
    }

    @Override
    @Transactional
    public void sendMessageToChannelByApp(String channelKey, String message) throws IOException {

        String urlStr = slackProperties.getChatApi();

        urlStr += "?channel="+channelKey;
        urlStr += "&text="+ URLEncoder.encode(message, "UTF-8");

        HttpURLConnection conn = null;
        URL url = new URL(urlStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer "+ slackProperties.getToken());
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuffer response = new StringBuffer();

        if (conn.getResponseCode()==200) {
            response.append(br.readLine());
            if (response!=null) {
                if (String.valueOf(response).contains("\"ok\":true")) {
                    System.out.println("슬랙 메시지 발송 성공");
                }else {
                    System.out.println("슬랙 메시지 발송 실패");
                }
            }
        } else {
            System.out.println("슬랙 API 오류 발생 code: " + conn.getResponseCode() + " message: " + conn.getResponseMessage());
        }

        br.close();
        conn.disconnect();
    }
}
