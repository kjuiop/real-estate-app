package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public interface RealEstateReader {

    Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto);

    RealEstateDetailDto getRealEstateDetail(Long realEstateId);

    RealEstate getRealEstateById(Long realEstateId);

    boolean isExistAddress(String address);

    Long getPrevRealEstateId(Long realEstateId);

    Long getNextRealEstateId(Long realEstateId);

    List<Long> getRealEstateIdsBySearch(RealEstateSearchDto searchDto);
}
