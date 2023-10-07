package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.category.Category;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
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
    public Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser) {
        Category usageType = categoryService.getCategoryById(createForm.getUsageTypeId());
        Administrator manager = administratorService.getAdminEntityByUsername(createForm.getManagerUsername());

        RealEstate realEstate;
        if (createForm.getRealEstateId() == null) {
            realEstate = RealEstate.create(createForm, manager, usageType, loginUser.getLoginUser());
        } else {
            realEstate = realEstateReader.getRealEstateById(createForm.getRealEstateId());
            realEstate.update(createForm, manager, usageType, loginUser.getLoginUser());
        }
        return realEstateStore.store(realEstate).getId();
    }
}
