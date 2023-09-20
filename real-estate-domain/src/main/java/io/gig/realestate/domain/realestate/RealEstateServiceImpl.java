package io.gig.realestate.domain.realestate;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.dto.RealEstateListDto;
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

    private final RealEstateReader realEstateReader;
    private final RealEstateStore realEstateStore;

    @Override
    @Transactional(readOnly = true)
    public Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto) {
        return realEstateReader.getRealEstatePageListBySearch(searchDto);
    }

    @Override
    @Transactional
    public Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser) {
        Administrator manager = administratorService.getAdminEntityByUsername(createForm.getManagerUsername());
        RealEstate newRealEstate = RealEstate.create(createForm, manager, loginUser.getLoginUser());
        return realEstateStore.store(newRealEstate).getId();
    }
}
