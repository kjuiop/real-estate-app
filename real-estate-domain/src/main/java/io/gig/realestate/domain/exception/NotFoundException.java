package io.gig.realestate.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : JAKE
 * @date : 2023/03/20
 */
@Getter
public class NotFoundException extends RuntimeException {

    private HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
