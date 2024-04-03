package io.gig.realestate.domain.buyer.basic;

import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.buyer.basic.dto.*;
import io.gig.realestate.domain.buyer.detail.dto.BuyerDetailUpdateForm;
import io.gig.realestate.domain.buyer.memo.dto.MemoForm;
import org.springframework.data.domain.Page;

/**
 * @author : JAKE
 * @date : 2024/02/24
 */
public interface BuyerService {
    Page<BuyerListDto> getBuyerPageListBySearch(BuyerSearchDto condition);

    Long create(BuyerForm createForm, LoginUser loginUser);

    BuyerDetailDto getBuyerDetail(Long buyerId);

    Long update(BuyerForm updateForm, LoginUser loginUser);

    BuyerModalDto getBuyerDetailModal(Long buyerId, LoginUser loginUser);

    BuyerModalDto createMemo(Long buyerId, MemoForm createForm, LoginUser loginUser);
}
