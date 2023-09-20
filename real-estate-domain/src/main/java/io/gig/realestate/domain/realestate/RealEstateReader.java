package io.gig.realestate.domain.realestate;

import io.gig.realestate.domain.realestate.dto.RealEstateListDto;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2023/09/20
 */
public interface RealEstateReader {

    Page<RealEstateListDto> getRealEstatePageListBySearch(RealEstateSearchDto searchDto);

}
