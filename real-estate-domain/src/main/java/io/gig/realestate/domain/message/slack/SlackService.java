package io.gig.realestate.domain.message.slack;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * @author : JAKE
 * @date : 2024/05/01
 */
public interface SlackService {
    void sendMessage(String message);

    void sendMessageToChannelByApp(String channelKey, String message) throws IOException;

    String getSlackIdByEmail(String email);
}
