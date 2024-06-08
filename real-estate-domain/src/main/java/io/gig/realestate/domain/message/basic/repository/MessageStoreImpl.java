package io.gig.realestate.domain.message.basic.repository;

import io.gig.realestate.domain.message.basic.MessageInfo;
import io.gig.realestate.domain.message.basic.MessageStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/06/08
 */
@Component
@Transactional
@RequiredArgsConstructor
public class MessageStoreImpl implements MessageStore {

    private final MessageStoreRepository storeRepository;

    @Override
    public MessageInfo store(MessageInfo messageInfo) {
        return storeRepository.save(messageInfo);
    }
}
