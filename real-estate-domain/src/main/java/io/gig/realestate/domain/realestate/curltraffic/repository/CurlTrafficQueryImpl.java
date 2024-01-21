package io.gig.realestate.domain.realestate.curltraffic.repository;

import io.gig.realestate.domain.realestate.curltraffic.CurlTrafficReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2024/01/21
 */
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CurlTrafficQueryImpl implements CurlTrafficReader {
}
