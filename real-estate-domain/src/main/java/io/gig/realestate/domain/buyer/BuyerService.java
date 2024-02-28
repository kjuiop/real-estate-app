package io.gig.realestate.domain.buyer;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.dto.*;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerService {
    Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition);

    Long create(BuyerCreateForm createForm, LoginUser loginUser);

    BuyerDetailDto getBuyerDetail(Long buyerId);

    Long update(BuyerDetailUpdateForm updateForm, LoginUser loginUser);
}
