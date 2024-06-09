package io.gig.realestate.domain.message.basic;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
public interface MessageStore {
    MessageInfo store(MessageInfo messageInfo);
}
