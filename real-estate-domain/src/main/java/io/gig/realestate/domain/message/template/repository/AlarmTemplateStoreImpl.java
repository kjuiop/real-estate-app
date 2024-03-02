package io.gig.realestate.domain.message.template.repository;

import io.gig.realestate.domain.message.template.AlarmTemplateStore;
import io.gig.realestate.domain.message.template.AlarmTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/03/02
 */
@Component
@Transactional
@RequiredArgsConstructor
public class AlarmTemplateStoreImpl implements AlarmTemplateStore {

    private final AlarmTemplateStoreRepository storeRepository;

    @Override
    public AlarmTemplate store(AlarmTemplate alarmTemplate) {
        return storeRepository.save(alarmTemplate);
    }
}
