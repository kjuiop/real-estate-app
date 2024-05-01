package io.gig.realestate.admin.controller.error;

import io.gig.realestate.domain.message.slack.SlackService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : JAKE
 * @date : 2024/04/29
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SlackService slackService;

    @ExceptionHandler(value = Exception.class)
    public String handle(Exception e) {
        slackService.sendMessage(e.getMessage());
        log.error("An error occurred: {}", e.getMessage(), e);
        return "error/500";
    }
}
