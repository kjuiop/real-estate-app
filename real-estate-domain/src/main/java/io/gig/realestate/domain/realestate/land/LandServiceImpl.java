package io.gig.realestate.domain.realestate.land;

import io.gig.realestate.domain.realestate.land.dto.LandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/23
 */
@Service
@RequiredArgsConstructor
public class LandServiceImpl implements LandService {

    @Override
    @Transactional(readOnly = true)
    public List<LandDto> getLandListInfoByPnu(String pnu) {
        return null;
    }
}
