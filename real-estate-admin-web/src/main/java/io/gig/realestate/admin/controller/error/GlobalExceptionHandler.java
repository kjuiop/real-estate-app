package io.gig.realestate.admin.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : JAKE
 * @date : 2024/04/29
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    public String handle(Exception e) {
        log.error("hello " + e.getMessage());
        return "error/500";
    }
}
