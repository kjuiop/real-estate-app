package io.gig.realestate.domain.realestate.price;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.basic.RealEstateReader;
import io.gig.realestate.domain.realestate.basic.RealEstateStore;
import io.gig.realestate.domain.realestate.price.dto.PriceCreateForm;
import io.gig.realestate.domain.realestate.price.dto.PriceListDto;
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

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;
    private final PriceReader priceReader;

    @Override
    @Transactional(readOnly = true)
    public List<PriceListDto> getPriceListInfoByRealEstateId(Long realEstateId) {
        return priceReader.getPriceInfoByRealEstateId(realEstateId);
    }

    @Override
    @Transactional
    public Long create(PriceCreateForm createForm, LoginUser loginUser) {

        RealEstate realEstate;
        if (createForm.getRealEstateId() == null) {
            realEstate = RealEstate.initialInfoWithImg(createForm.getLegalCode(), createForm.getAddress(), createForm.getLandType(), createForm.getBun(), createForm.getJi(), createForm.getImgUrl());
        } else {
            realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
            realEstate.updateImgUrl(createForm.getImgUrl());
        }

        PriceInfo priceInfo = PriceInfo.create(createForm, realEstate);
        realEstate.addPriceInfo(priceInfo);
        return realEstateStore.store(realEstate).getId();
    }

}
