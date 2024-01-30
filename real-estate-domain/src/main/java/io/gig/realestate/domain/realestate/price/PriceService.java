package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceListDto;
import io.gig.realestate.domain.realestate.price.dto.PriceUpdateForm;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/30
 */
public interface PriceService {
    Long create(PriceCreateForm createForm, LoginUser loginUser);

    List<PriceListDto> getPriceListInfoByRealEstateId(Long realEstateId);

    Long update(PriceUpdateForm updateForm, LoginUser loginUser);

    PriceInfo getPriceInfoByPriceId(Long priceId);
}
