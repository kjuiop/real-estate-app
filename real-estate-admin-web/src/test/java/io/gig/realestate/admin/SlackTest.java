package io.gig.realestate.admin;

import io.gig.realestate.domain.message.slack.SlackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author : JAKE
 * @date : 2024/06/05
 */
@ContextConfiguration(classes = TestConfiguration.class)
@SpringBootTest
public class SlackTest {

    @Autowired
    private SlackService slackService;

    @Test
    void sendTest() {
        try {
            slackService.sendMessageToChannelByApp("C076SFYQVU1", "자바 테스터로 메시지 전송 테스트1");
        } catch (Exception e) {
            System.out.println("sendTest Error occurred err: " + e.toString());
        }
    }

    @Test
    void getSlackIdByEmail() {
        String userId = slackService.getSlackIdByEmail("arneg0shua@gmail.com");
        System.out.println(userId);
    }

    @Test
    void sendDmMessage() {
        try {
            String userId = slackService.getSlackIdByEmail("arneg0shua@gmail.com");
            slackService.sendMessageToChannelByApp(userId, "DM Message Test");
        } catch (Exception e) {
            System.out.println("sendTest Error occurred err: " + e.toString());
        }
    }
}
