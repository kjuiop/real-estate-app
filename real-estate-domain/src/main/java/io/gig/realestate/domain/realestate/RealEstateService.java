package io.gig.realestate.domain.realestate;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.dto.RealEstateCreateForm;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
public interface RealEstateService {

    Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser);

}
