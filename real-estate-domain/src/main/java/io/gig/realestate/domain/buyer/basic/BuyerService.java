package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.BuyerCreateForm;
import io.gig.realestate.domain.buyer.basic.dto.BuyerListDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerSearchDto;
import io.gig.realestate.domain.buyer.basic.dto.BuyerDetailDto;
import io.gig.realestate.domain.buyer.detail.dto.BuyerDetailUpdateForm;
import io.gig.realestate.domain.buyer.detail.dto.ProcessDetailDto;
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
