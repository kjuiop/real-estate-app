package io.gig.realestate.domain.realestate;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.dto.RealEstateCreateForm;
import io.gig.realestate.domain.realestate.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.dto.RealEstateListDto;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
public interface RealEstateService {

    Long basicInfoSave(RealEstateCreateForm createForm, LoginUser loginUser);

    Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto);

    RealEstateDetailDto getDetail(Long realEstateId);
}
