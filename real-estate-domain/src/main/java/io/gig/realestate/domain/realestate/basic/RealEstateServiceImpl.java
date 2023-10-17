package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.realestate.basic.dto.*;
import io.gig.realestate.domain.realestate.construct.ConstructInfo;
import io.gig.realestate.domain.realestate.construct.dto.FloorCreateForm;
import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.land.dto.LandInfoDto;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.PriceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
@Service
@RequiredArgsConstructor
public class RealEstateServiceImpl implements RealEstateService {

    private final AdministratorService administratorService;
    private final CategoryService categoryService;

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto) {
        return realEstateReader.getRealEstatePageListBySearch(searchDto);
    }

    @Override
    @Transactional(readOnly = true)
    public RealEstateDetailDto getDetail(Long realEstateId) {
        return realEstateReader.getRealEstateDetail(realEstateId);
    }

    @Override
    @Transactional
    public Long create(RealEstateCreateForm createForm, LoginUser loginUser) {
        Administrator manager = administratorService.getAdminEntityByUsername(createForm.getManagerUsername());
        RealEstate newRealEstate;
        if (createForm.getUsageTypeId() != null) {
            Category usageType = categoryService.getCategoryById(createForm.getUsageTypeId());
            newRealEstate = RealEstate.createWithUsageType(createForm, manager, usageType, loginUser.getLoginUser());
        } else {
            newRealEstate = RealEstate.create(createForm, manager, loginUser.getLoginUser());
        }

        for (LandInfoDto dto : createForm.getLandInfoList()) {
            LandInfo landInfo = LandInfo.create(createForm.getAddress(), dto, newRealEstate);
            newRealEstate.addLandInfo(landInfo);
        }

        PriceInfo priceInfo = PriceInfo.create(createForm.getPriceInfo(), newRealEstate);
        newRealEstate.addPriceInfo(priceInfo);

        for (FloorCreateForm dto : createForm.getFloorInfoList()) {
            FloorPriceInfo floorInfo = FloorPriceInfo.create(dto, newRealEstate);
            newRealEstate.addFloorInfo(floorInfo);
        }

        ConstructInfo constructInfo = ConstructInfo.create(createForm.getConstructInfo(), newRealEstate);
        newRealEstate.addConstructInfo(constructInfo);

        for (CustomerCreateForm dto : createForm.getCustomerInfoList()) {
            CustomerInfo customerInfo = CustomerInfo.create(dto, newRealEstate);
            newRealEstate.addCustomerInfo(customerInfo);
        }

        return realEstateStore.store(newRealEstate).getId();
    }

    @Override
    @Transactional
    public Long update(RealEstateUpdateForm updateForm, LoginUser loginUser) {
        Administrator manager = administratorService.getAdminEntityByUsername(updateForm.getManagerUsername());
        Category usageType = null;
        if (updateForm.getUsageTypeId() != null) {
            usageType = categoryService.getCategoryById(updateForm.getUsageTypeId());
        }

        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.update(updateForm, manager, usageType, loginUser.getLoginUser());

        realEstate.getLandInfoList().clear();
        for (LandInfoDto dto : updateForm.getLandInfoList()) {
            LandInfo landInfo = LandInfo.create(updateForm.getAddress(), dto, realEstate);
            realEstate.addLandInfo(landInfo);
        }

        realEstate.getPriceInfoList().clear();
        PriceInfo priceInfo = PriceInfo.create(updateForm.getPriceInfo(), realEstate);
        realEstate.addPriceInfo(priceInfo);

        realEstate.getFloorPriceInfo().clear();
        for (FloorCreateForm dto : updateForm.getFloorInfoList()) {
            FloorPriceInfo floorInfo = FloorPriceInfo.create(dto, realEstate);
            realEstate.addFloorInfo(floorInfo);
        }

        realEstate.getConstructInfoList().clear();
        ConstructInfo constructInfo = ConstructInfo.create(updateForm.getConstructInfo(), realEstate);
        realEstate.addConstructInfo(constructInfo);

        realEstate.getCustomerInfoList().clear();
        for (CustomerCreateForm dto : updateForm.getCustomerInfoList()) {
            CustomerInfo customerInfo = CustomerInfo.create(dto, realEstate);
            realEstate.addCustomerInfo(customerInfo);
        }

        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser) {
        Category usageType = null;
        if (createForm.getUsageTypeId() != null) {
            usageType = categoryService.getCategoryById(createForm.getUsageTypeId());
        }
        Administrator manager = administratorService.getAdminEntityByUsername(createForm.getManagerUsername());

        RealEstate realEstate;
        if (createForm.getRealEstateId() == null) {
            realEstate = RealEstate.createWithUsageType(createForm, manager, usageType, loginUser.getLoginUser());
        } else {
            realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
        }
        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long basicInfoUpdate(RealEstateUpdateForm updateForm, LoginUser loginUser) {
        Category usageType = null;
        if (updateForm.getUsageTypeId() != null) {
            usageType = categoryService.getCategoryById(updateForm.getUsageTypeId());
        }

        Administrator manager = administratorService.getAdminEntityByUsername(updateForm.getManagerUsername());
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.update(updateForm, manager, usageType, loginUser.getLoginUser());
        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long updateProcessStatus(RealEstateUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateProcessStatus(updateForm.getProcessType(), loginUser.getLoginUser());
        return realEstateStore.store(realEstate).getId();
    }

    @Override
    @Transactional
    public Long updateRStatus(StatusUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateRStatus(updateForm.getRYn());
        return updateForm.getRealEstateId();
    }

    @Override
    @Transactional
    public Long updateABStatus(StatusUpdateForm updateForm, LoginUser loginUser) {
        RealEstate realEstate = realEstateReader.getRealEstateById(updateForm.getRealEstateId());
        realEstate.updateABStatus(updateForm.getAbYn());
        return updateForm.getRealEstateId();
    }
}
