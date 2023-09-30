package io.gig.realestate.domain.realestate.construct;

import io.gig.realestate.domain.realestate.construct.dto.ConstructDataApiDto;
import io.gig.realestate.domain.realestate.construct.dto.ConstructFloorDataApiDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
public interface ConstructService {
    ConstructDataApiDto getConstructInfo(String bCode, String landType, String bun, String ji) throws IOException;

    List<ConstructFloorDataApiDto> getConstructFloorInfo(String bCode, String landType, String bun, String ji) throws IOException;
}
