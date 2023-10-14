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

        return null;
//        RealEstate realEstate;
//        if (createForm.getRealEstateId() == null) {
//            realEstate = RealEstate.initialInfoWithImg(createForm.getLegalCode(), createForm.getAddress(), createForm.getLandType(), createForm.getBun(), createForm.getJi(), createForm.getImgUrl());
//        } else {
//            realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
//            realEstate.updateImgUrl(createForm.getImgUrl());
//        }
//        PriceInfo priceInfo = PriceInfo.create(createForm, realEstate);
//        realEstate.addPriceInfo(priceInfo);
//
//        realEstate.getFloorPriceInfo().clear();
//        for (PriceCreateForm.FloorDto dto : createForm.getFloorInfo()) {
//            FloorPriceInfo floorInfo = FloorPriceInfo.create(dto, realEstate);
//            realEstate.addFloorInfo(floorInfo);
//        }
//        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long update(PriceUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate  = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateImgUrl(updateForm.getImgUrl());

        PriceInfo priceInfo = realEstate.getPriceInfoList().get(0);
        priceInfo.update(updateForm);
        realEstate.getPriceInfoList().clear();
        realEstate.addPriceInfo(priceInfo);

        realEstate.getFloorPriceInfo().clear();
        for (PriceUpdateForm.FloorDto dto : updateForm.getFloorInfo()) {
            FloorPriceInfo floorInfo = FloorPriceInfo.update(dto, realEstate);
            realEstate.addFloorInfo(floorInfo);
        }
        return realEstateStore.store(realEstate).getId();
    }
}
