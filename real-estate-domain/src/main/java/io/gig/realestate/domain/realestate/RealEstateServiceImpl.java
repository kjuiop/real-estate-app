package io.gig.realestate.domain.realestate;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.admin.AdministratorService;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.dto.RealEstateCreateForm;
import lombok.RequiredArgsConstructor;
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
    private final RealEstateStore realEstateStore;

    @Override
    @Transactional
    public Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser) {
        Administrator manager = administratorService.getAdminEntityByUsername(createForm.getManagerUsername());
        RealEstate newRealEstate = RealEstate.create(createForm, manager, loginUser.getLoginUser());
        return realEstateStore.store(newRealEstate).getId();
    }
}
