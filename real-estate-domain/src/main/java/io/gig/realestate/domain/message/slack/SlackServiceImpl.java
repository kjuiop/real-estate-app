package io.gig.realestate.domain.message.slack;

import io.gig.realestate.domain.utils.properties.SlackProperties;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2024/05/01
 */
@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final SlackProperties slackProperties;

    @Override
    public void sendMessage(String message) {
        SlackApi slackApi = new SlackApi(slackProperties.getUrl());
        slackApi.call(new SlackMessage("An error occurred : " + message));
    }
}
