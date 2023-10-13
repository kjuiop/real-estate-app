package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateUpdateForm;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
public interface RealEstateService {

    Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser);

    Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto);

    RealEstateDetailDto getDetail(Long realEstateId);

    Long basicInfoUpdate(RealEstateUpdateForm updateForm, LoginUser loginUser);

    Long updateProcessStatus(RealEstateUpdateForm updateForm, LoginUser loginUser);

    Long create(RealEstateCreateForm createForm, LoginUser loginUser);
}
