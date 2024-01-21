package io.gig.realestate.domain.realestate.curltraffic;

import io.gig.realestate.domain.common.YnType;

/**
 * @author : JAKE
 * @date : 2024/01/21
 */
public interface CurlTrafficService {

    void createLandPublicApi(int statusCode, String pnu);
}
