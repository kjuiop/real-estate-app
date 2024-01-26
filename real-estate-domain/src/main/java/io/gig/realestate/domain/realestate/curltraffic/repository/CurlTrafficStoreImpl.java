package io.gig.realestate.domain.realestate.curltraffic.repository;

import io.gig.realestate.domain.realestate.curltraffic.CurlTrafficLight;
import io.gig.realestate.domain.realestate.curltraffic.CurlTrafficStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/01/21
 */
@Component
@Transactional
@RequiredArgsConstructor
public class CurlTrafficStoreImpl implements CurlTrafficStore {

    private final CurlTrafficStoreRepository storeRepository;

    @Override
    public CurlTrafficLight store(CurlTrafficLight curlTrafficLight) {
        return storeRepository.save(curlTrafficLight);
    }
}
