package io.gig.realestate.domain.realestate.curltraffic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/01/21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurlTrafficServiceImpl implements CurlTrafficService {

    private final CurlTrafficReader curlTrafficReader;
    private final CurlTrafficStore curlTrafficStore;

    @Override
    @Transactional
    public void createLandPublicApi(int statusCode, String pnu) {
//        CurlTrafficLight curlTrafficLight = CurlTrafficLight.createByLand(statusCode, pnu);
//        curlTrafficStore.store(curlTrafficLight);
    }
}
