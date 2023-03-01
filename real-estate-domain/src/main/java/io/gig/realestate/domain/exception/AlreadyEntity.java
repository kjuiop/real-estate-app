package io.gig.realestate.domain.exception;

/**
 * @author : JAKE
 * @date : 2023/03/01
 */
public class AlreadyEntity extends RuntimeException {

    public AlreadyEntity() {
    }

    public AlreadyEntity(String message) { super(message); }

    public AlreadyEntity(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyEntity(Throwable cause) {
        super(cause);
    }

    public AlreadyEntity(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
