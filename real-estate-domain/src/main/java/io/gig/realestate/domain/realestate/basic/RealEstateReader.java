package io.gig.realestate.domain.realestate.basic;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.realestate.basic.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public interface RealEstateReader {

    Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto, Administrator loginUser);

    RealEstateDetailDto getRealEstateDetail(Long realEstateId);

    RealEstate getRealEstateById(Long realEstateId);

    boolean isExistAddress(String address);

    Long getPrevRealEstateId(Long realEstateId);

    Long getNextRealEstateId(Long realEstateId);

    List<Long> getRealEstateIdsBySearch(RealEstateSearchDto searchDto, Administrator loginUser);

    RealEstateDetailAllDto getRealEstateDetailAllInfo(Long realEstateId);

    boolean isExistLegalCodeAndBunJi(String legalCode, String bun, String ji);

    List<CoordinateDto> getCoordinateList(RealEstateSearchDto condition, Administrator loginUser);

    List<RealEstateListDto> getRealEstateByAddress(String address);
}
