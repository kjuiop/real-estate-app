package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.dto.*;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2023/09/18
 */
public interface RealEstateService {

    Page<RealEstateListDto> getRealEstatePageListBySearch(String sessionId, RealEstateSearchDto searchDto);

    RealEstateDetailDto getDetail(String sessionId, Long realEstateId);

    Long updateProcessStatus(RealEstateUpdateForm updateForm, LoginUser loginUser);

    Long create(RealEstateCreateForm createForm, LoginUser loginUser);

    Long update(RealEstateUpdateForm updateForm, LoginUser loginUser);

    Long updateRStatus(StatusUpdateForm updateForm, LoginUser loginUser);

    Long updateABStatus(StatusUpdateForm updateForm, LoginUser loginUser);

    boolean checkDuplicateAddress(String address, LoginUser loginUser);

    Long getPrevRealEstateId(Long realEstateId);

    Long getNextRealEstateId(Long realEstateId);
}
