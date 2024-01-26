package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceListDto;
import io.gig.realestate.domain.realestate.price.dto.PriceUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceReader priceReader;

    @Override
    @Transactional(readOnly = true)
    public List<PriceListDto> getPriceListInfoByRealEstateId(Long realEstateId) {
        return priceReader.getPriceInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional
    public Long create(PriceCreateForm createForm, LoginUser loginUser) {
        return null;
    }

    @Override
    @Transactional
    public Long update(PriceUpdateForm updateForm, LoginUser loginUser) {
        return null;
    }
}
