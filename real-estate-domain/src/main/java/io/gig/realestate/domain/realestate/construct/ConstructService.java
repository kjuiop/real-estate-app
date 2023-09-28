package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
public interface ConstructService {
    ConstructDataApiDto getConstructInfoByPnu(String pnu) throws IOException;
}
